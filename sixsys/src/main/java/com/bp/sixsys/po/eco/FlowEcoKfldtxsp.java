package com.bp.sixsys.po.eco;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bp.common.base.BaseEntityUUID;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * @author yanjisheng
 * @version 1.0
 * @Description: 康复劳动调休审批实体类
 * @date 2019年10月12日
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "康复劳动调休审批")
@TableName(value = "flow_eco_kfldtxsp")
public class FlowEcoKfldtxsp extends BaseEntityUUID {

    /**
     * 调休原因
     */
    @ApiModelProperty(value = "调休原因")
    private String txyy;

    /**
     * 单位
     */
    @ApiModelProperty(value = "单位")
    private String bm;


    /**
     * 人数
     */
    @ApiModelProperty(value = "人数")
    private String rs;

    /**
     * 调休日期
     */
    @ApiModelProperty(value = "调休日期")

    private String txrq;

    /**
     * 补休日期
     */
    @ApiModelProperty(value = "补休日期")
    private String bxrq;

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
     * 流程标题
     */
    @ApiModelProperty(value = "流程标题")
    private String actTitle;

    /**
     * 流程实例id
     */
    @ApiModelProperty(value = "流程实例id")
    private String pid;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
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
     * 补休情况
     */
    @ApiModelProperty(value = "补休情况")
    private String bxqk;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String bz;

    /**
     * 部门
     */
    @TableField(exist = false)
    private String bmname;


}