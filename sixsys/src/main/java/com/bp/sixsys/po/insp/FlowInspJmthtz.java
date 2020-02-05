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
 * @author pengwanli
 * @version 1.0
 * @Description: 诫勉谈话通知实体类
 * @date 2019年12月02日
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "诫勉谈话通知")
@TableName(value = "flow_insp_jmthtz")
public class FlowInspJmthtz extends BaseEntityUUID {

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
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private String bh;

    /**
     * 党支部
     */
    @ApiModelProperty(value = "党支部")
    private String dzb;

    /**
     * 谈话对象姓名
     */
    @ApiModelProperty(value = "谈话对象姓名")
    private String thdxxm;

    /**
     * 谈话对象编码
     */
    @ApiModelProperty(value = "谈话对象编码")
    private String thdxbm;

    /**
     * 谈话人姓名
     */
    @ApiModelProperty(value = "谈话人姓名")
    private String thrxm;

    /**
     * 谈话人编码
     */
    @ApiModelProperty(value = "谈话人编码")
    private String thrbm;

    /**
     * 审签领导姓名
     */
    @ApiModelProperty(value = "审签领导姓名")
    private String sqldxm;

    /**
     * 审签领导编码
     */
    @ApiModelProperty(value = "审签领导编码")
    private String sqldbm;


}