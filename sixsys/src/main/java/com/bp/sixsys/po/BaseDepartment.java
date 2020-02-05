package com.bp.sixsys.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bp.common.base.BaseEntityUUID;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * @author 郭祥旭
 * @version 1.0
 * @Description: 戒毒机构内部门表实体类
 * @date 2019年06月26日
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "戒毒机构内部门表")
@TableName(value = "base_department")
public class BaseDepartment extends BaseEntityUUID {

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    @TableField(exist = false)
    private String bh;

    /**
     * 戒毒机构编码
     */
    @ApiModelProperty(value = "戒毒机构编码")
    private String jdjgbm;

    /**
     * 部门编码
     */
    @ApiModelProperty(value = "部门编码")
    private String bmbm;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称")
    private String bmmc;

    /**
     * 建制级别
     */
    @ApiModelProperty(value = "建制级别")
    private String jzjb;

    /**
     * 部门类型
     */
    @ApiModelProperty(value = "部门类型")
    private String bmlx;

    /**
     * 上级部门编码
     */
    @ApiModelProperty(value = "上级部门编码")
    private String sjbmbm;

    /**
     * 部门简介
     */
    @ApiModelProperty(value = "部门简介")
    private String bmjj;

    /**
     * 成立日期
     */
    @ApiModelProperty(value = "成立日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date clrq;

    /**
     * 负责人
     */
    @ApiModelProperty(value = "负责人")
    private String fzr;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String lxdh;

    /**
     * 传真号码
     */
    @ApiModelProperty(value = "传真号码")
    private String czhm;
    /**
     * 是否一线部门(新增)
     */
    @ApiModelProperty(value = "是否一线部门(新增)")
    private Integer sfyxbm;



    /*页面所需字段**/


    @ApiModelProperty(value = "键制级别名称")
    @TableField(exist = false)
    private String jzjbName;

    @ApiModelProperty(value = "部门类型名称")
    @TableField(exist = false)
    private String bmlxName;

    /**
     * 用于组织树的字段
     */

    private String deptCode;


    private String sjDeptCode;


}