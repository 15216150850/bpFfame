package com.bp.model.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author
 * @version 1.0
 * @Description: 组织机构扩展实体类
 * @date 2017年10月16日
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "组织机构扩展实体类")
public class OrganizationDto extends Organization {

    /**
     * 父级名称
     */
    @ApiModelProperty(value = "父级名称")
    private String pname;

    /**
     * 区域id
     */
    @ApiModelProperty(value = "区域id")
    private Integer regionId;

    /**
     * 区域名称
     */
    @ApiModelProperty(value = "区域名称")
    private String regionName;

}