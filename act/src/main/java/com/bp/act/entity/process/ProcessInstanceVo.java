package com.bp.act.entity.process;


import com.bp.act.entity.online.TaskVo;
import lombok.Data;

/**
 * 流程实例实体类
 *
 * @author 钟欣凯
 */
@Data
public class ProcessInstanceVo {

    private static final long serialVersionUID = 1L;

    private String id;

    private String processInstanceId;

    private String processDefinitionId;

    private TaskVo task;


    public ProcessInstanceVo() {
        super();
    }

    public ProcessInstanceVo(String id, String processInstanceId, String processDefinitionId) {
        super();
        this.id = id;
        this.processInstanceId = processInstanceId;
        this.processDefinitionId = processDefinitionId;
    }
}
