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
 * @Description: 组织机构实体类
 * @date 2017年10月16日
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "组织机构实体类")
@TableName(value = "t_organization")
public class Organization extends BaseEntity {

    /**
     * 父级id
     */
    @NotNull(message = "父级id不能为空")
    @ApiModelProperty(value = "父级id")
    private Integer pid;

    /**
     * 机构名称
     */
    @NotBlank(message = "机构名称不能为空")
    @ApiModelProperty(value = "机构名称")
    private String name;

    /**
     * 机构代码
     */
    @NotBlank(message = "机构代码不能为空")
    @ApiModelProperty(value = "机构代码")
    private String code;

    /**
     * 机构类别
     */
    @ApiModelProperty(value = "机构类别")
    private String type;

    /**
     * 结构类型
     */
    @ApiModelProperty(value = "结构类型")
    private String grade;

    /**
     * 主要负责人
     */
    @ApiModelProperty(value = "主要负责人")
    private String primaryMan;

    /**
     * 副负责人
     */
    @ApiModelProperty(value = "副负责人")
    private String viceMan;

    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人")
    private String linkMan;

    /**
     * 联系地址
     */
    @ApiModelProperty(value = "联系地址")
    private String address;

    /**
     * 邮编
     */
    @ApiModelProperty(value = "邮编")
    private String zipcode;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    private String phone;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 区域编码
     */
    @ApiModelProperty(value = "区域编码")
    private String regionCode;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
}