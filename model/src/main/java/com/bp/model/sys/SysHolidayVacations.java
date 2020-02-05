package com.bp.model.sys;

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
 * @Description: 节假日设置实体类
 * @date 2020年01月17日
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "节假日设置")
public class SysHolidayVacations extends BaseEntityUUID {

    /**
     * 节假日日期
     */
    @ApiModelProperty(value = "节假日日期")
    private String holidayDate;

    /**
     * 状态（0：非节假日，1：是节假日）
     */
    @ApiModelProperty(value = "状态（0：非节假日，1：是节假日）")
    private String status;


}