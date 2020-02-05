package com.bp.act.bean;


import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @author 钟欣凯
 * 流程参数，作业务回调使用
 */
@Accessors(chain = true)
@Data
public class ActRollBackEntity {

    /**
     * 呈报意见
     */
    private String comment;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 流程实例ID
     */
    private String pid;

    /**
     * 连线名称
     */
    private String msg;

    /**
     * 当前用户的ID
     */
    private Integer currentUserId;


    /**
     * 同一连线的不同情况
     */
    private String selectMsg;

    /**
     * 审批文件uuid
     */
    private String fileUuid;


    /**
     * 签名
     */
    private String signName;

    /**
     * 任务办理人候选人，用逗号隔开
     */
    private String userIds;

    /**
     * 任务实际办理人
     */
    private String assignee;

    /**
     * 流程定义的key
     */
    private String pkey;

    private Map<String, Object> map;

    private String baseUrl;
}
