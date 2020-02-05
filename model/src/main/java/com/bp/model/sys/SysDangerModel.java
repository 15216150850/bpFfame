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
 * @Description: 戒毒人员危险性分析评估模型实体类
 * @date 2019年10月23日
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "戒毒人员危险性分析评估模型")
@TableName(value = "sys_danger_model")
public class SysDangerModel extends BaseEntityUUID {

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
     * 突发危急重症
     */
    @ApiModelProperty(value = "突发危急重症")
    private String sickness;

    /**
     * 自杀
     */
    @ApiModelProperty(value = "自杀")
    private String suicide;

    /**
     * 自伤
     */
    @ApiModelProperty(value = "自伤")
    private String hurt;

    /**
     * 自残
     */
    @ApiModelProperty(value = "自残")
    private String disabled;

    /**
     * 脱逃
     */
    @ApiModelProperty(value = "脱逃")
    private String escape;


}