package com.bp.act.listener;

import com.alibaba.fastjson.JSON;

import com.bp.act.bean.ActRollBackEntity;
import com.bp.act.bean.ReturnBean;
import com.bp.act.service.ProcessService;


import com.bp.act.utils.UserUtils;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 任务结束监听器
 * 做统一回调
 *
 * @author : 钟欣凯
 * @date: 2019/6/12 16:22
 */
@Service("completeTaskListener")
public class CompleteTaskListener implements TaskListener {


    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ProcessService processService;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void notify(DelegateTask delegateTask) {
        String name = delegateTask.getName();
        String id = delegateTask.getId();
        Task task = taskService.createTaskQuery().taskId(id).singleResult();
        String msg = (String) runtimeService.getVariable(task.getExecutionId(), "msg");
        String processDefinitionId = task.getProcessDefinitionId();
        String executionId = task.getExecutionId();
        String comment = (String) runtimeService.getVariable(executionId, "comment");
        String selectMsg = (String) runtimeService.getVariable(executionId, "selectMsg");
        String signName = (String) runtimeService.getVariable(executionId, "signName");
        String fileUuid = (String) runtimeService.getVariable(executionId, "fileUuid");
        Object map = runtimeService.getVariable(executionId, "map");
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        String pid = task.getProcessInstanceId();
        String key = processDefinition.getKey();
        String baseUrl = processService.selectKeyUrlByKey(key).getBaseUrl();
        //作回调处理,各个服务之间自己定义接口
        String[] split = baseUrl.split("/");
//        "http://sys/"
        //设置传入回调方法的参数
        ActRollBackEntity actRollBackEntity = new ActRollBackEntity();
        actRollBackEntity.setPid(pid).setComment
                (comment).setCurrentUserId(UserUtils.getCurrentUser().getId()).setMsg(msg).
                setTaskName(name).setSelectMsg(selectMsg).setSignName(signName).
                setFileUuid(fileUuid).setAssignee(delegateTask.getAssignee()).setPkey(key).setMap((Map<String, Object>) map)
                .setBaseUrl(baseUrl);
        //任务结束时执行的业务逻辑
        HttpHeaders requestHeaders = new HttpHeaders();
        String assignee = delegateTask.getAssignee();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        requestHeaders.setContentType(type);
        String s = JSON.toJSONString(actRollBackEntity);
        HttpEntity<String> requestEntity = new HttpEntity<String>(s, requestHeaders);
        restTemplate.postForObject("http://" + baseUrl + "/api/taskComplete/", requestEntity,
                ReturnBean.class);

    }
}
