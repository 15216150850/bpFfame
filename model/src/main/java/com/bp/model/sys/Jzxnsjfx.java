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
 * @author zhangyu
 * @version 1.0
 * @Description: 戒治效能数据分析实体类
 * @date 2019年10月24日
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "戒治效能数据分析")
@TableName(value = "jzxnsjfx")
public class Jzxnsjfx extends BaseEntityUUID {

    /**
     * 戒毒人员编码
     */
    @ApiModelProperty(value = "戒毒人员编码")
    private String jdrybm;

    /**
     * 生理脱毒效能总得分
     */
    @ApiModelProperty(value = "生理脱毒效能总得分")
    private Double sltdxn;

    /**
     * 心理康复效能评估总得分
     */
    @ApiModelProperty(value = "心理康复效能评估总得分")
    private Double xlkf;

    /**
     * 认知能力效能评估总得分
     */
    @ApiModelProperty(value = "认知能力效能评估总得分")
    private Double rznl;

    /**
     * 社会适应效能评估总得分
     */
    @ApiModelProperty(value = "社会适应效能评估总得分")
    private Double shsy;

    /**
     * 行为方式效能评估总得分
     */
    @ApiModelProperty(value = "行为方式效能评估总得分")
    private Double xwfs;

    /**
     * 总得分
     */
    @ApiModelProperty(value = "总得分")
    private Double zf;

}