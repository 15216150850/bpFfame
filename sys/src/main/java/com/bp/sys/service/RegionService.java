package com.bp.sys.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bp.common.base.BpBaseMapper;
import com.bp.sys.po.Region;
import com.bp.sys.mapper.RegionMapper;
import com.bp.common.base.BaseMapper;
import com.bp.common.base.BaseServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Description：区域服务层
 * @author：
 * @date：2016-07-26
 */
@Service
public class RegionService extends BaseServiceImpl<RegionMapper, Region> {

    @Resource
    private RegionMapper regionMapper;

    @Override
    public BpBaseMapper<Region> getMapper() {
        return regionMapper;
    }

    @CacheEvict(value = "regionCache", allEntries = true)
    @Override
    public Integer insert(Region entity) {
        return super.insert(entity);
    }

    @CacheEvict(value = "regionCache", allEntries = true)
    @Override
    public void update(Region entity) {
        super.update(entity);
    }

    @Cacheable(value = "regionCache", unless = "#result == null")
    public List selectListByPid(Integer pid) {
        return regionMapper.selectListByPid(pid);
    }

    /**
     * 区域区域树
     *
     * @return
     */
    @Cacheable(value = "regionCache", unless = "#result == null")
    public JSONArray selectRegionTree() {
        List list = regionMapper.selectByMap(new HashMap<>());
        JSONArray array = new JSONArray();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                JSONObject jsonObject = new JSONObject();
                Map m1 = (Map) list.get(i);
                int resId1 = Integer.valueOf(m1.get("id") + "");
                jsonObject.put("id", resId1);
                jsonObject.put("name", m1.get("name"));
                jsonObject.put("code", m1.get("code"));
                jsonObject.put("pId", m1.get("pid"));
                array.add(jsonObject);
            }
        }
        return array;
    }

    /**
     * 检测区域编码
     *
     * @param id
     * @param code
     * @return
     */
    public Integer checkCode(Integer id, String code) {
        return regionMapper.checkCode(id, code);
    }

    /**
     * 递归删除
     */
    @CacheEvict(value = "regionCache", allEntries = true)
    @Override
    public void delete(Integer id) {
        super.delete(id);
        // 查询子集
        List<Map> regList = regionMapper.selectChildList(id);
        for (Map dic : regList) {
            Integer regId = Integer.valueOf(dic.get("id") + "");
            delete(regId);
        }
    }

    /**
     * 根据code查询实体
     * @param code
     * @return
     */
    public Region selectEntityByCode(String code){
        QueryWrapper<Region> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("code",code);
        return regionMapper.selectOne(queryWrapper);
    }
}
