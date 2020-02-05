package com.bp.sixsys.po.eco;

import java.math.BigDecimal;
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
 * @author zhangyu
 * @version 1.0
 * @Description: 打版定价实体类
 * @date 2019年11月25日
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "打版定价")
@TableName(value = "flow_eco_dbdj")
public class FlowEcoDbdj extends BaseEntityUUID {

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    private String khmc;

    /**
     * 品名/款号
     */
    @ApiModelProperty(value = "品名/款号")
    private String pmkh;

    /**
     * 报告人
     */
    @ApiModelProperty(value = "报告人")
    private String bgr;

    /**
     * 订单数量
     */
    @ApiModelProperty(value = "订单数量")
    private Float ddsl;

    /**
     * 预估日产量
     */
    @ApiModelProperty(value = "预估日产量")
    private Float ygrcl;

    /**
     * 预估高峰日产量
     */
    @ApiModelProperty(value = "预估高峰日产量")
    private Float yggfrcl;

    /**
     * 计划来料时间
     */
    @ApiModelProperty(value = "计划来料时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date jhllsj;

    /**
     * 计划生产线组数
     */
    @ApiModelProperty(value = "计划生产线组数")
    private Float jhscxzs;

    /**
     * 交货日期
     */
    @ApiModelProperty(value = "交货日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date jhrq;

    /**
     * 工序价合计
     */
    @ApiModelProperty(value = "工序价合计")
    private BigDecimal gxjhj;

    /**
     * 打版定价人员
     */
    @ApiModelProperty(value = "打版定价人员")
    private String dbdjry;

    /**
     * 意见说明
     */
    @ApiModelProperty(value = "意见说明")
    private String yjsm;

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
     * 申请时间
     */
    @ApiModelProperty(value = "申请时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sqsj;

    /**
     * 加工大队意见价格（元）
     */
    @ApiModelProperty(value = "加工大队意见价格（元）")
    private BigDecimal jgddyj;

    /**
     * 生活习艺科意见价格（元）
     */
    @ApiModelProperty(value = "生活习艺科意见价格（元）")
    private BigDecimal shxykyj;

    /**
     * 商务谈判价格（元）
     */
    @ApiModelProperty(value = "商务谈判价格（元）")
    private BigDecimal swtpjg;

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


}