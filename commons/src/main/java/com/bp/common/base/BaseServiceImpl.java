package com.bp.common.base;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bp.common.bean.ReturnBean;
import com.bp.common.constants.SysConstant;
import com.bp.common.utils.UserUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @version 1.0
 * @Description: 公共抽取服务层的实现
 * @date 2016年7月16日
 */
public abstract class BaseServiceImpl<K extends BpBaseMapper<T>, T extends BaseEntity> extends ServiceImpl<K,T> implements BaseService<T> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取mapper
     *
     * @return
     */
    public abstract BpBaseMapper<T> getMapper();

    /**
     * 插入
     *
     * @param entity

.     */
    @Override
    public Long insert(T entity) {
        entity.setInserter(UserUtils.getCurrentUser() == null ? 0 : (UserUtils.getCurrentUser().getId()));
        entity.setInsertTime(new Date());
        getMapper().insert(entity);
        return entity.getId();
    }

    /**
     * 根据id删除
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        getMapper().deleteById(id);
    }

    /**
     * 根据ids批量删除
     *
     * @param idsStr
     */
    @Override
    public void deleteByIds(String idsStr) {
        String[] ids = idsStr.split(",");
        getMapper().deleteBatchIds(Arrays.asList(ids));

    }

    /**
     * 更新
     *
     * @param entity
     * @throws Exception
     */
    @Override
    public void update(T entity) {
        entity.setUpdater(UserUtils.getCurrentUser() == null ? 0 : (UserUtils.getCurrentUser().getId()));
        entity.setUpdateTime(new Date());
        getMapper().updateById(entity);
    }


    /**
     * 根据id查询实体
     *
     * @param id
     * @return
     */
    @Override
    public T selectEntityById(Long id) {
        return getMapper().selectById(id);
    }


    /**
     * 分页
     *
     * @return
     */
    @Override
    public ReturnBean<List<T>> queryList(Map para) {
        if (para!=null&&para.get("page")!=null){
            int page = Integer.parseInt(para.get(SysConstant.PAGE).toString());
            int limit = Integer.parseInt(para.get(SysConstant.LIMIT).toString());
            PageHelper.startPage(page, limit);
            List<T> list = getMapper().queryList(para);
            PageInfo<Map> pageInfo = new PageInfo(list);
            return ReturnBean.list(list,pageInfo.getTotal());
        } else {

            List<T> list = getMapper().queryList(para);
            return ReturnBean.list(list, (long) list.size());
        }
    }
}



