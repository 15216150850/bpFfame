package com.bp.model.sys;

import com.bp.common.base.BaseEntityUUID;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * @author 钟欣凯
 * @version 1.0
 * @Description: 请假流程实体类
 * @date 2019年07月19日
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "请假流程")
public class MyLeaves extends BaseEntityUUID {

    /**
     * 请假理由
     */
    @ApiModelProperty(value = "请假理由")
    private String qjly;

    /**
     * 请假天数
     */
    @ApiModelProperty(value = "请假天数")
    private Integer qjts;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private String actTitle;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private String pid;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private String docState;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private String flowSn;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private Integer flowState;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private String remark;


}