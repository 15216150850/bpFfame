package com.bp.common.dto;

import com.bp.common.base.BaseEntityUUID;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * @author 刘毓瑞
 * @version 1.0
 * @Description: 戒毒人员分级管理记录表实体类
 * @date 2019年08月30日
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "戒毒人员分级管理记录表")
public class BaseAddictsLevel extends BaseEntityUUID {

    /**
     * 戒毒人员编码
     */
    @ApiModelProperty(value = "戒毒人员编码")
    private String jdrybm;

    /**
     * 变更前分级管理等级
     */
    @ApiModelProperty(value = "变更前分级管理等级")
    private String beforLevel;

    /**
     * 变更后分级管理等级
     */
    @ApiModelProperty(value = "变更后分级管理等级")
    private String afterLevel;

    /**
     * 变更原因
     */
    @ApiModelProperty(value = "变更原因")
    private String content;


}