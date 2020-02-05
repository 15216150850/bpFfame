package com.bp.sys.mapper;


import com.bp.common.base.BaseMapper;
import com.bp.common.base.BpBaseMapper;
import com.bp.sys.po.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @version 1.0
 * @Description: 角色管理映射层
 * @date 2016年7月16日
 */
@Mapper
public interface RoleMapper extends BpBaseMapper<Role> {

    /**
     * 检测角色名是否重复
     *
     * @param name 角色名
     * @param id   角色id
     * @return
     */
    Integer checkName(@Param("name") String name, @Param("id") Integer id);

    /**
     * 检测角色编码是否重复
     *
     * @param code 角色编码
     * @param id   角色id
     * @return
     */
    Integer checkCode(@Param("code") String code, @Param("id") Integer id);

    /**
     * 根据角色编码删除角色资源
     *
     * @param roleCode 角色编码
     */
    void deleteRoleRes(@Param("roleCode") String roleCode);

    /**
     * 批量保存角色资源
     *
     * @param list 角色资源list
     */
    void insertRoleRes(List list);

    /**
     * 查询角色资源
     *
     * @param code 角色编码
     * @param pid  父id
     * @return
     */
    List<Map> selectRoleUrlRes(@Param("code") String code, @Param("pid") Integer pid);

    /**
     * 批量删除角色资源
     *
     * @param codes 角色编码
     */
    void deleteRoleResByCodes(@Param("codes") String codes);

    List<Role> selectByRoleCode(@Param("code") String code);

    /**
     * 根据用户ID删除角色单独功能资源
     *
     * @param userId 角色ID
     */
    void deleteUserRes(@Param("userId") Integer userId);


    /**
     * 保存用户权限
     *
     * @param list
     */
    void insertUserRes(List list);


    /**
     * 根据用户ID查询角色
     *
     * @param userId
     */
    List<Map> selectByUserId(@Param("userId") Integer userId);

    /**
     * 批量添加用户角色
     *
     * @param list
     */
    void insertUserRoles(List list);

    /**
     * 删除用户所有角色
     *
     * @param id
     */
    void deleteUserRoles(@Param("userId") Integer id);

    /**
     * 删除角色下所有用户
     *
     * @param roleCode
     */
    void deleteUserRolesByRoleCode(@Param("roleCode") String roleCode);

    /**
     * 查询部门与用户组织树
     *
     * @return
     */
    List<Map> selectDeptAndUserTree(@Param("roleCode") String roleCode);

    /**
     * 查询部门与用户组织树，不带角色反选
     *
     * @return
     */
    List<Map> selectUserTree();

    /**
     * @param userId
     * @param pid
     * @return
     */
    List<Map> selectRoleUrlResByUserId(@Param("code") String roleCode, @Param("userId") Integer userId, @Param("pid") Integer pid);

    List<Map> findByRolesUserId(Integer userId);

    List<Map> selectPerssionByUsername(Map<String, Object> map);
}
