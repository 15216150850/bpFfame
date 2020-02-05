package com.bp.common.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * @author 
 * @version 1.0
 * @Description: 公共抽取的映射层
 * @date 2016年7月16日
 */
public interface BaseMapperUUID<T> extends BaseMapper<T>  {

    /**
     * 分页查询
     * @param para
     * @return
     */
    List<Map<String, Object>> selectList(Map para);
}
