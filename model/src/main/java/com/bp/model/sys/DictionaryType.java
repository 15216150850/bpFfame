package com.bp.model.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bp.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;


/**
 * @author
 * @version 1.0
 * @Description: 字典类型表实体类
 * @date 2017年04月11日
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName(value = "t_dictionary_type")
public class DictionaryType extends BaseEntity {

    /**
     * 类型编码
     */
    @NotBlank(message = "类型编码不能为空")
    @ApiModelProperty(value = "类型编码")
    private String code;

    /**
     * 类型名称
     */
    @NotBlank(message = "类型名称不能为空")
    @ApiModelProperty(value = "类型名称")
    private String name;

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