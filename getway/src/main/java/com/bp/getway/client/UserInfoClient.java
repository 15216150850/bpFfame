package com.bp.getway.client;

import com.bp.common.bean.ReturnBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @auther: 钟欣凯
 * @date: 2019/6/18 14:48
 */
@FeignClient(name = "sys")
public interface UserInfoClient {
    @GetMapping("ingnore/findUserByUserName/{username}")
    @ResponseBody
    public ReturnBean<Map> findUserByUserName(@PathVariable("username") String username);

}
