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
 * @Description: 资源实体类
 * @date 2017年10月16日
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "资源实体类")
@TableName(value = "t_res")
public class Res extends BaseEntity {

    /**
     * 资源名称
     */
    @NotNull(message = "资源名称")
    @ApiModelProperty(value = "资源名称")
    private String name;

    /**
     * 连接
     */
    @ApiModelProperty(value = "连接")
    private String url;

    /**
     * 父id
     */
    @ApiModelProperty(value = "父id")
    private Integer pid;

    /**
     * 图表
     */
    @ApiModelProperty(value = "图表")
    private String icon;

    /**
     * 权限
     */
    @ApiModelProperty(value = "权限")
    private String permission;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空")
    @ApiModelProperty(value = "排序")
    private Integer sortNo;

    /**
     * 类别 0：菜单 1按钮
     */
    @NotNull(message = "类型不能为空")
    @ApiModelProperty(value = "类别")
    private String type;

    /**
     * 打开目标
     */
    @ApiModelProperty(value = "打开目标")
    private String target;
}