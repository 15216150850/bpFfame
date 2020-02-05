package com.bp.act.service;


import com.bp.act.entity.process.ActUserTask;
import com.bp.act.mapper.ActUserTaskMapper;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 钟欣凯
 * @version 1.0
 * @Description: act_user_task服务层
 * @date 2018年11月19日
 */
@Service
public class ActUserTaskService {

    @Resource
    private ActUserTaskMapper actUserTaskMapper;

    @Resource
    private RepositoryService repositoryService;


    /**
     * 根据流程定义的KEY查询列表
     *
     * @param para 参数
     * @return 查询结果
     */
    public List<ActUserTask> selectAllByKey(Map para) {
        List<ActUserTask> list = actUserTaskMapper.selectAllEntityByKey(para);
        return list;
    }

    public void insertSingleActivitiInfo(ProcessDefinition processDefinition) {
        Map map = new HashMap(16);
        //noinspection unchecked
        map.put("key", processDefinition.getKey());
        List<ActUserTask> list = actUserTaskMapper.selectAllEntityByKey(map);
        ProcessDefinitionEntity processDef = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinition.getId());
        //获得当前任务的所有节点
        List<ActivityImpl> activitiList = processDef.getActivities();
        for (ActivityImpl activity : activitiList) {
            ActivityBehavior activityBehavior = activity.getActivityBehavior();
            boolean isFound = false;
            //是否为用户任务
            if (activityBehavior instanceof UserTaskActivityBehavior) {
                UserTaskActivityBehavior userTaskActivityBehavior = (UserTaskActivityBehavior) activityBehavior;
                TaskDefinition taskDefinition = userTaskActivityBehavior.getTaskDefinition();
                //任务所属角色
                String taskDefKey = taskDefinition.getKey();
                Expression taskName = taskDefinition.getNameExpression();

                //判断表中是否存在此节点
                if (list.size() != 0) {
                    for (ActUserTask userTask : list) {
                        if (taskDefKey.equals(userTask.getTaskDefKey())) {
                            userTask.setProcDefKey(processDefinition.getKey());
                            userTask.setProcDefName(processDefinition.getName());
                            userTask.setTaskDefKey(taskDefKey);
                            userTask.setTaskName(taskName.toString());
                            actUserTaskMapper.update(userTask);
                            isFound = true;
                            break;
                        }
                    }

                }
                if (!isFound) {
                    ActUserTask userTask = new ActUserTask();
                    userTask.setProcDefKey(processDefinition.getKey());
                    userTask.setProcDefName(processDefinition.getName());
                    userTask.setTaskDefKey(taskDefKey);
                    userTask.setTaskName(taskName.toString());
                    actUserTaskMapper.insert(userTask);
                }
            }
        }
    }

    public void updateUser(List<LinkedHashMap> maps) {
        actUserTaskMapper.updateUser(maps);
    }
}
