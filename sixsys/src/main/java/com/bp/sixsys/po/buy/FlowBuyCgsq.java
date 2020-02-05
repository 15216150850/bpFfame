package com.bp.sixsys.po.buy;

import java.math.BigDecimal;
import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @Description: 采购申请实体类
 * @date 2019年11月25日
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "采购申请")
@TableName("flow_buy_cgsq")
public class FlowBuyCgsq extends BaseEntityUUID {

    /**
     * 流程标题
     */
    @ApiModelProperty(value = "流程标题")
    @Excel(name = "项目名称", width = 20)
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
     * 申请事由
     */
    @ApiModelProperty(value = "申请事由")
    private String sqsy;

    /**
     * 预计金额
     */
    @ApiModelProperty(value = "预计金额")
    private BigDecimal yjje;

    /**
     * 采购原因
     */
    @ApiModelProperty(value = "采购原因")
    private String cgyy;

    /**
     * 该项目资金来源
     */
    @ApiModelProperty(value = "该项目资金来源")
    private String gxmzjly;

    /**
     * 预算情况
     */
    @ApiModelProperty(value = "预算情况")
    private String ysqk;

    /**
     * 是否政府采购
     */
    @ApiModelProperty(value = "是否政府采购")
    private String sfzfcg;

    /**
     * 采购组织形式
     */
    @ApiModelProperty(value = "采购组织形式")
    private String cgzzxs;

    /**
     * 采购实施机构
     */
    @ApiModelProperty(value = "采购实施机构")
    private String cgssjg;

    /**
     * 采购方式
     */
    @ApiModelProperty(value = "采购方式")
    @Excel(name = "采购方式", width = 20,replace = { "公开招标_01", "竞争性谈判_02","竞争性磋商_03", "单一来源_04","网上商城_05", "协议供货_06","询价_07","其它_08"," _null" },orderNum="2")
    private String cgfs;

    /**
     * 选定分管领导
     */
    @ApiModelProperty(value = "选定分管领导")
    private String xdfgld;

    /**
     * 牵头人
     */
    @ApiModelProperty(value = "牵头人")
    private String qtr;

    /**
     * 采购领导小组会议评定结果
     */
    @ApiModelProperty(value = "采购领导小组会议评定结果")
    private String cgldxzhypdjg;

    /**
     * 省戒毒局批复意见
     */
    @ApiModelProperty(value = "省戒毒局批复意见")
    private String sjdjpfyj;

    /**
     * 是否生产设备
     */
    @ApiModelProperty(value = "是否生产设备")
    private String sfscsb;

    /**
     * 提示
     */
    @ApiModelProperty(value = "提示")
    private String tips;

    /**
     * 申请人所属部门
     */
    @ApiModelProperty(value = "申请人所属部门")
    private String sqrssbm;

    /**
     * 物资明细级预算
     */
    @ApiModelProperty(value = "物资明细级预算")
    private String wzmxjys;

    /**
     * 采购归口部门
     */
    @ApiModelProperty(value = "采购归口部门")
    private String cggkbm;

    /**
     * 采购归口部门负责人
     */
    @ApiModelProperty(value = "采购归口部门负责人")
    private String cggkbmfzr;

    /**
     * 选定分管领导
     */
    @ApiModelProperty(value = "选定分管领导")
    private String xdfg;

    /**
     * 考察小组成员
     */
    @ApiModelProperty(value = "考察小组成员")
    private String kcxzcy;

    /**
     * 采购办公室意见
     */
    @ApiModelProperty(value = "采购办公室意见")
    private String cgbgsyj;

    /**
     * 考察时间
     */
    @ApiModelProperty(value = "考察时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date kcsj;

    /**
     * 考察地点
     */
    @ApiModelProperty(value = "考察地点")
    private String kcdd;

    /**
     * 考察情况
     */
    @ApiModelProperty(value = "考察情况")
    private String kcqk;

    /**
     * 考察小组意见
     */
    @ApiModelProperty(value = "考察小组意见")
    private String kcxzyj;

    /**
     * 验收日期
     */
    @ApiModelProperty(value = "验收日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date ysrq;

    /**
     * 验收单位部门
     */
    @ApiModelProperty(value = "验收单位部门")
    private String ysdwbm;

    /**
     * 供应商名称
     */
    @ApiModelProperty(value = "供应商名称")
    private String gysmc;

    /**
     * 验收人员
     */
    @ApiModelProperty(value = "验收人员")
    private String ysry;

    /**
     * 验收物资明细及金额
     */
    @ApiModelProperty(value = "验收物资明细及金额")
    private String yswzmxjje;

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
     * 采购领导小组会议抄告单
     */
    @ApiModelProperty(value = "采购领导小组会议抄告单")
    private String cgldxzhycgd;

    /**
     * 表单初始数据
     */
    @ApiModelProperty(value = "表单初始数据")
    private String bdcssj;

    /**
     * 采购领导小组会议评定抄告单
     */
    @ApiModelProperty(value = "采购领导小组会议评定抄告单")
    private String cgldxzhypdcgd;

    /**
     * 党委会抄告单
     */
    @ApiModelProperty(value = "党委会抄告单")
    private String dwhcgd;

    /**
     * 物资名称
     */
    @ApiModelProperty(value = "物资名称")
    private String wzmc;

    /**
     * 金额
     */
    @ApiModelProperty(value = "金额")
    private BigDecimal je;

    /**
     * 会议采购单
     */
    @ApiModelProperty(value = "会议采购单")
    private String hycgd;

    /**
     * 办公室主任填写抄告单
     */
    @ApiModelProperty(value = "办公室主任填写抄告单")
    private String bgszrtxcgd;

    /**
     * 评定抄告单
     */
    @ApiModelProperty(value = "评定抄告单")
    private String pdcgd;

    /**
     * 申请人部门编码
     */
    @ApiModelProperty(value = "申请人部门编码")
    private String sqrbmbm;

    /**
     * 物资类别
     */
    @ApiModelProperty(value = "物资类别")
    private String wzlb;

    /**
     * 采购归口部门编码
     */
    @ApiModelProperty(value = "采购归口部门编码")
    private String cggkbmbm;

    /**
     * 采购归口部门负责人编码
     */
    @ApiModelProperty(value = "采购归口部门负责人编码")
    private String cggkbmfzrbm;

    /**
     * 采购时间
     */
    @ApiModelProperty(value = "采购时间")
    @TableField(exist = false)
    @Excel(name = "采购时间", width = 20,orderNum="3")
    private String cgsj;

    /**
     * 成交金额
     */
    @ApiModelProperty(value = "成交金额")
    @Excel(name = "成交金额", width = 20,orderNum="4")
    private BigDecimal cjje;

    /**
     * 是否在政府采购目录内
     */
    @ApiModelProperty(value = "是否在政府采购目录内")
    private String sfzzfcgmln;

    /**
     * 采购时间
     */
    @ApiModelProperty(value = "归口部门")
    @TableField(exist = false)
    @Excel(name = "归口部门", width = 20,orderNum="1")
    private String bmmc;


}