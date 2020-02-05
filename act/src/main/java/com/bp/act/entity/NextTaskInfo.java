package com.bp.act.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 钟欣凯
 * <p>
 * 下一节点任务信息实体
 */
@Data
@ApiModel(value = "下一节点任务信息")
public class NextTaskInfo {

    @ApiModelProperty(name = "流程实例ID")
    private String pid;

    @ApiModelProperty(name = "下一任务办理人ID")
    private String userTaskIds;

    @ApiModelProperty(name = "下一任务名称")
    private String nextTaskName;

    @ApiModelProperty(name = "下一次任务警察编码")
    private String jcbms;
}
