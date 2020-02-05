package com.bp.model.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author
 * @version 1.0
 * @Description: 临时授权扩展属性
 * @date 2018年10月24日
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "临时授权扩展属性")
public class TempResDto extends TempRes {

    @ApiModelProperty(value = "资源IDS")
    private String resId;

    @ApiModelProperty(value = "资源名集合")
    private String resNames;

}