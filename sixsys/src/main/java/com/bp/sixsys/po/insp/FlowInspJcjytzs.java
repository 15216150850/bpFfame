package com.bp.sixsys.po.insp;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @author guoxiangxu
 * @version 1.0
 * @Description: 监查建议通知书实体类
 * @date 2019年12月02日
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "监查建议通知书")
@TableName(value = "flow_insp_jcjytzs")
public class FlowInspJcjytzs extends BaseEntityUUID {

    /**
     * 接收单位
     */
    @ApiModelProperty(value = "接收单位")
    private String jsdw;

    /**
     * 文号
     */
    @ApiModelProperty(value = "文号")
    private String wh;

    /**
     * 监察建议
     */
    @ApiModelProperty(value = "监察建议")
    private String jcjy;

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
     * 检查建议回复
     */
    @ApiModelProperty(value = "检查建议回复")
    private String jcjyhf;

    /**
     * wenhao
     */
    @ApiModelProperty(value = "wenhao")
    private String wenhao;

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
     * 组织检查部门
     */
    @TableField(exist = false)
    private String bmname;





}