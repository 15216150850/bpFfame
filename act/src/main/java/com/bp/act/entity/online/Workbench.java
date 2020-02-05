package com.bp.act.entity.online;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 钟欣凯
 * 工作台显示代办和已办工作内容
 */
@Data
public class Workbench implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 任务ID
     */
    private String id;
    /**
     * 任务节点key
     */
    private String taskDefinitionKey;
    /**
     * 流程定义ID
     */
    private String processDefinitionId;
    /**
     * 流程定义key,与前台页面名称对应
     */
    private String processDefinitionKey;
    /**
     * 流程实例ID
     */
    private String processInstanceId;
    /**
     * 到期时间,暂时不用
     */
    private Date dueDate;
    /**
     * 办理人
     */
    private String assignee;
    /*页面显示以下几个字段*/
    /**
     * 流程名称
     */
    private String flowName;
    /**
     * 任务单号
     */
    private String flowSn;
    /**
     * 当前流程节点
     */
    private String flowNode;
    /**
     * 申请人
     */
    private String proposer;
    /**
     * 申请时间
     */
    private Date createTime;
    /**
     * 申请内容
     */
    private String description;
    /**
     * 流程标题
     */
    private String actTitle;


}
