package com.bp.model.sys;


import com.baomidou.mybatisplus.annotation.TableName;
import com.bp.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author
 * @version 1.0
 * @Description: 业务字典类型实体类
 * @date 2018年11月06日
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "业务字典类型实体类")
@TableName(value = "ser_dictionary_Type")
public class SerDictionaryType extends BaseEntity {

    /**
     * 类型编码
     */
    @ApiModelProperty(value = "类型编码")
    private String code;

    /**
     * 类型名称
     */
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
    @ApiModelProperty(value = "排序")
    private Integer sortNo;

}