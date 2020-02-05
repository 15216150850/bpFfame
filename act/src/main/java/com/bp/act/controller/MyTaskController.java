package com.bp.act.controller;


import com.bp.act.bean.ReturnBean;
import com.bp.act.entity.ActKeyUrl;
import com.bp.act.entity.NextTaskInfo;
import com.bp.act.entity.online.Workbench;
import com.bp.act.service.MyTaskService;
import com.bp.act.service.ProcessService;
import com.bp.act.utils.TypeConversionUtils;
import com.bp.act.utils.UserUtils;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 个人任务控制层
 *
 * @author : 钟欣凯
 * @date: 2019/6/12 11:02
 */
@RestController
@RequestMapping("myTask")
public class MyTaskController {

    @Autowired
    private MyTaskService myTaskService;

    @Autowired
    private ProcessService processService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;


    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private HttpServletResponse res;


    /**
     * 代办任务
     *
     * @param para
     * @return
     */
    @GetMapping("/todoList")
    public ReturnBean<List<Workbench>> todoList(@RequestParam Map<String, Object> para) {
        return myTaskService.todoList(para);
    }

    @GetMapping("/getTasksSize")
    public ReturnBean<Map<String, String>> todolist() {
        String currentUserId = UserUtils.getCurrentUser().getId().toString();
        // 根据当前人的ID查询
        TaskQuery taskQuery = taskService.createTaskQuery().taskAssignee(currentUserId);
        Integer personalTasksSize = taskQuery.orderByTaskCreateTime().desc().list().size();
        Integer grouplTasksSize = taskService.createTaskQuery().taskCandidateUser(currentUserId).list().size();
        Map map = new HashMap(2);
        map.put("personalTasksSize", personalTasksSize);
        map.put("grouplTasksSize", grouplTasksSize);
        return ReturnBean.ok(map);
    }

    /**
     * 办理任务
     *
     * @param map
     * @return
     */
    @PostMapping("/handleTask")
    public ReturnBean handleTask(@RequestParam Map map) {

        return myTaskService.handleTask(map);
    }

    @PostMapping("/betchHandleTask")
    public ReturnBean betchHandleTask(@RequestParam Map map) {

        return myTaskService.betchHandleTask(map);
    }

    @GetMapping("getBaseURLByKey")
    public String getBaseURLByKey(String key) {
        ActKeyUrl actKeyUrl = processService.selectKeyUrlByKey(key);
        return actKeyUrl.getBaseUrl();
    }

    /**
     * 根据流程实例ID查询历史办理情况
     *
     * @param pid 流程实例ID
     * @return 查询结果
     */
    @GetMapping("selectHistoryList/{pid}")
    public ReturnBean<Map<String, Object>> selectHistoryList(@PathVariable String pid) {
        return myTaskService.selectHistoryList(pid);
    }

    @GetMapping("getLineName/{taskId}")
    public ReturnBean<List<String>> getLineName(@PathVariable String taskId) {

        List<String> list = new ArrayList<>();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(task.getProcessDefinitionId());
        String excId = task.getExecutionId();
        ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(excId).singleResult();
        String activitiId = execution.getActivityId();
        ActivityImpl activity = processDefinitionEntity.findActivity(activitiId);
        List<PvmTransition> pvmList = activity.getOutgoingTransitions();
        if (pvmList != null && pvmList.size() > 0) {
            for (PvmTransition pvm : pvmList) {
                String name = (String) pvm.getProperty("name");
                if (StringUtils.isNotEmpty(name)){
                    list.add(name);
                } else {
                    list.add("确认");
                }

            }
        } else {
            list.add("确认");

        }
        return ReturnBean.ok(list);
    }

    /**
     * 获取下一任务节点信息
     *
     * @param pid
     * @return
     */
    @GetMapping("/findNextTaskInfo/{pid}/{taskId}")
    public ReturnBean<List<NextTaskInfo>> findNextTaskInfo(@PathVariable("pid") String pid, @PathVariable("taskId") String taskId) {
        return myTaskService.findNextTaskInfo(pid, taskId);
    }

    @RequestMapping("rollBackToAssgin")
    public void rollBackToAssignWoekFlow(@RequestParam("processInstanceId") String processInstanceId, String destTaskkey) {
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        Map map = TypeConversionUtils.objToMap(historicProcessInstance);
        System.out.println("1111");
    }

    @GetMapping("getFlowNode/{pid}")
    public void getFlowNode(@PathVariable("pid") String pid){
        myTaskService.getFlowNode(pid);
    }


    /**
     * 获取已办理完成的任务节点
     * @param pid
     * @return
     */
    @GetMapping("findFinishTaskByPid/{pid}")
    public ReturnBean<List<Map<String, Object>>> findFinishTaskByPid(@PathVariable("pid") String pid) {
         return myTaskService.findFinishTaskByPid(pid);
    }

    /**
     * 获取审批过程的文字，用于APP
     * @param pid
     * @return
     */
    @GetMapping("getHandleText/{pid}")
    public ReturnBean<List<Map<String,Object>>> getHandleText(@PathVariable("pid") String pid){
        return myTaskService.getHandleText(pid);
    }

    @GetMapping("getAllActivitiId/{pid}")
    public ReturnBean<List<Map<String,Object>>> getAllActivitiId(@PathVariable("pid") String pid){
        return myTaskService.getAllActivitiId(pid);
    }

    @GetMapping("getNextTaskInfo/{taskId}")
    public ReturnBean getNextTaskInfo(@PathVariable("taskId") String taskId){
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        return   myTaskService.findNextTaskInfo(task.getProcessInstanceId(),taskId);
    }
}
