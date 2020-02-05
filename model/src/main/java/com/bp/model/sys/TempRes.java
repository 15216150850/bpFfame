package com.bp.model.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bp.common.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * @author
 * @version 1.0
 * @Description: 临时授权资源权限表实体类
 * @date 2018年10月24日
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "临时授权扩展属性")
@TableName(value = "t_temp_res")
public class TempRes extends BaseEntity {

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String remark;

    /**
     * 用户ID集，逗号隔开
     */
    @ApiModelProperty(value = "用户ID集，逗号隔开")
    private String userId;

    /**
     * 用户姓名集，逗号隔开
     */
    @ApiModelProperty(value = "用户姓名集，逗号隔开")
    private String userName;

    /**
     * 授权开始时间
     */
    @ApiModelProperty(value = "授权开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 授权结束时间
     */
    @ApiModelProperty(value = "授权结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

}