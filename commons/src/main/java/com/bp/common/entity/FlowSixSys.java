package com.bp.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bp.common.base.BaseEntityUUID;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * @author zhongxinkai
 * @version 1.0
 * @Description: 六大体系流程汇总表实体类
 * @date 2019年11月20日
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "六大体系流程汇总表")
@TableName(value = "flow_six_sys")
public class FlowSixSys extends BaseEntityUUID {

    /**
     * 体系种类
     */
    @ApiModelProperty(value = "体系种类")
    private String sysType;

    /**
     * 监督问责流程实例ID
     */
    @ApiModelProperty(value = "监督问责流程实例ID")
    private String supseUuid;

    /**
     * 业务流程实例ID
     */
    @ApiModelProperty(value = "业务流程实例ID")
    private String busiPid;

    @ApiModelProperty(value = "业务UUID")
    private String busiUuid;

    /**`
     * 业务链接
     */
    @ApiModelProperty(value = "业务表名")
    private String baseUrl;

    /**
     * 问责状态（1 事前 2事中 3事后）
     */
    @ApiModelProperty(value = "问责状态（1 事前 2事中 3事后）")
    private String abilityState;

    /**
     * 是否问责（1 是 0 否）
     */
    @ApiModelProperty(value = "是否问责（1 是 0 否）")
    private String isAbility;

    /**
     * 流程标题
     */
    @ApiModelProperty(value = "流程标题")
    private String actTitle;

    @ApiModelProperty(value = "业务表名")
    private String tableName;

}