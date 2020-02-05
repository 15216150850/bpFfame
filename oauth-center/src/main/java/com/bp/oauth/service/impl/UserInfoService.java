package com.bp.oauth.service.impl;

import com.bp.common.entity.LoginUser;
import com.bp.common.utils.Common;
import com.bp.oauth.mapper.UserInfoMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @auther: 钟欣凯
 * @date: 2019/5/13 10:51
 */
@Service
public class UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;


    public LoginUser findUserInfoByUserName(String username){
        LoginUser loginUser = new LoginUser();
        Map<String,Object> userMap  = userInfoMapper.findUserByUsername(username);
        if (userMap == null){
            return null;
        }
        try {
            BeanUtils.populate(loginUser,userMap);
        } catch (Exception e) {
            throw  new RuntimeException(e);
        }
        Set<String> perSet  = new HashSet<>();
        Map param = new HashMap(2);
        param.put("userId", userMap.get("id"));
        param.put("username", userMap.get("username"));
//        //获取角色权限
        List<Map> permissionByUsername = userInfoMapper.findPermissionByUsername(param);
        for (Map map:permissionByUsername){
            String permission = Common.getObjStr(map.get("permission"));
            if (!"".equals(permission)){
                    perSet.add(permission);
            }
        }
        loginUser.setPermissions(perSet);
        String roleCode = Common.getObjStr(userMap.get("roleCode"));
        Set<String> roleSet = new HashSet<>();
        if (roleCode != null){
            String[] split = roleCode.split(",");
            roleSet.addAll(Arrays.asList(split));
        }
        loginUser.setSysRoles(roleSet);

        if ("0".equals(Common.getObjStr(userMap.get("status")))){
            loginUser.setEnabled(true);
        } else {
            loginUser.setEnabled(false);
        }
        return loginUser;
    }

}
