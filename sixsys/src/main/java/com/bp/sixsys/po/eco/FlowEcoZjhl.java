package com.bp.sixsys.po.eco;

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
 * @Description: 资金回笼实体类
 * @date 2019年10月15日
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "资金回笼")
@TableName(value = "flow_eco_zjhl")
public class FlowEcoZjhl extends BaseEntityUUID {

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    private String gsmc;

    /**
     * 公司法人代表
     */
    @ApiModelProperty(value = "公司法人代表")
    private String gsfrdb;

    /**
     * 公司结账人
     */
    @ApiModelProperty(value = "公司结账人")
    private String gsjzr;

    /**
     * 联系方式
     */
    @ApiModelProperty(value = "联系方式")
    private String lxfs;

    /**
     * 公司地址
     */
    @ApiModelProperty(value = "公司地址")
    private String gsdz;

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
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String bz;

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
     * 生产大队确认（指定人员）
     */
    @ApiModelProperty(value = "生产大队确认（指定人员）")
    private String scddqr;

    /**
     * 发票开出时间
     */
    @ApiModelProperty(value = "发票开出时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date fpkcsj;

    /**
     * 货款是否已回笼
     */
    @ApiModelProperty(value = "货款是否已回笼")
    private String hksfyhl;

    /**
     * 货款回笼时间
     */
    @ApiModelProperty(value = "货款回笼时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date hkhlsj;

    /**
     * 流程标题
     */
    @ApiModelProperty(value = "流程标题")
    private String actTitle;

    /**
     * 流程实例pid
     */
    @ApiModelProperty(value = "流程实例pid")
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
     * 流程状态
     */
    @ApiModelProperty(value = "是否提交党委会")
    private String sftjdwh;

    /**
     * 流程状态
     */
    @ApiModelProperty(value = "发票开出说明")
    private String fpsm;



}