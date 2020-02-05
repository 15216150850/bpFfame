package com.bp.sys.mapper;

import com.bp.common.base.BaseMapper;
import com.bp.common.base.BpBaseMapper;
import com.bp.sys.po.Organization;
import com.bp.sys.po.OrganizationDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * @author
 * @version 1.0
 * @Description: 组织机构映射层
 * @date 2017年05月17日
 */
@Mapper
public interface OrganizationMapper extends BpBaseMapper<Organization> {

    /**
     * 检测组织编码
     *
     * @param id   组织id
     * @param code 组织编码
     * @return
     */
    Integer checkCode(@Param("id") Integer id, @Param("code") String code);

    /**
     * 获取组织树结构
     *
     * @return
     */
    List selectAllTree();

    /**
     * 查询子集组织
     *
     * @param id 组织id
     * @return
     */
    List<Map> selectChildList(@Param("id") Integer id);

    Map selectMapById(Integer id);
}
