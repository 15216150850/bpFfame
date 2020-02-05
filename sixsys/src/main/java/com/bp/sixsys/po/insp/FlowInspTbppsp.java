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
 * @Description: 通报批评审批实体类
 * @date 2019年12月03日
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "通报批评审批")
@TableName(value = "flow_insp_tbppsp")
public class FlowInspTbppsp extends BaseEntityUUID {

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private String bh;

    /**
     * 被通报批评人姓名
     */
    @ApiModelProperty(value = "被通报批评人姓名")
    private String btbpprxm;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private String xb;

    /**
     * 出生年月
     */
    @ApiModelProperty(value = "出生年月")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date csny;

    /**
     * 政治面貌
     */
    @ApiModelProperty(value = "政治面貌")
    private String zzmm;

    /**
     * 工作单位及职位
     */
    @ApiModelProperty(value = "工作单位及职位")
    private String gzdwjzw;

    /**
     * 时间
     */
    @ApiModelProperty(value = "时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date sj;

    /**
     * 被通报批评事由
     */
    @ApiModelProperty(value = "被通报批评事由")
    private String btbppsy;

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
     * 流程编号
     */
    @ApiModelProperty(value = "职位")
    private String zw;


}