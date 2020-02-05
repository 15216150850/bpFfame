package com.bp.model.sys;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xcj
 * @version 1.0
 * @Description: 业务字典实体类扩展
 * @date 2018年11月06日
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "业务字典实体类扩展")
public class SerDictionaryDto extends SerDictionary {

    /**
     * 类别名称
     */
    @ApiModelProperty(value = "类别")
    private String typeName;

    private List<SerDictionary> serDictionaryList;

}