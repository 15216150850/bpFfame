package com.bp.oauth.client;

import com.bp.common.entity.LoginUser;
import com.bp.common.entity.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * 系统服务模块接口
 * @auther: 钟欣凯
 * @date: 2019/5/6 11:16
 */
@FeignClient(name = "sys")
public interface SysClient {
    /**
     * 根据用户名查询用户的信息
     * @param username 用户名
     * @return 查询结果
     */
    @GetMapping("/ingnore/findUserInfoByUserName")
    @ResponseBody
    public SysUser findUserInfoByUserName(@RequestParam("username") String username);
}
