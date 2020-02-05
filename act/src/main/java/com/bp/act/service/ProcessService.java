package com.bp.act.service;



import com.bp.act.bean.ReturnBean;
import com.bp.act.bean.ReturnCode;
import com.bp.act.client.SysClient;
import com.bp.act.contants.SysConstant;
import com.bp.act.entity.ActKeyUrl;

import com.bp.act.entity.SysUser;
import com.bp.act.entity.process.ActUserTask;
import com.bp.act.mapper.ActUserTaskMapper;
import com.bp.act.mapper.ProcessMapper;

import com.bp.act.utils.UserUtils;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @auther: 钟欣凯
 * @date: 2019/1/21 09:41
 */
@Service
public class ProcessService {

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private IdentityService identityService;

    @Resource
    private ProcessMapper processMapper;

    @Autowired
    private ActUserTaskMapper actUserTaskMapper;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private SysClient sysClient;

    /**
     * 启动流程定义
     *
     * @param map 启动参数
     * @return 启动结果
     */
    @Transactional(rollbackFor = Exception.class)
    //  @TxTransaction
    public ReturnBean startProcess(SysUser sysUser, Map map) {
        String keyName = map.get("keyName").toString();
        System.out.println(sysUser);
        map.remove("keyName");
        SysUser currentUser = UserUtils.getCurrentUser();
        identityService.setAuthenticatedUserId(sysUser.getId().toString());
        ProcessInstance processInstance = null;
        try {
            processInstance = runtimeService.startProcessInstanceByKey(keyName, map);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnBean.error("流程启动失败!");
        }
        //将key 与 url关系插入到数据库当中
        ActKeyUrl actKeyUrl = new ActKeyUrl();
        actKeyUrl.setBaseUrl(map.get("baseUrl").toString());
        actKeyUrl.setKey(keyName);
        ActKeyUrl actKeyUrl1 = processMapper.selectKeyUrlByKey(keyName);

        if (actKeyUrl1 == null) {
            processMapper.insertActKeyUrl(actKeyUrl);
        } else {
            actKeyUrl.setId(actKeyUrl1.getId());
            processMapper.updateActKeyUrl(actKeyUrl);
        }
        return new ReturnBean(ReturnCode.OK.getCode(), "流程启动成功", processInstance.getId());
    }

    /**
     * 插入key和路径关系的数据
     *
     * @param actKeyUrl key和路径的基本关系数据
     */
    public void insertActKeyUrl(ActKeyUrl actKeyUrl) {
        processMapper.insertActKeyUrl(actKeyUrl);
    }

    /**
     * 根据key 查询URL
     *
     * @param key 流程key
     * @return 查询结果
     */
    public ActKeyUrl selectKeyUrlByKey(String key) {

        return processMapper.selectKeyUrlByKey(key);
    }

//    /**
//     *  查询最新版本的流程定义
//     * @param para 查询参数
//     * @return 查询结果
//     */
//    public LayUIDataTable selectListProef(Map para) {
//        LayUIDataTable dataTable = new LayUIDataTable();
//        // 下面两句要连着写在一起，就可以实现分页
//        dataTable.setLength(Integer.valueOf(para.get(SysConstant.LIMIT).toString()));
//        dataTable.setPageNum(Integer.valueOf(para.get(SysConstant.PAGE).toString()));
//        PageHelper.startPage(dataTable.getPageNum(), dataTable.getLength());
//        // 下面这句是为了获取分页信息，比如记录总数等等
//        List<Map> list = processMapper.selectListProef(para);
//        PageInfo<Map> pageInfo = new PageInfo(list);
//        dataTable.setData(list);
//        dataTable.setRecordsFiltered(pageInfo.getTotal());
//        dataTable.setCount(dataTable.getRecordsFiltered());
//        dataTable.setCode("0");
//        dataTable.setMsg("请求成功!");
//        return dataTable;
//    }

    /**
     * 插入是否自定义审批人员
     *
     * @param map 参数
     */
    public void insertIsDefined(Map<String, Object> map) {
        processMapper.insertIsDefined(map);
    }
//
//    /**
//     *   查询自定义流程
//     * @param para 查询参数
//     * @return 查询结果
//     */
//    public LayUIDataTable selectListDefinedProdefList(Map para) {
//        LayUIDataTable dataTable = new LayUIDataTable();
//        // 下面两句要连着写在一起，就可以实现分页
//        dataTable.setLength(Integer.valueOf(para.get(SysConstant.LIMIT).toString()));
//        dataTable.setPageNum(Integer.valueOf(para.get(SysConstant.PAGE).toString()));
//        PageHelper.startPage(dataTable.getPageNum(), dataTable.getLength());
//        // 下面这句是为了获取分页信息，比如记录总数等等
//        List<Map> list = processMapper.selectListDefinedProdefList(para);
//        PageInfo<Map> pageInfo = new PageInfo(list);
//        dataTable.setData(list);
//        dataTable.setRecordsFiltered(pageInfo.getTotal());
//        dataTable.setCount(dataTable.getRecordsFiltered());
//        dataTable.setCode("0");
//        dataTable.setMsg("请求成功!");//        dataTable.setMsg("请求成功!");
//        return dataTable;
//    }

    /**
     * 根据key修改是否自定义审批人员表
     *
     * @param paraMap 参数
     */
    public void updateIsDefinedByKey(Map<String, Object> paraMap) {
        processMapper.updateIsDefinedByKey(paraMap);
    }

    public void deleteIsDefinedByKey(String key) {
        processMapper.deleteIsDefinedByKey(key);
    }

    /**
     * 查询最新版本的流程定义列表
     *
     * @param para
     * @return
     */
    public ReturnBean<Map<String, Object>> selectListProef(Map para) {
        if (para.get("page") != null) {
            para.put("page", (Integer.valueOf(para.get(SysConstant.PAGE).toString()) - 1) * Integer.valueOf(para.get(SysConstant.LIMIT).toString()));
        }
        Long count = processMapper.selectListDefinedProdefListCount(para);
        List<Map> list = processMapper.selectListDefinedProdefList(para);
        return ReturnBean.list(list, count);
    }


    /**
     * 初始化流程定义
     *
     * @param processDefinition
     */
    public void insertSingleActivitiInfo(ProcessDefinition processDefinition) {

        List<ActUserTask> list = processMapper.findProCessByKey(processDefinition.getKey());
        try {


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

                    Set<Expression> candidateUserIdExpressions = taskDefinition.getCandidateUserIdExpressions();
                    //任务所属角色
                    String taskDefKey = taskDefinition.getKey();
                    Expression taskName = taskDefinition.getNameExpression();

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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据流程key查找
     *
     * @param key
     * @return
     */
    public ReturnBean<List<ActUserTask>> findUserTaskByKey(String key) {

        List<ActUserTask> actUserTasks = processMapper.findProCessByKey(key);
        return ReturnBean.ok(actUserTasks);
    }

    /**
     * 设置用户
     *
     * @param actUserTasks
     * @return
     */
    public ReturnBean updateUserId(List<ActUserTask> actUserTasks) {
        for (ActUserTask actUserTask :
                actUserTasks) {
            String jcbms = actUserTask.getJcbms();
            StringBuilder ids = new StringBuilder();
            if (!StringUtils.isEmpty(jcbms)) {
                for (String userrname :
                        jcbms.split(",")) {
                    ReturnBean<Map> userByUserName = sysClient.findUserByUserName(userrname);
                    if (userByUserName.data != null) {
                        ids.append(userByUserName.data.get("id")).append(",");
                    }

                }
            }
            if (!"".equals(ids.toString())) {
                actUserTask.setCandidateIds(ids.substring(0, ids.length() - 1));

            } else {
                actUserTask.setCandidateIds("");
            }
             actUserTask.setJcbms(jcbms);
            actUserTaskMapper.update(actUserTask);
        }
        return ReturnBean.ok("用户设置成功");
    }
    /**
     * 根据流程定义key和任务key查询
     * @param pey
     * @param taskKey
     * @return
     */
    public ReturnBean<ActUserTask> findUserTaskByPkeyAndTaskKey(String pey, String taskKey) {
      ActUserTask actUserTask =   actUserTaskMapper.findByPidAndTaskKey(pey,taskKey);
        return ReturnBean.ok(actUserTask);
    }
}
