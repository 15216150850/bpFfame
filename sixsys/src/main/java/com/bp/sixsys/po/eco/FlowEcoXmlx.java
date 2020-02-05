package com.bp.sixsys.po.eco;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bp.common.base.BaseEntityUUID;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * @author guoxiangxu
 * @version 1.0
 * @Description: gov_xmlx实体类
 * @date 2019年10月10日
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "gov_xmlx")
@TableName(value = "flow_eco_xmlx")
public class FlowEcoXmlx extends BaseEntityUUID {

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    private String gsmc;

    /**
     * 公司法定代表
     */
    @ApiModelProperty(value = "公司法定代表")
    private String gsfddb;

    /**
     * 开户银行
     */
    @ApiModelProperty(value = "开户银行")
    private String khyh;

    /**
     * 帐号
     */
    @ApiModelProperty(value = "帐号")
    private String zh;

    /**
     * 实际经营者
     */
    @ApiModelProperty(value = "实际经营者")
    private String sjjyz;

    /**
     * 经营者手机号
     */
    @ApiModelProperty(value = "经营者手机号")
    private String jyzsj;

    /**
     * 经营者传真
     */
    @ApiModelProperty(value = "经营者传真")
    private String jyzcz;

    /**
     * 经营者地址
     */
    @ApiModelProperty(value = "经营者地址")
    private String jyzdz;

    /**
     * 与法人代表关系
     */
    @ApiModelProperty(value = "与法人代表关系")
    private String yfrdbgx;

    /**
     * 公司资产
     */
    @ApiModelProperty(value = "公司资产")
    private String gszc;

    /**
     * 公司经营范围
     */
    @ApiModelProperty(value = "公司经营范围")
    private String gsjyfw;

    /**
     * 意见说明
     */
    @ApiModelProperty(value = "意见说明")
    private String yjsm;

    /**
     * 相关文件
     */
    @ApiModelProperty(value = "相关文件")
    private String fj;

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
     * 领导小组意见
     */
    @ApiModelProperty(value = "调研小组成员")
    private String xzxzcy;

    /**
     * 调研小组调研报告
     */
    @ApiModelProperty(value = "调研小组调研报告")
    private String dyxzdybg;

    /**
     * 所党委意见
     */
    @ApiModelProperty(value = "所党委意见")
    private String sdwyj;


}