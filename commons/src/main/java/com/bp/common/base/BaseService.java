package com.bp.common.base;


import com.bp.common.bean.ReturnBean;

import java.util.List;
import java.util.Map;

/**
 * @author 
 * @version 1.0
 * @Description: 抽取公共服务层接口
 * @date 2016年7月16日
 */
public interface BaseService<T> {
    /**
     * 插入
     *
     * @param entity
     * @return
     */
    Long insert(T entity);

    /**
     * 根据id删除
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 根据ids删除
     *
     * @param ids
     */
    void deleteByIds(String ids);

    /**
     * 更新
     *
     * @param entity
     * @throws Exception
     */
    void update(T entity);



    /**
     * 根据id查询实体
     *
     * @param id
     * @return
     */
    T selectEntityById(Long id);


    /**
     * 分页
     * @param para 参数
     * @return
     */
    ReturnBean<List<T>> queryList(Map para);



}
