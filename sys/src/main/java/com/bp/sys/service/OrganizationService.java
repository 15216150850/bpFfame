package com.bp.sys.service;

import com.bp.common.base.BaseMapper;
import com.bp.common.base.BaseServiceImpl;
import com.bp.common.base.BpBaseMapper;
import com.bp.sys.mapper.OrganizationMapper;
import com.bp.sys.po.Organization;
import com.bp.sys.po.OrganizationDto;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @version 1.0
 * @Description: 组织机构服务层
 * @date 2017年05月17日
 */
@Service
public class OrganizationService extends BaseServiceImpl<OrganizationMapper, Organization> {

    @Resource
    private OrganizationMapper organizationMapper;

    @Override
    public BpBaseMapper<Organization> getMapper() {
        return organizationMapper;
    }

    @CacheEvict(value = "organizationCache", allEntries = true)
    @Override
    public Integer insert(Organization entity) {
        return super.insert(entity);
    }

    @CacheEvict(value = "organizationCache", allEntries = true)
    @Override
    public void update(Organization entity) {
        super.update(entity);
    }

    /**
     * 检查机构代码是否重复
     *
     * @param id
     * @param code
     * @return
     */
    public Integer checkCode(Integer id, String code) {
        return organizationMapper.checkCode(id, code);
    }

    /**
     * 获取组织树
     *
     * @return
     */
    @Cacheable(value = "organizationCache", unless = "#result == null")
    public List selectAllTree() {
        return organizationMapper.selectAllTree();
    }

    /**
     * 根据参数查询
     *
     * @param para
     * @return
     */
    @Cacheable(value = "organizationCache", unless = "#result == null")
    public List selectListNoPage(Map para) {
        return organizationMapper.selectList(para);
    }

    /**
     * 删除
     *
     * @param id
     */
    @CacheEvict(value = "organizationCache", allEntries = true)
    @Override
    public void delete(Integer id) {
        super.delete(id);
        List<Map> resList = organizationMapper.selectChildList(id);
        for (Map map : resList) {
            String idd = map.get("id").toString();
            delete(Integer.valueOf(idd));
        }
    }

    public Map selectMapById(Integer id) {
        return organizationMapper.selectMapById(id);
    }
}
