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
 * @Description: 戒治效能分析评估系统评估模型实体类
 * @date 2019年10月23日
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "戒治效能分析评估系统评估模型")
@TableName(value = "sys_efficiency_model")
public class SysEfficiencyModel extends BaseEntityUUID {

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
     * 得分
     */
    @ApiModelProperty(value = "得分")
    private String score;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;


}