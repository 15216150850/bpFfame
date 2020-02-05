package com.bp.sys.service;


import com.bp.common.base.BaseServiceImpl;
import com.bp.common.base.BpBaseMapper;
import com.bp.common.bean.ReturnBean;
import com.bp.common.constants.SysConstant;
import com.bp.common.entity.SysUser;
import com.bp.common.utils.Common;
import com.bp.common.utils.UserUtils;
import com.bp.sys.mapper.ResMapper;
import com.bp.sys.mapper.RoleMapper;
import com.bp.sys.mapper.UserMapper;
import com.bp.sys.po.BasePolice;
import com.bp.sys.po.User;
import com.bp.sys.po.UserDto;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @version 1.0
 * @Description: 用户服务层
 * @date 2017年03月29日
 */
@Service
public class UserService extends BaseServiceImpl<UserMapper, User> {

    @Resource
    private UserMapper userMapper;


    @Autowired
    private RoleMapper roleMapper;

    @Resource
    private ResMapper resMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public BpBaseMapper<User> getMapper() {
        return userMapper;
    }

    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    public Map selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    /**
     * 检测用户账号是否重复
     *
     * @param username
     * @return
     */
    public Integer checkUsername(String username, Integer id) {
        return userMapper.checkUsername(username, id);
    }

    /**
     * 查询某个角色下的所有用户
     *
     * @param roleCode
     * @return
     */
    public List<Integer> selectUserByRoleCode(String roleCode) {
        return userMapper.selectUserByRoleCode(roleCode);
    }

    /**
     * 新增用户
     *
     * @param user
     */
    public void insertUser(User user, String roleCodes) {
        Integer userId = super.insert(user);
        //新增用户角色信息
        this.insertUserRoles(roleCodes, userId);
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @param roleCodes
     */
    @CacheEvict(value = "resCache", allEntries = true)
    public void updateUser(User user, String roleCodes) {
        //先删除用户所有角色信息
        roleMapper.deleteUserRoles(user.getId());
        //更新用户信息
        super.update(user);
        //新增用户角色信息
        this.insertUserRoles(roleCodes, user.getId());
    }

    /**
     * 新增用户角色，多角色
     *
     * @param roleCodes
     * @param userId
     */
    @CacheEvict(value = "resCache", allEntries = true)
    public void insertUserRoles(String roleCodes, Integer userId) {
        List list = new ArrayList();
        String[] role = roleCodes.split(",");
        //多角色循环分割
        for (String roleCode : role) {
            Map pmap = new HashMap(3);
            pmap.put("roleCode", roleCode);
            pmap.put("userId", userId);
            pmap.put("inserter", UserUtils.getCurrentUser().getId());
            list.add(pmap);
        }
        roleMapper.insertUserRoles(list);
    }

    /**
     * 删除用户
     *
     * @param userId
     */
    public void deleteUser(Integer userId) {
        //删除用户所有角色信息
        roleMapper.deleteUserRoles(userId);
        super.delete(userId);
    }

    public Map findUserByUsername(String username) {

        return userMapper.findUserByUsername(username);
    }

    public List<Map> findByRolesUserId(Integer userId) {

        return roleMapper.findByRolesUserId(userId);
    }

    public List<Map> selectPerssionByUsername(Integer userId, String permission) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("userId", userId);
        map.put("permission", permission);
        return roleMapper.selectPerssionByUsername(map);
    }


    public Map findPermissionsByUser() {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> paraMap = new HashMap<>();
        String username = UserUtils.getCurrentUser().getUsername();
        if ("admin".equals(username)) {
            paraMap.put("roleCode", "admin");
        } else {
            paraMap.put("roleCode", "user");
        }
        Map userMap = this.findUserByUsername(username);
        paraMap.put("userId", Integer.valueOf(userMap.get("id").toString()));
        List<Map> list = resMapper.selectListByUser(paraMap);
        List<String> perssions = new ArrayList<>();
        for (Map m : list) {
            perssions.add(Common.getObjStr(m.get("permission")));
        }
        map.put("permissions", perssions);
        return map;
    }

    /**
     * 根据用户名修改账号状态
     *
     * @param username
     * @param status
     */
//    @Transactional(rollbackFor = Exception.class)
//    @LcnTransaction
    //todo
    public void updateStatusByUsername(String username, String status) {
        userMapper.updateStatusByUsername(username, status);
    }


    //为接口提供数据 通过部门ids查询用户
    public List<Map> selectUserByOrgIds(String[] orgIds) {
        return userMapper.selectUserByOrgIds(orgIds);
    }

    //为接口提供数据 通过角色ids查询用户
    public List<Map> selectUserByRoleIds(String[] roleIds) {
        return userMapper.selectUserByRoleIds(roleIds);
    }

    //根据用户id查询用户dto
    public UserDto selectUesrDtoById(Integer id) {
        return userMapper.selectUesrDtoById(id);
    }

    /**
     * 分页查询用户信息
     *
     * @param para
     * @return
     */
    public ReturnBean<List<User>> selectUserDtoList(Map para) {
        //复杂查询条件封装
        para.put("conditionList", Common.dealCondition(para));
        if (para.containsKey(SysConstant.PAGE)) {
            int page = Integer.parseInt(para.get(SysConstant.PAGE).toString());
            int limit = Integer.parseInt(para.get(SysConstant.LIMIT).toString());
            PageHelper.startPage(page, limit);
        }
        List<UserDto> list = userMapper.selectUserDtoList(para);
        PageInfo<Map> pageInfo = new PageInfo(list);
        return ReturnBean.list(list, pageInfo.getTotal());


    }

    /**
     * 创建警察账户
     *
     * @param basePolice
     * @return
     */
    public ReturnBean createPoliceAccount(BasePolice basePolice) {
        User user = new User();
        String jcbm = basePolice.getJcbm();
        if (jcbm != null) {
            user.setUsername(basePolice.getJcbm()).setPassword(passwordEncoder.encode(jcbm)).setName(basePolice.getXm())
                    .setOrganizationCode(basePolice.getBmbm())
                    .setStatus("0");
            userMapper.insert(user);
        }

        return ReturnBean.ok();
    }

    public ReturnBean<List<SysUser>> findUserByBmbm(String bmbms) {
        List<SysUser> sysUsers = userMapper.findUserByBmbm(bmbms.split(SysConstant.COMMA));
        return ReturnBean.ok(sysUsers);
    }

    public void updateByUserName(User user) {
        userMapper.updateByUserName(user);
    }

    /**
     * 多个用户ID查询信息
     *
     * @param userIds
     * @return
     */
    public ReturnBean<List<Map<String, Object>>> findUserInfoByUserIds(String userIds) {
        String[] ids = userIds.split(",");
        List<Map<String, Object>> list = new ArrayList<>();

        for (String id : ids) {
            Map<String, Object> map = userMapper.selectUserInfoById(Integer.valueOf(id));
            list.add(map);
        }
        return ReturnBean.ok(list);
    }

    public List<Map<String, Object>> findUserByName(String name) {
        return userMapper.selectByName(name);
    }

    public List<Map<String, Object>> findNotPoliceAccounts() {
        return userMapper.findNotPoliceAccounts();
    }

    /**
     * 消息通知 获取系统角色下所有的用户
     * @param roleCodes 根据系统角色逗号隔开字符串
     * @return 返回系统角色下所有的用户
     */
    public ReturnBean<List<SysUser>> findUserByRoleCodes(String roleCodes) {
        List<SysUser> list = userMapper.findUserByRoleCodes(roleCodes.split(SysConstant.COMMA));
        return ReturnBean.list(list,(long)list.size());
    }

    /**
     * 消息通知 根据用户名获取用户信息
     * @param usernames 用户名逗号隔开字符串
     * @return 返回用户信息
     */
    public ReturnBean<List<SysUser>> findUserIdByUserNames(String usernames){
        List<SysUser> list = userMapper.findUserByUsernames(usernames.split(SysConstant.COMMA));
        return ReturnBean.list(list,(long)list.size());
    }

    public String findUserIdsByJcbms(String jcbms) {

        return userMapper.findUserIdsByJcbms(jcbms.split(","));
    }

    /**
     * 带部门查询所有的警察用户
     * @return
     */
    public ReturnBean<Map<String, Object>> findAllAccount() {

        List<Map<String,Object>> maps = userMapper.findAllAccount();
        return ReturnBean.ok(maps);
    }
}
