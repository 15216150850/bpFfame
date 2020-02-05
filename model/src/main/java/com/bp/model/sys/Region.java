package com.bp.model.sys;


import com.baomidou.mybatisplus.annotation.TableName;
import com.bp.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @version 1.0
 * @Description：Region实体类
 * @author：
 * @date：2016-07-26
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "区域实体类")
@TableName(value = "t_region")
public class Region extends BaseEntity {

    /**
     * 地区编码
     */
    @NotBlank(message = "地区编码不能为空")
    @ApiModelProperty(value = "地区编码")
    private String code;

    /**
     * 地区名称
     */
    @NotBlank(message = "地区名称不能为空")
    @ApiModelProperty(value = "地区名称")
    private String name;

    /**
     * 区域类型
     */
    @NotBlank(message = "区域类型不能为空")
    @ApiModelProperty(value = "区域类型")
    private String type;

    /**
     * 父id
     */
    @NotNull(message = "父id不能为空")
    @ApiModelProperty(value = "父id")
    private Integer pid;
}
