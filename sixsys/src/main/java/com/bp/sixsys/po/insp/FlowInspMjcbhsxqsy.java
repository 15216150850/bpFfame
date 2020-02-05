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
 * @author pengwanli
 * @version 1.0
 * @Description: 民警操办婚丧喜庆事宜报告单实体类
 * @date 2019年12月02日
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "民警操办婚丧喜庆事宜报告单")
@TableName(value = "flow_insp_mjcbhsxqsy")
public class FlowInspMjcbhsxqsy extends BaseEntityUUID {

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
     * 时间
     */
    @ApiModelProperty(value = "时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sj;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String xm;

    /**
     * 年龄
     */
    @ApiModelProperty(value = "年龄")
    private Integer nl;

    /**
     * 工作单位及职务
     */
    @ApiModelProperty(value = "工作单位及职务")
    private String gzdwjzw;

    /**
     * 报告事由
     */
    @ApiModelProperty(value = "报告事由")
    private String bgsy;

    /**
     * 举行时间
     */
    @ApiModelProperty(value = "举行时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date jxsj;

    /**
     * 举行地点
     */
    @ApiModelProperty(value = "举行地点")
    private String jxdd;

    /**
     * 参加人员范围及数量
     */
    @ApiModelProperty(value = "参加人员范围及数量")
    private String cjryfwjsl;

    /**
     * 宴席桌数
     */
    @ApiModelProperty(value = "宴席桌数")
    private Integer yxzs;

    /**
     * 使用车辆
     */
    @ApiModelProperty(value = "使用车辆")
    private String sycl;

    /**
     * 其它需要报告的内容
     */
    @ApiModelProperty(value = "其它需要报告的内容")
    private String qtxybgdnr;

    /**
     * 事后报告
     */
    @ApiModelProperty(value = "事后报告")
    private String shbg;


}