package com.bp.act.service;

import com.alibaba.fastjson.JSON;
import com.bp.act.bean.ActRollBackEntity;
import com.bp.act.bean.ReturnBean;
import com.bp.act.client.FileStoreClient;
import com.bp.act.contants.SysConstant;
import com.bp.act.entity.ActFinishTask;
import com.bp.act.entity.ActKeyUrl;
import com.bp.act.entity.NextTaskInfo;
import com.bp.act.entity.SysUser;
import com.bp.act.entity.online.Workbench;
import com.bp.act.entity.process.ActUserTask;
import com.bp.act.mapper.ActUserTaskMapper;
import com.bp.act.mapper.ProcessMapper;
import com.bp.act.utils.Common;
import com.bp.act.utils.DateUtil;
import com.bp.act.utils.TypeConversionUtils;
import com.bp.act.utils.UserUtils;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.NoneEndEventActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Attachment;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.*;

/**
 * 个人任务服务层
 *
 * @author 钟欣凯
 * @date: 2019/6/12 11:04
 */
@Service
public class MyTaskService {


    @Autowired
    private TaskService taskService;


    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FileStoreClient fileStoreClient;

    @Autowired
    private ProcessMapper processMapper;

    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private ActFinishTaskService actFinishTaskService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private ActUserTaskMapper actUserTaskMapper;

    /**
     * 代办任务
     *
     * @param para
     * @return
     */
    public ReturnBean<List<Workbench>> todoList(Map<String, Object> para) {

        Integer pageNum = Integer.valueOf(para.get(SysConstant.PAGE).toString());
        Integer pageSize = Integer.valueOf(para.get(SysConstant.LIMIT).toString());
        String keyWord = Common.getObjStr(para.get("keyWord"));
        List<Workbench> list = new ArrayList<Workbench>();
        String lclx = Common.getObjStr(para.get("lclx"));
        String actTitle = Common.getObjStr(para.get("actTitle"));
        String currentUserId = UserUtils.getCurrentUser().getId().toString();
        //查询个人任务总条数
        long tasksSize = 0L;
        //查询个人任务组任务总条数
        List<Task> tasks = new ArrayList<>();
        //查询个人任务list
        tasksSize = taskService.createTaskQuery().taskNameLike("%" + keyWord + "%").processVariableValueLike("actTitle", "%" + actTitle + "%").
                processDefinitionNameLike("%" + lclx + "%")
                .taskCandidateUser(currentUserId).list().size();
        tasks = taskService.createTaskQuery()
                .taskNameLike("%" + keyWord + "%").processVariableValueLike("actTitle", "%" + actTitle + "%")
                .processDefinitionNameLike("%" + lclx + "%").orderByTaskCreateTime().desc()
                .taskCandidateOrAssigned(currentUserId).listPage((pageNum - 1) * pageSize, pageSize);
        for (Task t : tasks) {
            ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(t.getProcessInstanceId()).singleResult();
            HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery().processInstanceId(t.getProcessInstanceId()).singleResult();
            Map runtimeVal = runtimeService.getVariables(t.getExecutionId());
            Workbench workbench = new Workbench();
            workbench.setId(t.getId());
            workbench.setTaskDefinitionKey(t.getTaskDefinitionKey());
            workbench.setProcessDefinitionId(t.getProcessDefinitionId());
            workbench.setProcessDefinitionKey(pi.getProcessDefinitionKey());
            workbench.setProcessInstanceId(t.getProcessInstanceId());
            workbench.setDueDate(t.getDueDate());
            workbench.setAssignee(t.getAssignee());
            workbench.setFlowName(pi.getProcessDefinitionName());
            workbench.setFlowNode(t.getName());
            workbench.setActTitle(Common.getObjStr(runtimeVal.get("actTitle")));
            workbench.setProposer(Common.getObjStr(runtimeVal.get("startUserName")));
            workbench.setCreateTime(hpi.getStartTime());
            workbench.setFlowSn(Common.getObjStr(runtimeVal.get("flowSn")));
            workbench.setDescription(Common.getObjStr(runtimeVal.get("descripiton")));
            list.add(workbench);
        }
        return ReturnBean.list(list, tasksSize);
    }

    /**
     * 办理任务
     *
     * @param para 办理参数
     * @return 办理结果
     */
    @SuppressWarnings("Duplicates")
    @Transactional(rollbackFor = Exception.class)
    public ReturnBean handleTask(Map para) {
        if ("reject".equals(para.get("msg"))) {
            this.taksReject(para);
            return ReturnBean.ok("任务办理成功");
        }
        Map<String, Object> variables = new HashMap<>();
        Set<String> set = para.keySet();
        for (String o :
                set) {
            variables.put(o, para.get(o));
        }
        String taskId = para.get("taskId").toString();
        String pId = para.get("pId").toString();
        String pKey = para.get("pKey").toString();
        String comment = para.get("comment").toString();
        String msg = para.get("msg").toString();
        String definejcbms = "";
        String selectMsg = "";
        String selectMessage = "";
        String selectState = "";
        if (para.get("selectMessage") != null) {
            selectMessage = para.get("selectMessage").toString();
        }
        if (para.get("selectMsg") != null) {
            selectMsg = para.get("selectMsg").toString();
        }
        String signName = "";
        if (para.get("signName") != null) {
            signName = para.get("signName").toString();
        }
        if (para.get("selectState") != null) {
            selectState = para.get("selectState").toString();
        } else {
            selectState = msg;
        }
        if (para.get("definejcbms")!=null){
            definejcbms = para.get("definejcbms").toString();
        }

        ActKeyUrl actKeyUrl = processMapper.selectKeyUrlByKey(pKey);
        String baseUrl = actKeyUrl.getBaseUrl();
        //todo
        String fileUuid = "";
        if (StringUtils.isNotEmpty(Common.getObjStr(para.get("fileUuid")))) {
            fileUuid = para.get("fileUuid").toString();
            //修改文件状态
            fileStoreClient.updateFileStatus(fileUuid);
        }

        SysUser user = UserUtils.getCurrentUser();
        //判断该任务是否为组任务，若为组任务，则让当前登陆人领取任务
        Task task = taskService
                .createTaskQuery()//
                .taskCandidateUser(user.getId().toString()).taskId(taskId).singleResult();
        if (task != null) {
            taskService.claim(taskId, user.getId().toString());
        } else {
            task = taskService.createTaskQuery().taskAssignee(user.getId().toString()).taskId(taskId).singleResult();
        }


        try {
            variables.put("selectMsg", selectMsg);
            variables.put("comment", comment);
            variables.put("fileUuid", fileUuid);
            variables.put("signName", signName);
            variables.put("msg", msg);
            variables.put("map", para);
            variables.put("definejcbms",definejcbms);
            variables.putAll(para);
            Authentication.setAuthenticatedUserId(user.getName());
            taskService.addComment(taskId, pId, comment + ":" + selectState);
            taskService.createAttachment("file", task.getId(),
                    pId, "", null, fileUuid);
            ProcessInstance pi = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(pId)
                    .singleResult();
            HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(task.getProcessInstanceId()).singleResult();
            //设置办理记录的参数
            ActFinishTask actFinishTask = new ActFinishTask();
            actFinishTask.setActName(task.getName());
            actFinishTask.setActUserId(user.getId());
            actFinishTask.setEndTime(new Date());
            actFinishTask.setInsertTime(new Date());
            actFinishTask.setProincId(pId);
            actFinishTask.setActTitle((String) taskService.getVariable(taskId, "actTitle"));
            actFinishTask.setAppliTime(hpi.getStartTime());
            actFinishTask.setApplicant((String) taskService.getVariable(taskId, "startUser"));
            actFinishTask.setKey(pKey);
            actFinishTask.setAppliName(pi.getProcessDefinitionName()).setComment(comment).setFileUuid(fileUuid)
                    .setSignName(signName).setMsg(selectState).setSelectMsg(selectMsg);
            actFinishTask.setFlowSn((String) runtimeService.getVariable(task.getExecutionId(), "flowSn"));
            taskService.complete(taskId, variables);

            /*
              判断流程是否结束，若流程结束，则执行相应的代码逻辑
             */
            //判断流程实例是否还存在
            String stateMsg = null;
            if (selectMessage != null && !"".equals(selectMessage)) {

                stateMsg = selectMessage;
            } else {
                stateMsg = msg;
            }
            pi = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(pId)
                    .singleResult();
            String[] split = baseUrl.split("/");
            Integer flowState = 0;
            if (pi == null) {
                //流程实例不存在，则流程已经走完了
                if ("关闭".equals(msg)) {
                    flowState = 4;
                } else {
                    flowState = 2;
                }
                restTemplate.getForObject("http://" + baseUrl + "/api/updateFlowState/" + pId + "/" + "流程已完成" + "/" + flowState,
                        ReturnBean.class);
                actFinishTask.setCurrentState(task.getName() + msg);
                ActRollBackEntity actRollBackEntity = new ActRollBackEntity();
                actRollBackEntity.setPid(pId).setComment
                        (comment).setCurrentUserId(UserUtils.getCurrentUser().getId()).setMsg(msg).
                        setTaskName(task.getName()).setSelectMsg(selectMsg).setSignName(signName).setFileUuid(fileUuid).setPkey(pKey).setMap(para).setBaseUrl(baseUrl);
                //任务结束时执行的业务逻辑
                HttpHeaders requestHeaders = new HttpHeaders();
                MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
                requestHeaders.setContentType(type);
                String s = JSON.toJSONString(actRollBackEntity);
                HttpEntity<String> requestEntity = new HttpEntity<String>(s, requestHeaders);
                restTemplate.postForObject("http://" + baseUrl + "/api/doFinishBusiness/", requestEntity,
                        ReturnBean.class);
            } else {

                if ("close".equals(para.get("msg"))) {
                    //直接结束流程
                    flowState = 4;

                    restTemplate.getForObject("http://" + baseUrl + "/api/updateFlowState/" + pId + "/流程关闭" + "/" + flowState,
                            ReturnBean.class);
                    runtimeService.deleteProcessInstance(pId, null);
                } else {
                    flowState = 5;
                    restTemplate.getForObject("http://" + baseUrl + "/api/updateFlowState/" + pId + "/null" + "/" + flowState,
                            ReturnBean.class);
                }
                //流程进行中

                actFinishTask.setCurrentState(task.getName() + msg);
            }
            //将已经办理的任务插入到数据库当中
            actFinishTaskService.insert(actFinishTask);
            actFinishTaskService.updateCurrentStateByPid(pId, task.getName());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("办理失败");
        }

        return ReturnBean.ok("办理成功");
    }

    /**
     * 流程驳回处理的业务
     *
     * @param para
     */
    private void taksReject(Map para) {
        Map<String, Object> variables = new HashMap<>();

        Set<String> set = para.keySet();
        for (String o :
                set) {
            variables.put(o, para.get(o));
        }
        String taskId = para.get("taskId").toString();
        String pId = para.get("pId").toString();
        String pKey = para.get("pKey").toString();
        String comment = para.get("comment").toString();
        String msg = para.get("msg").toString();
        String selectMsg = "";
        String selectMessage = "";
        String selectState = "";
        String rollTaksKey = para.get("rollTaksKey").toString();
        if (para.get("selectMessage") != null) {
            selectMessage = para.get("selectMessage").toString();
        }
        if (para.get("selectMsg") != null) {
            selectMsg = para.get("selectMsg").toString();
        }
        String signName = "";
        if (para.get("signName") != null) {
            signName = para.get("signName").toString();
        }
        if (para.get("selectState") != null) {
            selectState = para.get("selectState").toString();
        } else {
            selectState = msg;
        }
        ExecutionEntity entity = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(pId).singleResult();
        ProcessDefinitionEntity definition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                .getDeployedProcessDefinition(entity.getProcessDefinitionId());
        variables = entity.getProcessVariables();
        //当前活动环节
        ActivityImpl currActivityImpl = definition.findActivity(entity.getActivityId());
        //目标活动节点
        ActivityImpl nextActivityImpl = ((ProcessDefinitionImpl) definition).findActivity(rollTaksKey);
        if (currActivityImpl != null) {
            //所有的出口集合
            List<PvmTransition> pvmTransitions = currActivityImpl.getOutgoingTransitions();
            List<PvmTransition> oriPvmTransitions = new ArrayList<PvmTransition>();
            for (PvmTransition transition : pvmTransitions) {
                oriPvmTransitions.add(transition);
            }

            //清除所有出口
            pvmTransitions.clear();
            //建立新的出口
            List<TransitionImpl> transitionImpls = new ArrayList<>();
            TransitionImpl tImpl = currActivityImpl.createOutgoingTransition();
            tImpl.setDestination(nextActivityImpl);
            transitionImpls.add(tImpl);

//            List<Task> list = taskService.createTaskQuery().processInstanceId(entity.getProcessInstanceId())
//                    .taskDefinitionKey(entity.getActivityId()).list();
//            for (Task task : list) {
//                taskService.complete(task.getId(), variables);
////                historyService.deleteHistoricTaskInstance(task.getId());
//            }


            ActKeyUrl actKeyUrl = processMapper.selectKeyUrlByKey(pKey);
            String baseUrl = actKeyUrl.getBaseUrl();
            //todo
            String fileUuid = "";
            if (StringUtils.isNotEmpty(Common.getObjStr(para.get("fileUuid")))) {
                fileUuid = para.get("fileUuid").toString();
                //修改文件状态
                fileStoreClient.updateFileStatus(fileUuid);
            }

            SysUser user = UserUtils.getCurrentUser();
            //判断该任务是否为组任务，若为组任务，则让当前登陆人领取任务
            Task task = taskService
                    .createTaskQuery()//
                    .taskCandidateUser(user.getId().toString()).taskId(taskId).singleResult();
            if (task != null) {
                taskService.claim(taskId, user.getId().toString());
            } else {
                task = taskService.createTaskQuery().taskAssignee(user.getId().toString()).taskId(taskId).singleResult();
            }


            variables.put("selectMsg", selectMsg);

            variables.put("comment", comment);
            variables.put("fileUuid", fileUuid);
            variables.put("signName", signName);
            variables.put("msg", msg);
            variables.put("map", para);
            variables.putAll(para);
            Authentication.setAuthenticatedUserId(user.getName());
            taskService.addComment(taskId, pId, comment + ":" + selectState + ":" + msg);
            taskService.createAttachment("file", task.getId(),
                    pId, "", null, fileUuid);
            ProcessInstance pi = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(pId)
                    .singleResult();
            HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(task.getProcessInstanceId()).singleResult();
            //设置办理记录的参数
            ActFinishTask actFinishTask = new ActFinishTask();
            actFinishTask.setActName(task.getName());
            actFinishTask.setActUserId(user.getId());
            actFinishTask.setEndTime(new Date());
            actFinishTask.setInsertTime(new Date());
            actFinishTask.setProincId(pId);
            actFinishTask.setActTitle((String) taskService.getVariable(taskId, "actTitle"));
            actFinishTask.setAppliTime(hpi.getStartTime());
            actFinishTask.setApplicant((String) taskService.getVariable(taskId, "startUser"));
            actFinishTask.setKey(pKey);
            actFinishTask.setAppliName(pi.getProcessDefinitionName()).setComment(comment).setFileUuid(fileUuid)
                    .setSignName(signName).setMsg(selectState).setSelectMsg(selectMsg);
            actFinishTask.setFlowSn((String) runtimeService.getVariable(task.getExecutionId(), "flowSn"));
            taskService.complete(taskId, variables);
            int flowState = 5;
            String stateMsg = null;
            if (selectMessage != null && !"".equals(selectMessage)) {

                stateMsg = selectMessage;
            } else {
                stateMsg = msg;
            }
            restTemplate.getForObject("http://" + baseUrl + "/api/updateFlowState/" + pId + "/null" + "/" + flowState,
                    ReturnBean.class);
            actFinishTask.setCurrentState(task.getName() + msg);
            actFinishTaskService.insert(actFinishTask);
            actFinishTaskService.updateCurrentStateByPid(pId, task.getName());
            for (TransitionImpl transitionImpl : transitionImpls) {
                currActivityImpl.getOutgoingTransitions().remove(transitionImpl);
            }

            for (PvmTransition pvmTransition : oriPvmTransitions) {

                pvmTransitions.add(pvmTransition);

            }
        }
    }

    /**
     * 根据流程实例ID查询历史办理情况
     *
     * @param pid 流程实例ID
     * @return 查询结果
     */
    public ReturnBean<Map<String, Object>> selectHistoryList(String pid) {

        Map<String, Object> resultMap = new HashMap<>(16);
        ProcessInstance pi = null;
        pi = runtimeService.createProcessInstanceQuery().processInstanceId(pid)
                .singleResult();
        List<Map> commentList = new ArrayList<>();
        List<Map> attachmentList = new ArrayList<>();
        List<HistoricActivityInstance> hais = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(pid).activityType("userTask").list();
        for (HistoricActivityInstance hai : hais) {
            String historytaskId = hai.getTaskId();
            List<Comment> comments = taskService.getTaskComments(historytaskId);
            String acyivityName = hai.getActivityName();
            for (Comment comment :
                    comments) {
                Date time = comment.getTime();
                String timeStr = DateUtil.dateToString("yyyy-MM-dd HH:mm:ss", time);

                Map map = TypeConversionUtils.objToMap(comment);

                map.put("commentTime", timeStr);
                map.put("activitiName", acyivityName);
                String message = Common.getObjStr(map.get("message"));
                try {
                    map.put("selectState", message.split(":")[1]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                map.put("fullMessage", message.split(":")[0]);
                commentList.add(map);
            }
            List<Attachment> attachments = taskService.getTaskAttachments(historytaskId);
            for (Attachment attachment :
                    attachments) {
                attachmentList.add(TypeConversionUtils.objToMap(attachment));
            }
        }
        resultMap.put("commentList", commentList);
        resultMap.put("attachmentList", attachmentList);
        return ReturnBean.ok(resultMap);
    }

    /**
     * 批量办理任务
     *
     * @param para
     * @return
     */
    @SuppressWarnings("Duplicates")
    @Transactional(rollbackFor = Exception.class)
    public ReturnBean betchHandleTask(Map para) {
        Map<String, Object> variables = new HashMap<>();
        Set<String> set = para.keySet();
        for (String o : set) {
            variables.put(o, para.get(o));
        }
        String taskIdPids = para.get("taskIdPids").toString();
        String pKey = para.get("pKey").toString();
        String comment = para.get("comment").toString();
        String msg = para.get("msg").toString();
        Object userTaskIds = para.get("userTaskIds");

        String selectMsg = "";
        String selectMessage = "";
        if (para.get("selectMessage") != null) {
            selectMessage = para.get("selectMessage").toString();
        }
        if (para.get("selectMsg") != null) {
            selectMsg = para.get("selectMsg").toString();
        }
        String signName = "";
        if (para.get("signName") != null) {
            signName = para.get("signName").toString();
        }
        String selectState = "";
        if (para.get("selectState") != null) {
            selectState = para.get("selectState").toString();
        } else {
            selectState = msg;
        }
        ActKeyUrl actKeyUrl = processMapper.selectKeyUrlByKey(pKey);
        String baseUrl = actKeyUrl.getBaseUrl();
        String[] taskIdPidsArr = taskIdPids.split(",");
        for (String taskIdPid :
                taskIdPidsArr) {
            String[] split = taskIdPid.split(":");
            String taskId = split[0];
            String pId = split[1];
            SysUser user = UserUtils.getCurrentUser();
            //判断该任务是否为组任务，若为组任务，则让当前登陆人领取任务
            Task task = taskService
                    .createTaskQuery()//
                    .taskCandidateUser(user.getId().toString()).taskId(taskId).singleResult();
            if (task != null) {
                taskService.claim(taskId, user.getId().toString());
            } else {
                task = taskService.createTaskQuery().taskAssignee(user.getId().toString()).taskId(taskId).singleResult();
            }
            try {

                variables.put("selectMsg", selectMsg);
                variables.put("comment", comment);

                variables.put("msg", msg);
                variables.put("map", para);
                variables.putAll(para);
                variables.put("userTaskIds", userTaskIds);
                Authentication.setAuthenticatedUserId(user.getName());
                taskService.addComment(taskId, pId, comment + ":" + selectState);
                ProcessInstance pi = runtimeService.createProcessInstanceQuery()
                        .processInstanceId(pId)
                        .singleResult();
                HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery()
                        .processInstanceId(task.getProcessInstanceId()).singleResult();
                //设置办理记录的参数
                ActFinishTask actFinishTask = new ActFinishTask();
                actFinishTask.setActName(task.getName());
                actFinishTask.setActUserId(user.getId());
                actFinishTask.setEndTime(new Date());
                actFinishTask.setProincId(pId);
                actFinishTask.setActTitle((String) taskService.getVariable(taskId, "actTitle"));
                actFinishTask.setAppliTime(hpi.getStartTime());
                SysUser sysUser = UserUtils.getCurrentUser();
                actFinishTask.setApplicant(sysUser.getUsername());
                actFinishTask.setKey(pKey);
                actFinishTask.setAppliName(pi.getProcessDefinitionName()).setComment(comment)
                        .setSignName(signName).setMsg(selectState).setSelectMsg(selectMsg);
                actFinishTask.setFlowSn((String) runtimeService.getVariable(task.getExecutionId(), "flowSn"));
                taskService.complete(taskId, variables);
            /*
              判断流程是否结束，若流程结束，则执行相应的代码逻辑
             */
                //判断流程实例是否还存在
                String stateMsg = null;
                if (selectMessage != null && !"".equals(selectMessage)) {

                    stateMsg = selectMessage;
                } else {
                    stateMsg = msg;
                }
                pi = runtimeService.createProcessInstanceQuery()
                        .processInstanceId(pId)
                        .singleResult();
                String[] split1 = baseUrl.split("/");
                Integer flowState = 0;
                if (pi == null) {
                    if ("关闭".equals(msg)) {
                        flowState = 4;
                    } else {
                        flowState = 2;
                    }
                    restTemplate.getForObject("http://" + baseUrl + "/api/updateFlowState/" + pId + "/" + "流程已完成" + "/" + flowState,
                            ReturnBean.class);
                    actFinishTask.setCurrentState(task.getName() + msg);
                    ActRollBackEntity actRollBackEntity = new ActRollBackEntity();
                    actRollBackEntity.setPid(pId).setComment
                            (comment).setCurrentUserId(UserUtils.getCurrentUser().getId()).setMsg(msg).
                            setTaskName(task.getName()).setSelectMsg(selectMsg).setPkey(pKey).setMap(para).setBaseUrl(baseUrl);
                    //任务结束时执行的业务逻辑
                    HttpHeaders requestHeaders = new HttpHeaders();
                    MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
                    requestHeaders.setContentType(type);
                    String s = JSON.toJSONString(actRollBackEntity);
                    HttpEntity<String> requestEntity = new HttpEntity<String>(s, requestHeaders);
                    restTemplate.postForObject("http://" + baseUrl + "/api/doFinishBusiness/", requestEntity,
                            ReturnBean.class);
                } else {
                    //流程进行中
                    flowState = 5;
                    restTemplate.getForObject("http://" + baseUrl + "/api/updateFlowState/" + pId + "/null" + "/" + flowState,
                            ReturnBean.class);
                    actFinishTask.setCurrentState(task.getName() + msg);
                }
                //将已经办理的任务插入到数据库当中
                actFinishTaskService.insert(actFinishTask);

            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("办理失败");
            }
        }

        return ReturnBean.ok("批量办理任务成功");
    }

    /**
     * 获取任务开始节点的任务ID
     *
     * @param userId
     * @param pid
     * @return
     */
    public ReturnBean<String> findStartTaskId(Integer userId, String pid) {
        Task task = taskService.createTaskQuery().taskCandidateUser(userId.toString()).processInstanceId(pid)
                .singleResult();

        return ReturnBean.ok(task.getId());
    }

    private boolean findStr(String[] args, String str) {
        for (String s : args) {
            if (s.equals(str)) {
                return true;
            }
        }

        return false;
    }


    /**
     * 获取下一任务节点信息
     *
     * @param pid
     * @return
     */
    public ReturnBean<List<NextTaskInfo>> findNextTaskInfo(String pid, String taskId) {
        //流程标示
        String processDefinitionId = historyService.createHistoricProcessInstanceQuery().processInstanceId(pid)
                .singleResult().getProcessDefinitionId();

        ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                .getDeployedProcessDefinition(processDefinitionId);

        ExecutionEntity execution = (ExecutionEntity) runtimeService.createProcessInstanceQuery()
                .processInstanceId(pid).singleResult();
        //当前实例的执行到哪个节点
        String activitiId = execution.getActivityId();
        //获得当前任务的所有节点
        List<ActivityImpl> activitiList = def.getActivities();
        String id = null;
        for (ActivityImpl activityImpl : activitiList) {
            id = activityImpl.getId();
            if (activitiId.equals(id)) {
                System.out.println("当前任务：" + activityImpl.getProperty("name"));
                TaskDefinition taskDefinition = nextTaskDefinition(activityImpl, activityImpl.getId(), taskId);
                if (taskDefinition == null) {
                    return ReturnBean.ok();
                }
                Set<Expression> candidateUserIdExpressions = taskDefinition.getCandidateUserIdExpressions();
                String expressionText = "";
                for (Expression expression :
                        candidateUserIdExpressions) {
                    expressionText = expression.getExpressionText();
                }
                Map<String, Object> variables = taskService.getVariables(taskId);
                List<NextTaskInfo> nextTaskInfos = new ArrayList<>();
                String substring = expressionText.substring(2, expressionText.length() - 1);
                String userIds = (String) taskService.getVariable(taskId, expressionText.substring(2, expressionText.length() - 1));
                ActUserTask actUserTask = actUserTaskMapper.findByPidAndTaskKey(def.getKey(), taskDefinition.getKey());
                NextTaskInfo nextTaskInfo = new NextTaskInfo();
                nextTaskInfo.setNextTaskName(taskDefinition.getNameExpression().getExpressionText());
                nextTaskInfo.setPid(pid);
                if (actUserTask!=null && !StringUtils.isEmpty(actUserTask.getJcbms())){
                    nextTaskInfo.setJcbms(actUserTask.getJcbms());{
                        nextTaskInfo.setJcbms(actUserTask.getJcbms());
                    }
                } else {
                    nextTaskInfo.setUserTaskIds(userIds);
                }
                nextTaskInfos.add(nextTaskInfo);
                return ReturnBean.ok(nextTaskInfos);

            }
        }
        throw new RuntimeException("发生未知错误");
    }

    /**
     * 获取下一任务节点的业务逻辑
     *
     * @param activityImpl
     * @param activityId
     * @param taskId
     * @return
     */
    private TaskDefinition nextTaskDefinition(ActivityImpl activityImpl, String activityId, String taskId) {
        if ("userTask".equals(activityImpl.getProperty("type")) && !activityId.equals(activityImpl.getId())) {
            return ((UserTaskActivityBehavior) activityImpl.getActivityBehavior()).getTaskDefinition();
        } else {
            List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();
            List<PvmTransition> outTransitionsTemp = null;
            for (PvmTransition tr : outTransitions) {
                //获取线路的终点节点
                PvmActivity ac = tr.getDestination();
                Object type = ac.getProperty("type");
                System.out.println(type);
                outTransitionsTemp = ac.getOutgoingTransitions();
                Object conditionText = tr.getProperty("conditionText");
                Object documentation = tr.getProperty("documentation");

                System.out.println(conditionText);

                if (outTransitionsTemp.size() == 1) {
                    return nextTaskDefinition((ActivityImpl)ac, activityId, taskId);
                } else {
                        if ("exclusiveGateway".equals(ac.getProperty("type"))) {
                            String id = ac.getId();
                            String val = (String) taskService.getVariable(taskId, id);
                            Map<String, Object> variables = taskService.getVariables(taskId);
                            for (PvmTransition pvmTransition :
                                    outTransitionsTemp) {
                                if (pvmTransition.getProperty("conditionText").equals(val)) {
                                    return nextTaskDefinition((ActivityImpl) pvmTransition.getDestination(), activityId, taskId);
                                }
                            }
                            return nextTaskDefinition((ActivityImpl) outTransitionsTemp.get(0).getDestination(), activityId, taskId);
                        }
                        return nextTaskDefinition((ActivityImpl) tr.getDestination(), activityId, taskId);

                }

            }
            return null;
        }
    }

    /**
     * 获取已办理完成的任务节点
     *
     * @param pid
     * @return
     */
    public ReturnBean<List<Map<String, Object>>> findFinishTaskByPid(String pid) {
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().finished().processInstanceId(pid).list();

        List<Map<String, Object>> resultMap = new ArrayList<>();

        for (HistoricTaskInstance historicTaskInstance :
                list) {
            Map<String, Object> map = new HashMap<>();
            map.put("taskName", historicTaskInstance.getName());
            map.put("taskKey", historicTaskInstance.getTaskDefinitionKey());
            System.out.println(historicTaskInstance.getExecutionId());

//            System.out.println(list1);
            resultMap.add(map);
        }
        return ReturnBean.ok(resultMap);
    }

    public void getFlowNode(String pid) {

        List<Task> list = taskService.createTaskQuery().processInstanceId(pid).list();
        InputStream in = null;
        byte[] b = new byte[1024];
        try {
            ServletOutputStream sos = response.getOutputStream();
            in = imgs(pid);
            //文件名
            String src3 = "flowNode.png";
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder
                    .encode(src3, "UTF-8"));
            int i = in.read(b, 0, b.length);
            while (i != -1) {
                sos.write(b, 0, i);
                i = in.read(b, 0, b.length);
            }
            in.close();
            sos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private InputStream imgs(String processInstanceId) {


        //获取历史流程实例
        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
        ProcessEngineConfiguration processEngineConfiguration = processEngine.getProcessEngineConfiguration();
        Context.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);

        ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
        ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processInstance.getProcessDefinitionId());
        //高亮环节id集合
        List<String> highLightedActivitis = new ArrayList<String>();
        //高亮线路id集合
        List<Task> list = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        List<String> curentTask = new ArrayList<>();
        if (list.size() > 0) {
            for (Task task :
                    list) {
                curentTask.add(task.getTaskDefinitionKey());
            }
        } else {
            String processDefinitionId = processInstance.getProcessDefinitionId();
            ProcessDefinitionEntity processDef = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
            List<ActivityImpl> activitiList = processDef.getActivities();
            for (ActivityImpl activity :
                    activitiList) {
                ActivityBehavior activityBehavior = activity.getActivityBehavior();
                if (activityBehavior instanceof NoneEndEventActivityBehavior) {
                    curentTask.add(activity.getId());
                }

            }

        }
        //中文显示的是口口口，设置字体就好了
        InputStream imageStream = diagramGenerator.generateDiagram(bpmnModel, "png", curentTask, new ArrayList<>(), "宋体", "宋体", "宋体", null, 1.0);
        //单独返回流程图，不高亮显示
        return imageStream;

    }

    /**
     * 获取审批过程的文字，用于APP
     *
     * @param pid
     * @return
     */
    public ReturnBean<List<Map<String, Object>>> getHandleText(String pid) {

        List<HistoricActivityInstance> hais = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(pid).activityType("userTask").list();
        List<Map<String, Object>> maps = new ArrayList<>();
        for (HistoricActivityInstance hai : hais) {
            String historytaskId = hai.getTaskId();
            String activityId = hai.getActivityId();
            List<Comment> comments = taskService.getTaskComments(historytaskId);
            String acyivityName = hai.getActivityName();
            for (Comment comment :
                    comments) {
                Map<String, Object> resultMap = new HashMap<>(16);
                Map map = TypeConversionUtils.objToMap(comment);
                String message = Common.getObjStr(map.get("message"));
                resultMap.put("userId", comment.getUserId());
                resultMap.put("activityId", activityId);
                String msg = "";
                try {
                    msg = message.split(":")[2];
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String state = "1";
                if ("关闭".equals(msg) || "reject".equals(msg)) {
                    state = "0";
                }
                String selectState = "";
                try {
                    selectState = message.split(":")[1];
                } catch (Exception e) {
                    e.printStackTrace();
                }
                map.put("fullMessage", message.split(":")[0]);
                resultMap.put("state", state);
                resultMap.put("selectState", selectState);
                resultMap.put("activitiName", acyivityName);
                maps.add(resultMap);

            }

        }
        return ReturnBean.ok(maps);
    }

    public ReturnBean<List<Map<String, Object>>> getAllActivitiId(String pid) {
        List<Map<String, Object>> maps = new ArrayList<>();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(pid).singleResult();
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(pid).singleResult();
        String pdfId = null;
        if (processInstance != null){
            pdfId = processInstance.getProcessDefinitionId();
        } else {
            pdfId = historicProcessInstance.getProcessDefinitionId();
        }
        ProcessDefinition processDefinition = repositoryService.getProcessDefinition(pdfId);

        try {

            ProcessDefinitionEntity processDef = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinition.getId());
            //获得当前任务的所有节点
            List<ActivityImpl> activitiList = processDef.getActivities();
            for (ActivityImpl activity : activitiList) {
                ActivityBehavior activityBehavior = activity.getActivityBehavior();
                boolean isFound = false;
                //是否为用户任务
                if (activityBehavior instanceof UserTaskActivityBehavior) {
                     Map<String,Object> map = new HashMap<>();
                     map.put("activityId",activity.getId());
                     map.put("actvityName",activity.getProperties().get("name"));
                     maps.add(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ReturnBean.ok(maps);
    }


//    public List<String> getFinishActvity(String pid) {
//        List<Task> list = taskService.createTaskQuery().processInstanceId(pid).list();
//        List<String> curentTask = new ArrayList<>();
//        if (list.size() > 0){
//            for (Task task :
//                    list) {
//                curentTask.add(task.getTaskDefinitionKey());
//            }
//        } else {
//            curentTask.add("")
//        }
//
//        return null;
//    }
}
