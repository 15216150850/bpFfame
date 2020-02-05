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
 * @Description: 经济权力-企业固定资产处置实体类
 * @date 2019年10月09日
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "经济权力-企业固定资产处置")
@TableName(value = "flow_eco_qygdzccz")
public class EcoQygdzccz extends BaseEntityUUID {

    /**
     * 事项名称
     */
    @ApiModelProperty(value = "事项名称")
    private String sxmc;

    /**
     * 资产名称
     */
    @ApiModelProperty(value = "资产名称")
    private String zcmc;

    /**
     * 类别名称
     */
    @ApiModelProperty(value = "类别名称")
    private String lbmc;

    /**
     * 资产识别码
     */
    @ApiModelProperty(value = "资产识别码")
    private String zcsbm;

    /**
     * 已使用年限
     */
    @ApiModelProperty(value = "已使用年限")
    private String ysynx;

    /**
     * 使用部门
     */
    @ApiModelProperty(value = "使用部门")
    private String sybm;

    /**
     * 使用人
     */
    @ApiModelProperty(value = "使用人")
    private String syr;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private String sl;

    /**
     * 账面原值
     */
    @ApiModelProperty(value = "账面原值")
    private String zmyz;

    /**
     * 处置方式
     */
    @ApiModelProperty(value = "处置方式")
    private String czfs;

    /**
     * 处置原因和理由
     */
    @ApiModelProperty(value = "处置原因和理由")
    private String czyyhly;

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