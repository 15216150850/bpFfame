package com.bp.sixsys.po.proj;

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
 * @Description: 工程建设申请实体类
 * @date 2019年11月25日
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "工程建设申请")
@TableName(value = "flow_proj_gcjssq")
public class FlowProjGcjssq extends BaseEntityUUID {

    /**
     * 事项内容要点
     */
    @ApiModelProperty(value = "事项内容要点")
    private String sxnryd;

    /**
     * 预计金额（元）
     */
    @ApiModelProperty(value = "预计金额（元）")
    private BigDecimal yjje;

    /**
     * 工程建设原因（取自字典）
     */
    @ApiModelProperty(value = "工程建设原因（取自字典）")
    private String gcjsyy;

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
     * 基建办公室负责人意见
     */
    @ApiModelProperty(value = "基建办公室负责人意见")
    private String jjbgsyj;

    /**
     * 该项目资金来源
     */
    @ApiModelProperty(value = "该项目资金来源")
    private String xmzjly;

    /**
     * 预算情况
     */
    @ApiModelProperty(value = "预算情况")
    private String ysqk;

    /**
     * 采购实施机构（默认：委托代理机构采购）
     */
    @ApiModelProperty(value = "采购实施机构")
    private String cgssjg;

    /**
     * 采购方式（默认：公开招标）
     */
    @ApiModelProperty(value = "采购方式")
    private String cgfs;

    /**
     * 办公室主任抄告单
     */
    @ApiModelProperty(value = "办公室主任抄告单")
    private String bgszrcgd;

    /**
     * 党委会抄告单
     */
    @ApiModelProperty(value = "党委会抄告单")
    private String dwhcgd;

    /**
     * 省戒毒局批复意见
     */
    @ApiModelProperty(value = "省戒毒局批复意见")
    private String sjdjpfyj;

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