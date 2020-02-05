package com.bp.model.sys;


import com.baomidou.mybatisplus.annotation.TableName;
import com.bp.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author
 * @version 1.0
 * @Description: 用户实体类
 * @date 2017年03月27日
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "用户信息")
@Builder
@TableName(value = "t_user")
public class User extends BaseEntity {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 20, message = "密码长度不能小于6位,大于20位")
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    @ApiModelProperty(value = "姓名")
    private String name;

    /**
     * 账号状态 0：正常 1：禁用
     */
    @ApiModelProperty(value = "账号状态")
    private String status;

    /**
     * 角色编码
     */
    //@NotBlank(message="角色编码不能为空")
    @ApiModelProperty(value = "角色编码")
    private String roleCode;

    /**
     * 机构编码
     */
    @ApiModelProperty(value = "机构编码")
    private String organizationCode;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @ApiModelProperty(value = "用户头像")
    private String headImg;

}