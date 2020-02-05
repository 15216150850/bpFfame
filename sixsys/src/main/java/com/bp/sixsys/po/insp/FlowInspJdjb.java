package com.bp.sixsys.po.insp;

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
 * @Description: 监督举报实体类
 * @date 2019年10月12日
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "监督举报")
@TableName(value = "flow_insp_jdjb")
public class FlowInspJdjb extends BaseEntityUUID {

    /**
     * 举报内容
     */
    @ApiModelProperty(value = "举报内容")
    private String jbnr;

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
     * 处理领导
     */
    @ApiModelProperty(value = "处理领导")
    private String clld;

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