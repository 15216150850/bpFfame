package com.bp.sixsys.po.insp;

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
 * @Description: 谈话函询审批实体类
 * @date 2019年12月03日
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "谈话函询审批")
@TableName(value = "flow_insp_thhxsp")
public class FlowInspThhxsp extends BaseEntityUUID {

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private String bh;

    /**
     * 谈话函询对象
     */
    @ApiModelProperty(value = "谈话函询对象")
    private String thhxdx;

    /**
     * 职务
     */
    @ApiModelProperty(value = "职务")
    private String dwzw;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private String xb;

    /**
     * 年龄
     */
    @ApiModelProperty(value = "年龄")
    private String nl;

    /**
     * 政治面貌
     */
    @ApiModelProperty(value = "政治面貌")
    private String zzmm;

    /**
     * 民族
     */
    @ApiModelProperty(value = "民族")
    private String mz;

    /**
     * 谈话函询事由
     */
    @ApiModelProperty(value = "谈话函询事由")
    private String thhxsy;

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
     * 单据编号
     */
    @ApiModelProperty(value = "单据编号")
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