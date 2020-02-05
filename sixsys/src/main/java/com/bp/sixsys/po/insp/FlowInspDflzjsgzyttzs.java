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
 * @Description: 党风廉政建设工作约谈通知书实体类
 * @date 2019年12月03日
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "党风廉政建设工作约谈通知书")
@TableName(value = "flow_insp_dflzjsgzyttzs")
public class FlowInspDflzjsgzyttzs extends BaseEntityUUID {

    /**
     * 被约谈人姓名
     */
    @ApiModelProperty(value = "被约谈人姓名")
    private String bytrxm;

    /**
     * 约谈时间
     */
    @ApiModelProperty(value = "时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date ytsj;

    /**
     * 约谈联系人
     */
    @ApiModelProperty(value = "约谈联系人")
    private String ytlxr;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String lxdh;

    /**
     * 约谈地点
     */
    @ApiModelProperty(value = "约谈地点")
    private String ytdd;

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