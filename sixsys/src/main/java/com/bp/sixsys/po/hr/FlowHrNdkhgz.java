package com.bp.sixsys.po.hr;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bp.common.base.BaseEntityUUID;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * @author pengwanli
 * @version 1.0
 * @Description: 年度考核工作实体类
 * @date 2019年10月10日
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "年度考核工作")
@TableName(value = "flow_hr_ndkhgz")
public class FlowHrNdkhgz extends BaseEntityUUID {

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
     * 意见说明
     */
    @ApiModelProperty(value = "意见说明")
    private String yjsm;

    /**
     * 确定材料正确且完整
     */
    @ApiModelProperty(value = "确定材料正确且完整")
    private String qdclzqqwz;

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
     * 提示
     */
    @ApiModelProperty(value = "提示")
    private String tips;

    /**
     * 政治处会议时间
     */
    @ApiModelProperty(value = "政治处会议时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date zzchysj;

    /**
     * 政治处会议决定内容
     */
    @ApiModelProperty(value = "政治处会议决定内容")
    private String zzchyjdnr;

    /**
     * 领导小组会议时间
     */
    @ApiModelProperty(value = "领导小组会议时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ldxzhysj;

    /**
     * 领导小组会议决定内容
     */
    @ApiModelProperty(value = "领导小组会议决定内容")
    private String ldxzhyjdnr;

    /**
     * 抄告单内容
     */
    @ApiModelProperty(value = "抄告单内容")
    private String cgdnr;


}