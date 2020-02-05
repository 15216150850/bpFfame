package com.bp.sys.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bp.common.base.BaseMapper;
import com.bp.common.base.BaseServiceImpl;
import com.bp.common.base.BpBaseMapper;
import com.bp.common.utils.Common;
import com.bp.common.utils.UserUtils;
import com.bp.sys.mapper.ResMapper;
import com.bp.sys.mapper.RoleMapper;
import com.bp.sys.mapper.UserMapper;
import com.bp.sys.po.Role;
import com.bp.sys.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author
 * @version 1.0
 * @Description: 角色管理服务层
 * @date 2016年7月16日
 */
@Service
public class RoleService extends BaseServiceImpl<RoleMapper, Role> {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private ResMapper resMapper;
    @Autowired
    private UserService userService;

    @Override
    public BpBaseMapper<Role> getMapper() {
        return roleMapper;
    }

    private final Integer ADMIN_USERID = 1;

    /**
     * 检测角色名
     *
     * @param name 角色名
     * @param id   角色id
     * @return
     */
    public Integer checkName(String name, Integer id) {
        return roleMapper.checkName(name, id);
    }

    /**
     * 检测角色编码
     *
     * @param code 角色编码
     * @param id   角色id
     * @return
     */
    public Integer checkCode(String code, Integer id) {
        return roleMapper.checkCode(code, id);
    }

    /**
     * 获取角色资源树
     *
     * @param roleCode
     * @return
     */
    public JSONArray selectRoleResTree(String roleCode, Integer userId) {
        // 查询全部资源
        List allList = resMapper.selectRoleResByUserId(userId);
        if (ADMIN_USERID.equals(userId)) {
            allList = resMapper.selectAllTree();
        }
        // 查询角色资源
        List roleResList = resMapper.selectListByRoleCode(roleCode);

        Map<String, String> paraMap = new HashMap<>();
        paraMap.put("open", "true");
        paraMap.put("checked", "true");

        return JSONArray.parseArray(JSON.toJSONString(this.zTreeResUtil(allList, roleResList, paraMap)));
    }

    /**
     * 获取用户资源树
     *
     * @param pid
     * @return
     */
    public JSONArray selectResTreeByTempResId(Integer pid, Integer userId) {
        // 查询用户拥有的全部资源
        List allList = resMapper.selectRoleResByUserId(userId);
        if (ADMIN_USERID.equals(userId)) {
            allList = resMapper.selectAllTree();
        }
        // 查询用户资源
        List userResList = resMapper.selectListByTempResId(pid);


        Map<String, String> paraMap = new HashMap<>();
        paraMap.put("open", "true");
        paraMap.put("checked", "true");

        //单独的功能授权勾选中
        List resList = this.zTreeResUtil(allList, userResList, paraMap);

        return JSONArray.parseArray(JSON.toJSONString(resList));
    }

    /**
     * 获取用户资源树
     *
     * @return
     */
    public Object selectUserResTree(Integer myUserId, Integer userId) {
        // 查询全部资源
        List allList = resMapper.selectRoleResByUserId(myUserId);
        if (ADMIN_USERID.equals(myUserId)) {
            allList = resMapper.selectAllTree();
        }
        // 查询角色资源
        List roleResList = resMapper.selectRoleResByUserId(userId);
        // 查询用户资源
        List userResList = resMapper.selectListByUserId(userId);


        Map<String, String> paraMap = new HashMap<>();
        paraMap.put("open", "true");
        paraMap.put("checked", "true");

        //单独的功能授权勾选中
        List resList = this.zTreeResUtil(allList, userResList, paraMap);

        //把用户角色已经拥有的权限勾选中并禁止操作
        paraMap.put("chkDisabled", "true");
        List resultResList = this.zTreeResUtil(resList, roleResList, paraMap);

        return JSONArray.parseArray(JSON.toJSONString(resultResList));
    }

    /**
     * 通用节点组合方法封装
     *
     * @param allResList 所有资源节点集合
     * @param resList    授权的资源节点集合
     * @param paraMap    节点个性化参数
     * @return
     */
    public List zTreeResUtil(List allResList, List resList, Map<String, String> paraMap) {
        List array = new ArrayList();
        if (allResList != null && allResList.size() > 0) {
            for (int i = 0; i < allResList.size(); i++) {
                Map m1 = (Map) allResList.get(i);
                int resId1 = Integer.valueOf(m1.get("id") + "");
                /*m1.put("id", resId1);
                m1.put("name", m1.get("name"));*/
                //m1.put("pId", m1.get("pid"));
                if (resList != null && resList.size() > 0) {
                    for (int j = 0; j < resList.size(); j++) {
                        Map m2 = (Map) resList.get(j);
                        int resId2 = Integer.valueOf(m2.get("id") + "");
                        if (resId1 == resId2) {
                            //给用户拥有的节点设置个性化参数例如 选中、展开等等。。
                            for (Map.Entry<String, String> entry : paraMap.entrySet()) {
                                m1.put(entry.getKey(), entry.getValue());
                            }
                        }
                    }
                }
                array.add(m1);
            }
        }
        return array;
    }

    /**
     * 修改角色权限
     *
     * @param roleCode
     * @param resId
     * @throws Exception
     */

    public void updateRoleRes(String roleCode, String resId) {
        // 先删除角色权限
        roleMapper.deleteRoleRes(roleCode);
        if (resId != null && !"".equals(resId)) {
            List list = new ArrayList();
            String[] ids = resId.split(",");
            for (String id : ids) {
                Map pmap = new HashMap(2);
                pmap.put("roleCode", roleCode);
                pmap.put("resId", Integer.valueOf(id));
                list.add(pmap);
            }
            roleMapper.insertRoleRes(list);
        }
    }

    /**
     * 修改角色单独功能权限
     *
     * @param userId
     * @param resId
     * @throws Exception
     */
    public void updateUserRes(Integer userId, String resId) {
        // 先删除用户单独授权权限
        roleMapper.deleteUserRes(userId);
        if (resId != null && !"".equals(resId)) {
            List list = new ArrayList();
            String[] ids = resId.split(",");
            for (String id : ids) {
                Map pmap = new HashMap(2);
                pmap.put("userId", userId);
                pmap.put("resId", Integer.valueOf(id));
                pmap.put("inserter", UserUtils.getCurrentUser().getId());
                list.add(pmap);
            }
            roleMapper.insertUserRes(list);
        }
    }


    /**
     * 删除
     *
     * @param id
     * @param code
     */
    public void delete(Integer id, String code) {
        super.delete(id);
        roleMapper.deleteRoleRes(code);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @param codes
     */
    public void deleteByIdsAndCodes(String ids, String codes) {
        super.deleteByIds(ids);
        roleMapper.deleteRoleResByCodes(codes);
    }

    /**
     * 获取角色url资源
     *
     * @param code
     * @return
     */
    @Cacheable(value = "resCache")
    public List selectRoleUrlRes(String code, Integer pid) {
        List<Map> list = roleMapper.selectRoleUrlRes(code, pid);
        if (list != null && list.size() > 0) {
            for (Map m : list) {
                List chlid = selectRoleUrlRes(code, Integer.valueOf(m.get("id").toString()));
                m.put("child", chlid);
            }
        }
        return list;
    }

    /**
     * 角色树集合
     *
     * @return
     */
    public List selectUserRolesTree(Integer userId) {
        Map para = new HashMap(2);
        para.put("userId", userId);
        List roles = roleMapper.selectList(para);
        List userRoleList = roleMapper.selectByUserId(userId);
        Map paraMap = new HashMap(2);
        paraMap.put("checked", "true");
        if (null != userId) {
            return this.zTreeResUtil(roles, userRoleList, paraMap);
        } else {
            return roles;
        }

    }

    /**
     * 查询URL菜单
     *
     * @param userId
     * @param pid
     * @return
     */
    public List selectRoleUrlResByUserId(String roleCode, Integer userId, Integer pid) {
        List<Map> list = roleMapper.selectRoleUrlResByUserId(roleCode, userId, pid);
        if (list != null && list.size() > 0) {
            for (Map m : list) {
                List chlid = selectRoleUrlResByUserId(roleCode, userId, Integer.valueOf(m.get("id").toString()));
                m.put("child", chlid);
            }
        }
        return list;
    }

    /**
     * @param roleCode 查询部门与用户组织树
     * @return
     */
    public JSONArray selectDeptAndUserTree(String roleCode) {
        return JSONArray.parseArray(JSON.toJSONString(roleMapper.selectDeptAndUserTree(roleCode)));
    }

    /**
     * 查询部门与用户组织树
     *
     * @return
     */
    public JSONArray selectUserTree() {
        return JSONArray.parseArray(JSON.toJSONString(roleMapper.selectUserTree()));
    }

    /**
     * 给角色批量设置用户
     *
     * @param userIds
     * @param roleCode
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertUsersRole(String userIds, String roleCode) {
        String[] userId = userIds.split(",");
        List list = new ArrayList();
        User user=new User();
        for (String id : userId) {
            if (!"".equals(id)) {
                Map pmap = new HashMap(3);
                pmap.put("roleCode", roleCode);
                pmap.put("userId", id);
                pmap.put("inserter", UserUtils.getCurrentUser().getId());
                list.add(pmap);
                user.setId(Integer.valueOf(id));
                user.setRoleCode(roleCode);
                userService.update(user);
            }

        }
        //先删除
        this.deleteUserRolesByRoleCode(roleCode);
        roleMapper.insertUserRoles(list);
    }

    /**
     * 删除角色下所有用户
     *
     * @param roleCode
     */
    public void deleteUserRolesByRoleCode(String roleCode) {
        roleMapper.deleteUserRolesByRoleCode(roleCode);
    }

    /**
     * 查询用户所有角色封装成Set集合返回
     * @param userId
     * @return
     */
    public Set<String> selectByUserIdToSet(Integer userId){
        Set<String> sysRoles=new HashSet<>();
        List<Map> list = roleMapper.selectByUserId(userId);
        for (Map map:list) {
            String roleCode=Common.getObjStr(map.get("roleCode"));
            if(!"".equals(roleCode)){
                sysRoles.add(roleCode);
            }
        }
        return sysRoles;
    }

}
