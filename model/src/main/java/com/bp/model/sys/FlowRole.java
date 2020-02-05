package com.bp.model.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bp.common.base.BaseEntityUUID;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * @author yanjisheng
 * @version 1.0
 * @Description: 流程角色实体类
 * @date 2019年09月02日
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "流程角色")
@TableName(value = "flow_role")
public class FlowRole extends BaseEntityUUID {


    /**
     * 流程角色名称
     */
    @ApiModelProperty(value = "流程角色名称")
    private String name;

    /**
     * 流程角色编码
     */
    @ApiModelProperty(value = "流程角色编码")
    private String code;

    /**
     * 关联的用户
     */
    @ApiModelProperty(value = "关联的用户")
    private String userIds;

    /**
     * 关联的用户名
     */
    @ApiModelProperty(value = "关联的用户名")
    private String names;


}