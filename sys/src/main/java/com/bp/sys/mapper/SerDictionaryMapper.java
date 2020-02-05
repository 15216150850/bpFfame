package com.bp.sys.mapper;

import com.bp.common.base.BpBaseMapper;
import com.bp.sys.po.SerDictionary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * @author
 * @version 1.0
 * @Description: service_dictionary映射层
 * @date 2018年11月06日
 */
@Mapper
public interface SerDictionaryMapper extends BpBaseMapper<SerDictionary> {
    /**
     * 检测字典编码
     *
     * @param type 字典类型
     * @param code 字典编码
     * @param id   字典id
     * @return
     */
    Integer checkCode(@Param("type") String type, @Param("code") String code, @Param("id") Long id);

    /**
     * 获取子集字典
     *
     * @param id 字典id
     * @return
     */
    List<Map> selectChildList(@Param("id") Integer id);

    /**
     * 根据类型删除字典
     *
     * @param type
     */
    void deleteByType(@Param("type") String type);

    /**
     * 根据父id和类型查询字典
     *
     * @param pid  父id
     * @param type 类型
     * @return
     */
    List<Map> selectDicByPidAndType(@Param("pid") Integer pid, @Param("type") String type);

    /**
     * 根据类型批量删除字典
     *
     * @param types
     */
    void deleteByTypes(@Param("types") String types);

    /**
     * 根据类型字符串集合获取字典2.0
     *
     * @param type
     * @return
     */
    List<SerDictionary> selectDicByTypes(String[] type);

    /**
     * 根据name 和 type 查询code
     *
     * @param param
     * @return
     */
    String selectCodeByNameAndType(Map param);

    /**
     * 新闻查询树状栏目用根据type
     *
     * @param type 类型
     * @return
     */
    List<Map> selectDicByTypeTree(@Param("type") String type);
}
