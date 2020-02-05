package com.bp.act.utils;

import com.alibaba.fastjson.JSONObject;
import com.bp.act.entity.LoginUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.Map;

/**
 * 当前登录用户的实体类
 *
 * @author 钟欣凯
 */
public class UserUtils {

    /**
     * 获取当前登陆的用户
     *
     * @return //
     */
    @SuppressWarnings("rawtypes")
    public static LoginUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2Authentication) {
            OAuth2Authentication oAuth2Auth = (OAuth2Authentication) authentication;
            authentication = oAuth2Auth.getUserAuthentication();

            if (authentication instanceof UsernamePasswordAuthenticationToken) {
                UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;
                Object principal = authentication.getPrincipal();
                if (principal instanceof LoginUser) {
                    return (LoginUser) principal;
                }

                Map map = (Map) authenticationToken.getDetails();
                map = (Map) map.get("principal");

                return JSONObject.parseObject(JSONObject.toJSONString(map), LoginUser.class);
            }
        }
        return null;
    }
}
