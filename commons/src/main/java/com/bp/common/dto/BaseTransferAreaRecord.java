package com.bp.common.dto;

import com.bp.common.base.BaseEntityUUID;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * @author xcj
 * @version 1.0
 * @Description: 转区记录实体类
 * @date 2019年08月16日
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "转区记录")
public class BaseTransferAreaRecord extends BaseEntityUUID {

    /**
     * 戒毒人员编码
     */
    @ApiModelProperty(value = "戒毒人员编码")
    private String jdrybm;

    /**
     * 当前区域编码
     */
    @ApiModelProperty(value = "当前区域编码")
    private String nowArea;

    /**
     * 当前区域名称
     */
    @ApiModelProperty(value = "当前区域名称")
    private String nowAreaName;

    /**
     * 下一区域编码
     */
    @ApiModelProperty(value = "下一区域编码")
    private String nextArea;

    /**
     * 下一区域名称
     */
    @ApiModelProperty(value = "下一区域名称")
    private String nextAreaName;

    /**
     * 转区日期
     */
    @ApiModelProperty(value = "转区日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date transferDate;

    /**
     * 下一个区域呆的时间
     */
    @ApiModelProperty(value = "下一个区域呆的时间")
    private Integer nextAreaDays;

    /**
     * 类型(0:待转;1:已转)
     */
    @ApiModelProperty(value = "类型(0:待转;1:已转)")
    private String type;


}