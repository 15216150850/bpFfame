package com.bp.act.entity;


import com.bp.act.entity.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * @author 钟欣凯
 * @version 1.0
 * @Description: 个人已办理的任务实体类
 * @date 2018年11月15日
 */
@Accessors(chain = true)
@Data
public class ActFinishTask extends BaseEntity {

    /**
     * 流程实例ID
     */
    private String proincId;

    /**
     * 活动名称
     */
    private String actName;

    /**
     * 任务办理人ID
     */
    private Integer actUserId;

    /**
     * 任务办理时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 申请人
     */
    private String applicant;

    /**
     * 流程单号
     */
    private String flowSn;

    /**
     * 流程标题
     */
    private String actTitle;

    /**
     * 申请时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date appliTime;

    /**
     * 流程当前的状态
     */
    private String currentState;
    /**
     * 申请名称
     */
    private String appliName;

    /**
     * 办理批注
     */
    private String comment;

    /**
     * 上传附件
     */
    private String fileUuid;


    /**
     * 签名
     */
    private String signName;

    /**
     * 办理意见
     */
    private String msg;

    private String selectMsg;

    /**
     * 任务办理人名称
     */
    private String actUserName;


    /**
     * 办理人姓名
     */
    private String xm;

    private String currentFinishState;

    private String key;

}