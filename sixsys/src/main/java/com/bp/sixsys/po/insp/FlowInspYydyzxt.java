package com.bp.sixsys.po.insp;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
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
 * @Description: 运用第一种形态通知单实体类
 * @date 2019年12月02日
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "运用第一种形态通知单")
@TableName(value = "flow_insp_yydyzxt")
public class FlowInspYydyzxt extends BaseEntityUUID {

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
     * 运用对象
     */
    @ApiModelProperty(value = "运用对象")
    private String yydx;

    /**
     * 单位部门
     */
    @ApiModelProperty(value = "单位部门")
    private String dwbm;

    /**
     * 运用时间
     */
    @ApiModelProperty(value = "运用时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date yysj;

    /**
     * 处置方式
     */
    @ApiModelProperty(value = "处置方式")
    private String czfs;

    /**
     * 职务
     */
    @ApiModelProperty(value = "职务")
    private String zw;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private String xb;

    /**
     * 政治面貌
     */
    @ApiModelProperty(value = "政治面貌")
    private String zzmm;

    /**
     * 谈话地点
     */
    @ApiModelProperty(value = "谈话地点")
    private String thdd;

    /**
     * 支部书记
     */
    @ApiModelProperty(value = "支部书记")
    private String zbsj;

    /**
     * 内容概要
     */
    @ApiModelProperty(value = "内容概要")
    private String nrgy;


}