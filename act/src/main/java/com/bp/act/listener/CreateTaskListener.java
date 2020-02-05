package com.bp.act.listener;

import com.alibaba.fastjson.JSON;
import com.bp.act.bean.ActRollBackEntity;
import com.bp.act.bean.ReturnBean;
import com.bp.act.client.SysClient;
import com.bp.act.entity.LoginUser;
import com.bp.act.entity.process.ActUserTask;
import com.bp.act.mapper.ActUserTaskMapper;
import com.bp.act.service.ProcessService;
import com.bp.act.utils.UserUtils;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * 任务创建监听器
 * 做统一回调
 *
 * @auther: 钟欣凯
 * @date: 2019/6/12 16:22
 */
@Service("createTaskListener")
public class CreateTaskListener implements TaskListener {


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

    @Autowired
    private ActUserTaskMapper actUserTaskMapper;

    @Autowired
    private SysClient sysClient;

    @Override
    public void notify(DelegateTask delegateTask) {
        TaskEntity taskEntity = (TaskEntity) delegateTask;
        taskEntity.getProcessInstanceId();
        String executionId = taskEntity.getExecutionId();
        String pid = taskEntity.getProcessInstanceId();
        String key = taskEntity.getProcessInstance().getProcessDefinitionKey();
        String name = taskEntity.getName();
        String msg = "";
        String commnet = "";
        String selectMsg = "";
        String signName = "";
        String fileUuid = "";
        String definejcbms = "";
        if (runtimeService.getVariable(executionId, "msg") != null) {
            msg = (String) runtimeService.getVariable(executionId, "msg");
        }
        if (runtimeService.getVariable(executionId, "commnet") != null) {
            commnet = (String) runtimeService.getVariable(executionId, "commnet");
        }
        if (runtimeService.getVariable(executionId, "selectMsg") != null) {
            selectMsg = (String) runtimeService.getVariable(executionId, "selectMsg");
        }
        if (runtimeService.getVariable(executionId, "definejcbms") != null) {
            signName = (String) runtimeService.getVariable(executionId, "definejcbms");
        }
        if (runtimeService.getVariable(executionId, "fileUuid") != null) {
            fileUuid = (String) runtimeService.getVariable(executionId, "fileUuid");
        }
        if (runtimeService.getVariable(executionId, "definejcbms") != null) {
            definejcbms = (String) runtimeService.getVariable(executionId, "definejcbms");
        }
//        if (!StringUtils.isEmpty(runtimeService.getVariable(executionId, "userTaskIds"))) {
//            delegateTask.addCandidateUser(runtimeService.getVariable(executionId, "userTaskIds").toString());
//        }
//        if (!StringUtils.isEmpty(runtimeService.getVariable(executionId, "definejcbms"))) {
//            delegateTask.addCandidateUser(runtimeService.getVariable(executionId, "userTaskIds").toString());
//        }
        String baseUrl = processService.selectKeyUrlByKey(key).getBaseUrl();
        //作回调处理,各个服务之间自己定义接口
        String[] split = baseUrl.split("/");
        Set<IdentityLink> candidates = delegateTask.getCandidates();
        //动态设定任务办理人
        ActUserTask byPidAndTaskKey = actUserTaskMapper.findByPidAndTaskKey(key, delegateTask.getTaskDefinitionKey());
        if (!StringUtils.isEmpty(definejcbms)){
            for (IdentityLink identityLink :
                    candidates) {
                delegateTask.deleteCandidateUser(identityLink.getUserId());
            }
            String userIds = sysClient.findUserIdsByJcbms(definejcbms);
            userIds = userIds + ",1";
            String[] ids = userIds.split(",");
            List<String> strings = Arrays.asList(ids);
            delegateTask.addCandidateUsers(strings);
        } else {
            if (byPidAndTaskKey != null && !StringUtils.isEmpty(byPidAndTaskKey.getJcbms())) {
                //移除之前的任务候选人
                for (IdentityLink identityLink :
                        candidates) {
                    delegateTask.deleteCandidateUser(identityLink.getUserId());
                }
                String jcbms = byPidAndTaskKey.getJcbms();
                String userIds = sysClient.findUserIdsByJcbms(jcbms);
                userIds = userIds + ",1";
                String[] ids = userIds.split(",");
                List<String> strings = Arrays.asList(ids);
                delegateTask.addCandidateUsers(strings);
            }
        }


        String userIds = "";
        for (IdentityLink identityLink :
                candidates) {
            String userId = identityLink.getUserId();
            userIds = userId + ",";
        }
        Task task = null;
        System.out.println("任务办理人：" + candidates);
        ActRollBackEntity actRollBackEntity = new ActRollBackEntity();
        LoginUser currentUser = UserUtils.getCurrentUser();
        if (currentUser != null) {
            actRollBackEntity.setCurrentUserId(currentUser.getId());
        }
        actRollBackEntity.setPid(pid).setComment(commnet).
                setMsg(msg).
                setTaskName(name).setSelectMsg(selectMsg).setSignName(signName).setFileUuid(fileUuid).
                setUserIds(userIds.substring(0, userIds.length() - 1)).setPkey(key).setBaseUrl(baseUrl);
        //任务结束时执行的业务逻辑
        HttpHeaders requestHeaders = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        requestHeaders.setContentType(type);
        String s = JSON.toJSONString(actRollBackEntity);
        HttpEntity<String> requestEntity = new HttpEntity<String>(s, requestHeaders);
        restTemplate.postForObject("http://" + baseUrl + "/api/taskCreate", requestEntity,
                ReturnBean.class);
    }
}
