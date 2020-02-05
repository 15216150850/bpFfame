package com.bp.sys.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bp.common.base.BaseServiceImpl;
import com.bp.common.base.BpBaseMapper;
import com.bp.model.sys.Number;
import com.bp.sys.mapper.NumberMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author
 * @version 1.0
 * @Description: 流水号服务层
 * @date 2017年05月17日
 */
@Service
public class NumberService extends BaseServiceImpl<NumberMapper, Number> {

    @Resource
    private NumberMapper numberMapper;

    @Override
    public BpBaseMapper<Number> getMapper() {
        return numberMapper;
    }

    @CacheEvict(value = "numCache", allEntries = true)
    @Override
    public void delete(Long id) {
        super.delete(id);
    }

    @CacheEvict(value = "numCache", allEntries = true)
    @Override
    public void update(Number entity) {
        super.update(entity);
    }

    @Cacheable(value = "numCache")
    public Number selectByCode(String code) {
        QueryWrapper<Number> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", code);
        return getMapper().selectOne(queryWrapper);
    }

    public Integer checkNumber(Long id, String code) {
        QueryWrapper<Number> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", code);
        return getMapper().selectCount(queryWrapper);

    }

    /**
     * 获取流水号
     *
     * @param code 通过数据库行级锁获取流水号
     * @return
     */
    public String doSum(String code) {
        Number numMap = this.selectByCode(code);
        Integer id = Integer.valueOf(numMap.getId() + "");
        Map paraMap = new HashMap(1);
        paraMap.put("idd", id);
        return numberMapper.selectNum(paraMap);
    }
}
