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
 * @author yanjisheng
 * @version 1.0
 * @Description: 经济权力-企业固定资产所外调拨实体类
 * @date 2019年10月08日
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "经济权力-企业固定资产所外调拨")
@TableName(value = "flow_eco_qygdzcswdb")
public class EcoQygdzcswdb extends BaseEntityUUID {

    /**
     * 事项名称
     */
    @ApiModelProperty(value = "事项名称")
    private String sxmc;

    /**
     * 使用单位
     */
    @ApiModelProperty(value = "使用单位")
    private String sydw;

    /**
     * 资产名称
     */
    @ApiModelProperty(value = "资产名称")
    private String zcmc;

    /**
     * 资产数量
     */
    @ApiModelProperty(value = "资产数量")
    private String zcsl;

    /**
     * 资产编号
     */
    @ApiModelProperty(value = "资产编号")
    private String zcbh;

    /**
     * 资产状况
     */
    @ApiModelProperty(value = "资产状况")
    private String zczk;

    /**
     * 意见说明
     */
    @ApiModelProperty(value = "意见说明")
    private String yjsm;

    /**
     * 附件
     */
    @ApiModelProperty(value = "附件")
    private String file;
    
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
     * 流程id
     */
    @ApiModelProperty(value = "流程id")
    private String pid;

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
     * 文档状态
     */
    @ApiModelProperty(value = "文档状态")
    private String docState;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;


}