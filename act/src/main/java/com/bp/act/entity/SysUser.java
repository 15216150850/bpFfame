package com.bp.act.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
public class SysUser implements Serializable {

    private static final long serialVersionUID = 611197991672067628L;

    private Integer id;
    private String username;
    private String password;
    private String name;
    private String tx;
    private String phone;
    private Integer sex;
    /**
     * 状态
     */
    private Boolean enabled;
    private String type;
    private Date createTime;
    private Date updateTime;
    private Set<String> sysRoles;

    private String organizationCode;
    private Set<String> permissions;

}
