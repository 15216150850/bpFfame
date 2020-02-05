package com.bp.model.sys;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author
 * @version 1.0
 * @Description: 用户实体类
 * @date 2017年03月27日
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "用户信息扩展属性")

public class UserDto extends User {

    /**
     * 用户名
     */
    @ApiModelProperty(value = "组织机构ID")
    private String organizationId;


    @ApiModelProperty(value = "组织机构名称")
    private String organizationName;

    @ApiModelProperty(value = "角色编码集合")
    private String roleCodes;

    @ApiModelProperty(value = "角色名称集合")
    private String roleNames;


}