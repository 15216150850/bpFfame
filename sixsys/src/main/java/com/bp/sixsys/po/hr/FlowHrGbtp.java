package com.bp.sixsys.po.hr;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @author guoxiangxu
 * @version 1.0
 * @Description: 干部调配实体类
 * @date 2019年10月17日
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "干部调配")
@TableName(value = "flow_hr_gbtp")
public class FlowHrGbtp extends BaseEntityUUID {

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String xm;

    /**
     * 调入单位及职务
     */
    @ApiModelProperty(value = "调入单位及职务")
    private String drdwjzw;

    /**
     * 是否为中队长以上职务
     */
    @ApiModelProperty(value = "是否为中队长以上职务")
    private String sfzdzys;

    /**
     * 申请事由
     */
    @ApiModelProperty(value = "申请事由")
    private String sqsy;

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
     * 原部门 /所在部门
     */
    @ApiModelProperty(value = "原部门")
    private String ybm;

    /**
     * 调入后职务
     */
    @ApiModelProperty(value = "调入后职务")
    private String drhzw;


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
     * 是否全否（0、1）
     */
    @ApiModelProperty(value = "是否全否（0、1）")
    private String isAllno;

    /**
     * 确定材料正确且完整
     */
    @ApiModelProperty(value = "确定材料正确且完整")
    private String hccl;

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
    private String zzchynr;

    /**
     * 行政职务
     */
    @ApiModelProperty(value = "行政职务")
    private String xzzw;

    /**
     * 抄告单
     */
    @ApiModelProperty(value = "抄告单")
    private String cgd;

    /**
     * 调入后部门名称
     */
    @TableField(exist = false)
    private String drhbmmc;


}