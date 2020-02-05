package com.bp.oauth.service.impl;

import com.bp.common.entity.LoginUser;
import com.bp.common.entity.SysUser;
import com.bp.oauth.client.SysClient;
import com.bp.oauth.mapper.UserInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * 根据用户名获取用户<br>
 * <p>
 * 密码校验请看下面两个类
 * @author 钟欣凯
 * @see org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
 * @see org.springframework.security.authentication.dao.DaoAuthenticationProvider
 */
@Slf4j
@Service

public class UserDetailServiceImpl implements UserDetailsService {


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Resource
    private UserInfoService userInfoService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //从系统服务获取用户相关的信息
     LoginUser loginUser = userInfoService.findUserInfoByUserName(username);
        if (loginUser == null){
            throw new AuthenticationCredentialsNotFoundException("用户不存在");
        }
        if (!loginUser.isEnabled()) {
            throw new DisabledException("用户已作废");
        }
        return loginUser;
    }


}
