package com.bp.act.entity.process;

import lombok.Data;

import java.util.Date;

/**
 * 流程定义实体类
 *
 * @author 钟欣凯
 */
@Data
public class ProcessDefinitionVo {

    private static final long serialVersionUID = 1L;

    private String id;

    private String deploymentId;

    private String name;

    private String key;

    private int version;

    private Date deploymentTime;

    private String resourceName;

    private String diagramResourceName;


}
