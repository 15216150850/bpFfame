package com.bp.common.base;


import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bp.common.bean.ReturnBean;
import com.bp.common.constants.SysConstant;
import com.bp.common.utils.IdUtils;
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
 * @author 钟欣凯
 * @version 1.0
 * @Description: 公共抽取服务层的实现
 * @date 2016年7月16日
 */
public abstract class BaseServiceImplUUID<K extends BaseMapperUUID<T>, T extends BaseEntityUUID> extends ServiceImpl<K, T> implements IService<T> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取mapper
     *
     * @return
     */
    public abstract BaseMapperUUID<T> getMapper();

    /**
     * 插入
     *
     * @param entity
     * @return
     */

    public String insert(T entity) {
        entity.setUuid(IdUtils.getUuid());
        entity.setInserter(null == entity.getInserter() ? UserUtils.getCurrentUser() == null ? 0 : (UserUtils.getCurrentUser().getId()) : entity.getInserter());
        entity.setInsertTime(new Date());
        getMapper().insert(entity);
        return entity.getUuid();
    }

    /**
     * 根据id删除
     *
     * @param uuid
     */

    public void delete(String uuid) {
        getMapper().deleteById(uuid);
    }

    /**
     * 根据ids批量删除
     *
     * @param idsStr
     */

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

    public void update(T entity) {
        entity.setUpdater(UserUtils.getCurrentUser() == null ? 0 : (UserUtils.getCurrentUser().getId()));
        entity.setUpdateTime(new Date());
        getMapper().updateById(entity);
    }


    /**
     * 根据id查询实体
     *
     * @param uuid
     * @return
     */

    public T selectEntityById(String uuid) {
        return getMapper().selectById(uuid);
    }


    /**
     * 分页查询
     *
     * @return
     */

    public ReturnBean<List<T>> selectList(Map para) {
        if (para != null && para.get(SysConstant.PAGE) != null) {
            int page = Integer.parseInt(para.get(SysConstant.PAGE).toString());
            int limit = Integer.parseInt(para.get(SysConstant.LIMIT).toString());
            PageHelper.startPage(page, limit);
            List<Map<String, Object>> list = getMapper().selectList(para);
            PageInfo<Map> pageInfo = new PageInfo(list);
            return ReturnBean.list(list, pageInfo.getTotal());
        } else {
            List<Map<String, Object>> list = getMapper().selectList(para);
            return ReturnBean.list(list, (long) list.size());
        }
    }

}



