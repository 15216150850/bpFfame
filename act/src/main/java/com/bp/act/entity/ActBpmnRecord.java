package com.bp.act.entity;


import com.bp.act.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author 钟欣凯
 * @version 1.0
 * @Description: 流程部署表实体类
 * @date 2019年06月13日
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "act_bpmn_record")
public class ActBpmnRecord extends BaseEntity {

    /**
     * 记录名称
     */
    @ApiModelProperty(value = "记录名称")
    private String name;

    /**
     * bpmn文件url
     */
    @ApiModelProperty(value = "bpmn文件url")
    private String bpmnUrl;

    /**
     * bpmn文件名称
     */
    @ApiModelProperty(value = "bpmn文件名称")
    private String bpmnName;

    /**
     * png文件url
     */
    @ApiModelProperty(value = "png文件url")
    private String pngUrl;

    /**
     * png文件名称
     */
    @ApiModelProperty(value = "png文件名称")
    private String pngName;

    /**
     * 是否部署(1:是;0:否)
     */
    @ApiModelProperty(value = "是否部署(1:是;0:否)")
    private Integer isDelopy;

    /**
     * 流程key
     */
    @ApiModelProperty(value = "流程key")
    private String flowKey;

    /**
     * 流程图片访问路径
     */
    @ApiModelProperty(value = "流程图片访问路径")
    private String imageUrl;

    /**
     * 流程bpmn访问路径
     */
    @ApiModelProperty(value = "流程bpmn访问路径")
    private String fileUrl;

}