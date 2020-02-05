package com.bp.model.sys;


import com.baomidou.mybatisplus.annotation.TableName;
import com.bp.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author
 * @version 1.0
 * @Description: 角色实体类
 * @date 2016年5月20日
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "角色实体类")
@TableName(value = "t_role")
public class Role extends BaseEntity {

    /**
     * 角色名
     */
    @NotNull(message = "角色名不能为空")
    @ApiModelProperty(value = "角色名")
    private String name;

    /**
     * 角色编码
     */
    @NotNull(message = "角色编码不能为空")
    @ApiModelProperty(value = "角色编码")
    private String code;

    /**
     * 状态 0：正常  1：禁用
     */
    @ApiModelProperty(value = "状态")
    private String status;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空")
    @ApiModelProperty(value = "排序")
    private Integer sortNo;

}