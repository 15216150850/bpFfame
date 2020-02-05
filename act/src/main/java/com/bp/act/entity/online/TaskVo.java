package com.bp.act.entity.online;

import lombok.Data;

import java.util.Date;

@Data
public class TaskVo {

    private static final long serialVersionUID = 1L;

    private String id;

    private String taskDefinitionKey;

    private String name;

    private String processDefinitionId;

    private String processInstanceId;

    private Integer priority;

    private Date createTime;

    private Date dueDate;

    private String description;

    private String owner;

    private String assignee;


    public TaskVo() {
        super();
    }

    public TaskVo(String id, String taskDefinitionKey, String name, String processDefinitionId,
                  String processInstanceId, Integer priority, Date createTime, Date dueDate, String description, String owner,
                  String assignee) {
        super();
        this.id = id;
        this.taskDefinitionKey = taskDefinitionKey;
        this.name = name;
        this.processDefinitionId = processDefinitionId;
        this.processInstanceId = processInstanceId;
        this.priority = priority;
        this.createTime = createTime;
        this.dueDate = dueDate;
        this.description = description;
        this.owner = owner;
        this.assignee = assignee;
    }

}
