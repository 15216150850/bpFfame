package com.bp.model.sys;

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
 * @Description: sys_safety_prediction实体类
 * @date 2019年10月25日
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "sys_safety_prediction")
public class SysSafetyPrediction extends BaseEntityUUID {

    /**
     * 序号
     */
    @ApiModelProperty(value = "序号")
    private Integer sortNo;

    /**
     * 分类
     */
    @ApiModelProperty(value = "分类")
    private String type;

    /**
     * 评估指标
     */
    @ApiModelProperty(value = "评估指标")
    private String target;

    /**
     * 考量标准
     */
    @ApiModelProperty(value = "考量标准")
    private String standard;

    /**
     * 扣分
     */
    @ApiModelProperty(value = "扣分")
    private String score;


}