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
 * @author
 * @version 1.0
 * @Description: 字典表实体类
 * @date 2017年04月11日
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "字典表实体类")
@TableName(value = "t_dictionary")
public class Dictionary extends BaseEntity {

    /**
     * 类别
     */
    @NotBlank(message = "类别不能为空")
    @ApiModelProperty(value = "类别")
    private String type;

    /**
     * 父id
     */
    @NotNull(message = "父id不能为空")
    @ApiModelProperty(value = "父id")
    private Integer pid;

    /**
     * 字典代码
     */
    @NotBlank(message = "字典代码不能为空")
    @ApiModelProperty(value = "字典代码")
    private String code;

    /**
     * 字典名称
     */
    @NotBlank(message = "字典名称不能为空")
    @ApiModelProperty(value = "字典名称")
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