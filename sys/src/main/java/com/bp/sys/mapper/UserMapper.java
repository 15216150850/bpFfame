package com.bp.sys.mapper;


import com.bp.common.base.BpBaseMapper;
import com.bp.common.entity.SysUser;
import com.bp.sys.po.User;
import com.bp.sys.po.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @version 1.0
 * @Description: 用户映射层
 * @date 2017年03月9:44日
 */
@Mapper
public interface UserMapper extends BpBaseMapper<User> {

    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    Map selectByUsername(@Param("username") String username);

    /**
     * 检测用户名称
     *
     * @param username
     * @return
     */
    Integer checkUsername(@Param("username") String username, @Param("id") Integer id);

    /**
     * 查询某个角色下的所有用户
     *
     * @return
     */
    List<Integer> selectUserByRoleCode(@Param("roleCode") String roleCode);

    Map findUserByUsername(String username);

    List<Map> findPermissionByUsername(String username);

    /**
     * 根据部门集合查询该部门集合下所有用户信息
     *
     * @param ids
     * @return
     */
    List<Map> selectUserByOrgIds(String[] ids);

    /**
     * 根据角色集合查询该角色集合下所有用户信息
     *
     * @param ids
     * @return
     */
    List<Map> selectUserByRoleIds(String[] ids);

    /**
     * 根据用户名修改账号状态
     *
     * @param username
     * @param status
     */
    void updateStatusByUsername(@Param("username") String username, @Param("status") String status);

    /**
     * 根据id查询用户Dto
     *
     * @param id
     * @return
     */
    UserDto selectUesrDtoById(Integer id);


    /**
     * 用户列表分页查询
     *
     * @param para
     * @return
     */
    List<UserDto> selectUserDtoList(Map para);

    Long selectUserDtoListCount(Map para);

    List<SysUser> findUserByBmbm(@Param("bmbms") String[] bmbms);

    void updateByUserName(User user);

    Map<String, Object> selectUserInfoById(Integer id);

    public List<Map<String, Object>> selectByName(String name);

    public List<Map<String, Object>> findNotPoliceAccounts();

    List<SysUser> findUserByRoleCodes(@Param("roleCodeArr") String[] roleCodeArr);

    /**
     * 消息通知-根据用户名查询用户
     * @param usernameArr 用户名集合
     * @return
     */
    List<SysUser> findUserByUsernames(@Param("usernameArr") String[] usernameArr);

    String findUserIdsByJcbms(@Param("array") String[] array);

    List<Map<String, Object>> findAllAccount();


}
