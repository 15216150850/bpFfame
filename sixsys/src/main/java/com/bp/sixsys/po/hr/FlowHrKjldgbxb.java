package com.bp.sixsys.po.hr;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bp.common.base.BaseEntityUUID;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * @author zhangyu
 * @version 1.0
 * @Description: 科级领导干部选拔任用工作实体类
 * @date 2019年10月10日
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "科级领导干部选拔任用工作")
@TableName(value = "flow_hr_kjldgbxb")
public class FlowHrKjldgbxb extends BaseEntityUUID {

    /**
     * 意见说明
     */
    @ApiModelProperty(value = "意见说明")
    private String yjsm;

    /**
     * 相关文件(file_store的uuid)
     */
    @ApiModelProperty(value = "相关文件(file_store的uuid)")
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
     * 申请时间
     */
    @ApiModelProperty(value = "申请时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sqsj;

    /**
     * 政治处会议时间
     */
    @ApiModelProperty(value = "政治处会议时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date zzchysj;

    /**
     * 政治处会议内容
     */
    @ApiModelProperty(value = "政治处会议内容")
    private String zzchynr;

    /**
     * 领导小组会议时间
     */
    @ApiModelProperty(value = "领导小组会议时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ldxzhysj;

    /**
     * 领导小组会议内容
     */
    @ApiModelProperty(value = "领导小组会议内容")
    private String ldxzhynr;

    /**
     * 党委成员应到人数
     */
    @ApiModelProperty(value = "党委成员应到人数")
    private Integer ydrs;

    /**
     * 党委成员实到人数
     */
    @ApiModelProperty(value = "党委成员实到人数")
    private Integer sdrs;

    /**
     * 党委成员参会人员名单
     */
    @ApiModelProperty(value = "党委成员参会人员名单")
    private String rymd;

    /**
     * 抄告单
     */
    @ApiModelProperty(value = "抄告单")
    private String cgd;

    /**
     * 确定已公示5个工作日(0:否,1:是)
     */
    @ApiModelProperty(value = "确定已公示5个工作日(0:否,1:是)")
    private Integer gs;

    /**
     * 确定材料正确且完整(0:否,1:是)
     */
    @ApiModelProperty(value = "确定材料正确且完整(0:否,1:是)")
    private Integer hccl;

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