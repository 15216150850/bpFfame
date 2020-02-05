package com.bp.sys.mapper;

import com.bp.common.base.BaseMapper;
import com.bp.common.base.BpBaseMapper;
import com.bp.sys.po.SerDictionaryType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * @author
 * @version 1.0
 * @Description: 业务字典类型映射层
 * @date 2018年11月06日
 */
@Mapper
public interface SerDictionaryTypeMapper extends BpBaseMapper<SerDictionaryType> {
    /**
     * 检测字典类型编码是否重复
     *
     * @param code 字典类型编码
     * @param id   字典类型id
     * @return
     */
    Integer checkCode(@Param("code") String code, @Param("id") Integer id);

}
