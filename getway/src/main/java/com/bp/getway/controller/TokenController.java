package com.bp.getway.controller;

import com.bp.common.bean.ReturnBean;
import com.bp.common.interface_.LogQueue;
import com.bp.common.interface_.SystemClientInfo;
import com.bp.common.po.Log;
import com.bp.common.utils.Common;

import com.bp.getway.client.Oauth2Client;
import com.bp.getway.client.UserInfoClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 登陆、刷新token、退出
 *
 * @author 钟欣凯
 */
@Slf4j
@RestController
public class TokenController {

    @Resource
    private Oauth2Client oauth2Client;

    @Autowired
    private UserInfoClient userInfoClient;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private HttpServletRequest request;


    /**
     * 系统登陆<br>
     * 根据用户名登录<br>
     * 采用oauth2密码模式获取access_token和refresh_token
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录结果
     */
    @PostMapping("/userLogin")
    public Map<String, Object> login(String username, String password) {
        /*
           首先,后台先自己判断用户名和密码,然后再交与security,因为此出是用feign远程调用,
           若是登录失败,则会抛异常,但是获取不到状态码
         */
        ReturnBean<Map> userMapBean = userInfoClient.findUserByUserName(username);
        Map userMap = userMapBean.data;
        Map<String, Object> resultMap = new HashMap<>(16);
        if (userMap == null){
            resultMap.put("error","登录失败,该用户不存在!");
            return resultMap;
        }

        String codePassWord = Common.getObjStr(userMap.get("password"));
        if (!passwordEncoder.matches(password,codePassWord)){
            resultMap.put("error","登录失败,密码错误");
            return resultMap;
        }
        if (Integer.valueOf(userMap.get("status").toString()).equals(1)){
            resultMap.put("error","登录失败,该账户已被冻结");
            return resultMap;
        }
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OAuth2Utils.GRANT_TYPE, "password");
        parameters.put(OAuth2Utils.CLIENT_ID, SystemClientInfo.CLIENT_ID);
        parameters.put("client_secret", SystemClientInfo.CLIENT_SECRET);
        parameters.put(OAuth2Utils.SCOPE, SystemClientInfo.CLIENT_SCOPE);
        parameters.put("username", username);
        parameters.put("password", password);
         Map<String,Object> map = null;
        try {
            map = oauth2Client.postAccessToken(parameters);
            map.put("name",Common.getObjStr(userMap.get("name")));

        } catch (Exception e) {
            e.printStackTrace();
            map  = new HashMap<>();
            map.put("error","登录失败,请检查你的用户名和密码,或联系管理员");
        }
        //记录登录日志
        Log log = new Log();
        Object scope =  request.getParameter("scope");
        if ("app".equals(scope)){
            log.setModule("登录app");
        }else {
            log.setModule("登录");
        }
        log.setUsername(username);
        log.setName(userMap.get("name").toString());
        log.setCreateTime(new Date());
        amqpTemplate.convertAndSend(LogQueue.LOG_QUEUE, log);
        return map;
    }




    /**
     * 系统刷新refresh_token
     *
     * @param refreshToken
     * @return
     */
    @PostMapping("/refreshToken")
    public Map<String, Object> refreshToken(String refreshToken) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OAuth2Utils.GRANT_TYPE, "refresh_token");
        parameters.put(OAuth2Utils.CLIENT_ID, SystemClientInfo.CLIENT_ID);
        parameters.put("client_secret", SystemClientInfo.CLIENT_SECRET);
        parameters.put(OAuth2Utils.SCOPE, SystemClientInfo.CLIENT_SCOPE);
        parameters.put("refresh_token", refreshToken);
        Map<String, Object> map = oauth2Client.postAccessToken(parameters);
        return oauth2Client.postAccessToken(parameters);
    }

//    /** todo 目前无效果,后面再研究
//     * 退出
//     *
//     * @param accessToken token
//     */
//    @GetMapping("/userLogout")
//    public ReturnBean logout(String accessToken) {
////        if (StringUtils.getObjStr(accessToken)) {
////            if (StringUtils.isNoneBlank(token)) {
////                accessToken = token.substring(OAuth2AccessToken.BEARER_TYPE.length() + 1);
////            }
////        }
//        oauth2Client.removeToken(accessToken);
//        return ReturnBean.ok();
//    }

//

    @GetMapping("getWayTest")
    public ReturnBean getwayTest(){

        return ReturnBean.ok();
    }
}
