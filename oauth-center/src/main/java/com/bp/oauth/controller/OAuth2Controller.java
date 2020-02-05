package com.bp.oauth.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * 获取当前的用户登录的信息
 * @author 钟欣凯
 *
 */
@Slf4j
@RestController
@RequestMapping
public class OAuth2Controller {

    @Autowired
    private ConsumerTokenServices tokenServices;
    /**
     *  获取当前用户信息接口
     * @return 用户信息
     */
    @GetMapping("/user-me")
    public Authentication principal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.debug("user-me:{}", authentication.getName());
        return authentication;
    }

    @GetMapping("/feignTest")
    public String feignTest(){

        return "测试成功";
    }


    @DeleteMapping("remove_token")
    public void removeToken(String accessToken){
       tokenServices.revokeToken(accessToken);
       //todo 保存退出登录的日志

    }

}
