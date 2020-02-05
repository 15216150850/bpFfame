package com.bp.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bp.common.base.BaseServiceImpl;
import com.bp.common.base.BpBaseMapper;
import com.bp.sys.mapper.ResMapper;
import com.bp.sys.po.Res;
import com.bp.sys.po.dto.ResDto;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @version 1.0
 * @Description: 资源管理服务层
 * @date 2017年03月29日
 */
@Service
public class ResService extends BaseServiceImpl<ResMapper, Res> {

    @Resource
    private ResMapper resMapper;

    @Override
    public BpBaseMapper<Res> getMapper() {
        return resMapper;
    }

    @CacheEvict(value = "resCache", allEntries = true)
    @Override
    public Integer insert(Res entity) {
        return super.insert(entity);
    }

    @CacheEvict(value = "resCache", allEntries = true)
    @Override
    public void update(Res entity) {
        super.update(entity);
    }

    /**
     * 查询全部资源
     *
     * @return
     */
    @Cacheable(value = "resCache", unless = "")
    public List selectAllTree() {
        return resMapper.selectAllTree();
    }

    /**
     * 根据pid查询资源
     *
     * @param pid
     * @return
     */
    @Cacheable(value = "resCache")
    public List selectListByPid(Integer pid) {
        return resMapper.selectListByPid(pid);
    }

    /**
     * 递归删除模块
     *
     * @param id
     * @throws Exception
     */
    @CacheEvict(value = "resCache", allEntries = true)
    @Override
    public void delete(Integer id) {
        super.delete(id);
        List<Map> resList = resMapper.selectListByPid(id);
        for (Map map : resList) {
            String idd = map.get("id").toString();
            delete(Integer.valueOf(idd));
        }
    }

    /**
     * 查询所有资源记录
     *
     * @param pid 父节点id
     * @return 返回查询到的资源
     */
    public List<Res> selectAllTableTree(Integer pid) {
        return resMapper.selectAll();
    }


    @Cacheable(value = "resCache", key = "#userId")
    public List selectRoleUrlResByUserId(String roleCode, Integer userId, int pid) {
        List<Map> list = resMapper.selectRoleUrlResByUserId(roleCode, userId, pid);
        if (list != null && list.size() > 0) {
            for (Map m : list) {
                List chlid = selectRoleUrlResByUserId(roleCode, userId, Integer.valueOf(m.get("id").toString()));
                m.put("child", chlid);
            }
        }
        return list;
    }

    //    @Cacheable(value = "resTreeCache",key = "#userId")
    public List selectRoleResByUser(Integer userId) {
        List<Map> list = resMapper.selectRoleResByUser(userId);
        return list;
    }

    //    @Cacheable(value = "resCache",key = "#userId")
    public List selectRoleUrlResByUserIdSjpt(String roleCode, Integer userId, int pid) {
        List<Map> list = resMapper.selectRoleUrlResByUserIdSjpt(roleCode, userId, pid);
        if (list != null && list.size() > 0) {
            for (Map m : list) {
                List chlid = selectRoleUrlResByUserIdSjpt(roleCode, userId, Integer.valueOf(m.get("id").toString()));
                m.put("child", chlid);
            }
        }
        return list;
    }

    /**
     * 资源树获取列表 根据pid查询子级资源树列表
     *
     * @param pid 父节点pid
     * @return 返回同级别资源树
     */
    public List<ResDto> selectTableList(String pid) {
        return resMapper.selectTableList(pid);
    }
}
