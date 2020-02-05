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
 * @author guoxiangxu
 * @version 1.0
 * @Description: 资源租赁审批实体类
 * @date 2019年10月12日
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "资源租赁审批")
@TableName(value = "flow_eco_zyzl")
public class FlowEcoZyzl extends BaseEntityUUID {

    /**
     * 资源名称
     */
    @ApiModelProperty(value = "资源名称")
    private String zymc;

    /**
     * 位置结构
     */
    @ApiModelProperty(value = "位置结构")
    private String wzjg;

    /**
     * 招租时间
     */
    @ApiModelProperty(value = "招租时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date zzsj;

    /**
     * 招租形式
     */
    @ApiModelProperty(value = "招租形式")
    private String zzxs;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String bz;

    /**
     * 附件
     */
    @ApiModelProperty(value = "附件")
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
     * 考察小组成员
     */
    @ApiModelProperty(value = "考察小组成员")
    private String xzxzcy;

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


}