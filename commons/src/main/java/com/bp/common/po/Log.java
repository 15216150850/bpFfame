package com.bp.common.po;



import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bp.common.base.BaseEntity;
import lombok.Data;


import java.util.Date;

/**
 * @author: 钟欣凯
 * @date: 2018/12/28 10:32
 */

@Data
@TableName(value = "t_log")
public class Log extends BaseEntity {

    private static final long serialVersionUID = -5398795297842978376L;


    /** 用户名 */
    private String username;
    /** 模块 */
    private String module;
    /** 参数值 */
    private String params;
    private String remark;
    /** 是否执行成功 */
    private Boolean flag;
    @TableField(value = "createTime")
    private Date createTime;

    /**
     * 操作用户姓名
     */
    private String name;

}
