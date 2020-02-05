package com.bp.sixsys.po.insp;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
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
 * @Description: 诫勉谈话审批表实体类
 * @date 2019年12月03日
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "诫勉谈话审批表")
@TableName(value = "flow_insp_jmthsp")
public class FlowInspJmthsp extends BaseEntityUUID {

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private String bh;

    /**
     * 谈话对象姓名
     */
    @ApiModelProperty(value = "谈话对象姓名")
    private String thdxxm;

    /**
     * 出生年月
     */
    @ApiModelProperty(value = "出生年月")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date csny;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private String xb;

    /**
     * 政治面貌
     */
    @ApiModelProperty(value = "政治面貌")
    private String zzmm;

    /**
     * 工作单位及职务
     */
    @ApiModelProperty(value = "工作单位及职务")
    private String gzdwjzw;

    /**
     * 谈话人姓名（谈）
     */
    @ApiModelProperty(value = "谈话人姓名（谈）")
    private String thrxmtan;

    /**
     * 工作单位及职务（谈）
     */
    @ApiModelProperty(value = "工作单位及职务（谈）")
    private String gzdwjzwtan;

    /**
     * 谈话事由
     */
    @ApiModelProperty(value = "谈话事由")
    private String thsy;

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


    /**
     * 相关文件
     */
    @ApiModelProperty(value = "相关文件")
    private String xgwj;


}