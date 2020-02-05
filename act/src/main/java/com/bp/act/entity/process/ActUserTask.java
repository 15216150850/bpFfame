package com.bp.act.entity.process;


import com.bp.act.entity.base.BaseEntity;

import lombok.Data;

/**
 * @author
 * @version 1.0
 * @Description: 设定流程办理人实体类
 * @date 2018年11月19日
 */
@Data
public class    ActUserTask extends BaseEntity {

    /**
     *
     */
    private String procDefKey;

    /**
     *
     */
    private String procDefName;

    /**
     *
     */
    private String taskDefKey;

    /**
     *
     */
    private String taskName;

    /**
     *
     */
    private String taskType;

    /**
     *
     */
    private String candidateName;

    /**
     *
     */
    private String candidateIds;

    private String jcbms;


}