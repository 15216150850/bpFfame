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
 * @Description: 流水号实体类
 * @date 2017年05月17日
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "流水号实体类")
@TableName(value = "t_number")
public class Number extends BaseEntity {

    /**
     * 流水号编码
     */
    @NotBlank(message = "流水号编码不能为空")
    @ApiModelProperty(value = "流水号编码")
    private String code;

    /**
     * 流水号名称
     */
    @NotBlank(message = "流水号名称不能为空")
    @ApiModelProperty(value = "流水号名称")
    private String name;

    /**
     * 前缀
     */
    @ApiModelProperty(value = "前缀")
    private String prefix;

    /**
     * 后缀
     */
    @ApiModelProperty(value = "后缀")
    private String suffix;

    /**
     * 流水号
     */
    @NotNull(message = "流水号不能为空")
    @ApiModelProperty(value = "流水号")
    private Integer num;

    /**
     * 流水号长度
     */
    @NotNull(message = "流水号长度不能为空")
    @ApiModelProperty(value = "流水号长度")
    private Integer numLength;

    /**
     * 日期格式化
     */
    @ApiModelProperty(value = "日期格式化")
    private String dateFormat;

}