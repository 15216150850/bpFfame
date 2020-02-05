package com.bp.common.dto;

import com.bp.common.base.BaseEntityUUID;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * @author 刘毓瑞
 * @version 1.0
 * @Description: 戒毒人员家庭社会关系表实体类
 * @date 2019年07月02日
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "戒毒人员家庭社会关系表")
public class BaseAddictsRelation extends BaseEntityUUID {


    /**
     * 戒毒人员表UUID
     */
    @ApiModelProperty(value = "戒毒人员表UUID")
    private String baseAddictsUuid;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name;

    /**
     * 关系
     */
    @ApiModelProperty(value = "关系")
    private String gx;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String lxdh;

    /**
     * 现在住址
     */
    @ApiModelProperty(value = "现在住址")
    private String xzzz;


}