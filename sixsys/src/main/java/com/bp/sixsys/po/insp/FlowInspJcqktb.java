package com.bp.sixsys.po.insp;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.bp.common.base.BaseEntityUUID;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * @author pengwanli
 * @version 1.0
 * @Description: 规范权力运行检查情况通报实体类
 * @date 2019年12月02日
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "规范权力运行检查情况通报")
@TableName(value = "flow_insp_jcqktb")
public class FlowInspJcqktb extends BaseEntityUUID {

    /**
     * 流程标题
     */
    @ApiModelProperty(value = "流程标题")
    private String actTitle;

    /**
     * 流程实例ID
     */
    @ApiModelProperty(value = "流程实例ID")
    private String pid;

    /**
     * 流程备注
     */
    @ApiModelProperty(value = "流程备注")
    private String remark;

    /**
     * 单据状态
     */
    @ApiModelProperty(value = "单据状态")
    private String docState;

    /**
     * 流程编号
     */
    @ApiModelProperty(value = "流程编号")
    private String flowSn;

    /**
     * 流程状态
     */
    @ApiModelProperty(value = "流程状态")
    private Integer flowState;

    /**
     * 相关文件
     */
    @ApiModelProperty(value = "相关文件")
    private String xgwj;

    /**
     * 申请人
     */
    @ApiModelProperty(value = "申请人")
    private String sqr;

    /**
     * 申请人部门
     */
    @ApiModelProperty(value = "申请人部门")
    private String sqrbm;

    /**
     * 接收负责人
     */
    @ApiModelProperty(value = "接收负责人")
    private String jsfzr;

    /**
     * 接收负责人姓名
     */
    @ApiModelProperty(value = "接收负责人姓名")
    private String jsfzrName;

    /**
     * 通报内容
     */
    @ApiModelProperty(value = "通报内容")
    private String tbnr;

    /**
     * 回复内容
     */
    @ApiModelProperty(value = "回复内容")
    private String hfnr;


}