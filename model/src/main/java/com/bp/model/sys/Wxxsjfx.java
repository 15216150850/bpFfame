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
 * @Description: 危险性数据分析实体类
 * @date 2019年10月25日
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "危险性数据分析")
@TableName(value = "wxxsjfx")
public class Wxxsjfx extends BaseEntityUUID {

    /**
     * 戒毒人员编码
     */
    @ApiModelProperty(value = "戒毒人员编码")
    private String jdrybm;

    /**
     * 档案得分
     */
    @ApiModelProperty(value = "档案得分")
    private Double dadf;

    /**
     * 档案-突发危疾重症得分
     */
    @ApiModelProperty(value = "档案-突发危疾重症得分")
    private Double daTfwjzzdf;

    /**
     * 档案-自杀得分
     */
    @ApiModelProperty(value = "档案-自杀得分")
    private Double daZsdf;

    /**
     * 档案-自伤得分
     */
    @ApiModelProperty(value = "档案-自伤得分")
    private Double daZsgdf;

    /**
     * 档案-自残得分
     */
    @ApiModelProperty(value = "档案-自残得分")
    private Double daZcdf;

    /**
     * 档案-脱逃得分
     */
    @ApiModelProperty(value = "档案-脱逃得分")
    private Double daTtdf;

    /**
     * 重点病号得分
     */
    @ApiModelProperty(value = "重点病号得分")
    private Double zdbhdf;

    /**
     * 重点病号-突发危机重症得分
     */
    @ApiModelProperty(value = "重点病号-突发危机重症得分")
    private Double zdbhTfwjzzdf;

    /**
     * 重点病号-自杀得分
     */
    @ApiModelProperty(value = "重点病号-自杀得分")
    private Double zdbhZsdf;

    /**
     * 重点病号-自伤得分
     */
    @ApiModelProperty(value = "重点病号-自伤得分")
    private Double zdbhZsgdf;

    /**
     * 重点病号-自残得分
     */
    @ApiModelProperty(value = "重点病号-自残得分")
    private Double zdbhZcdf;

    /**
     * 重点病号-脱逃得分
     */
    @ApiModelProperty(value = "重点病号-脱逃得分")
    private Double zdbhTtdf;

    /**
     * 心理情绪得分
     */
    @ApiModelProperty(value = "心理情绪得分")
    private Double xlqxdf;

    /**
     * 心理情绪-突发危疾重症得分
     */
    @ApiModelProperty(value = "心理情绪-突发危疾重症得分")
    private Double xlqxTfwjzzdf;

    /**
     * 心理情绪-自杀得分
     */
    @ApiModelProperty(value = "心理情绪-自杀得分")
    private Double xlqxZsdf;

    /**
     * 心理情绪-自伤得分
     */
    @ApiModelProperty(value = "心理情绪-自伤得分")
    private Double xlqxZsgdf;

    /**
     * 心理情绪-自残得分
     */
    @ApiModelProperty(value = "心理情绪-自残得分")
    private Double xlqxZcdf;

    /**
     * 心理情绪-脱逃得分
     */
    @ApiModelProperty(value = "心理情绪-脱逃得分")
    private Double xlqxTtdf;

    /**
     * 生命体征得分
     */
    @ApiModelProperty(value = "生命体征得分")
    private Double smtzdf;

    /**
     * 生命体征-突发危疾重症得分
     */
    @ApiModelProperty(value = "生命体征-突发危疾重症得分")
    private Double smtzTfwjzzdf;

    /**
     * 生命体征-自杀得分
     */
    @ApiModelProperty(value = "生命体征-自杀得分")
    private Double smtzZsdf;

    /**
     * 生命体征-自伤得分
     */
    @ApiModelProperty(value = "生命体征-自伤得分")
    private Double smtzZsgdf;

    /**
     * 生命体征-自残得分
     */
    @ApiModelProperty(value = "生命体征-自残得分")
    private Double smtzZcdf;

    /**
     * 生命体征-逃脱得分
     */
    @ApiModelProperty(value = "生命体征-逃脱得分")
    private Double smtzTtdf;

    /**
     * 康复训练得分
     */
    @ApiModelProperty(value = "康复训练得分")
    private Double kfxldf;

    /**
     * 康复训练-突发危疾重症得分
     */
    @ApiModelProperty(value = "康复训练-突发危疾重症得分")
    private Double kfxlTfwjzzdf;

    /**
     * 康复训练-自杀得分
     */
    @ApiModelProperty(value = "康复训练-自杀得分")
    private Double kfxlZsdf;

    /**
     * 康复训练-自伤得分
     */
    @ApiModelProperty(value = "康复训练-自伤得分")
    private Double kfxlZsgdf;

    /**
     * 康复训练-自残得分
     */
    @ApiModelProperty(value = "康复训练-自残得分")
    private Double kfxlZcdf;

    /**
     * 康复训练-逃脱得分
     */
    @ApiModelProperty(value = "康复训练-逃脱得分")
    private Double kfxlTtdf;

    /**
     * 行为表现得分
     */
    @ApiModelProperty(value = "行为表现得分")
    private Double xwbxdf;

    /**
     * 行为表现-突发危疾重症得分
     */
    @ApiModelProperty(value = "行为表现-突发危疾重症得分")
    private Double xwbxTfwjzzdf;

    /**
     * 行为表现-自杀得分
     */
    @ApiModelProperty(value = "行为表现-自杀得分")
    private Double xwbxZsdf;

    /**
     * 行为表现-自伤得分
     */
    @ApiModelProperty(value = "行为表现-自伤得分")
    private Double xwbxZsgdf;

    /**
     * 行为表现-自残得分
     */
    @ApiModelProperty(value = "行为表现-自残得分")
    private Double xwbxZcdf;

    /**
     * 行为表现-逃脱得分
     */
    @ApiModelProperty(value = "行为表现-逃脱得分")
    private Double xwbxTtdf;

    /**
     * 六大模块总得分
     */
    @ApiModelProperty(value = "六大模块总得分")
    private Double zf;


}