package com.bp.act.client;

import com.bp.act.bean.ReturnBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * @author: 钟欣凯
 * @date: 2019年10月10日15:24:15
 */
@FeignClient(name = "sys")
public interface SysClient {

    /**
     * 根据用户名（警察编码）获得用户
     *
     * @param username
     * @return
     */
    @GetMapping("ingnore/findUserByUserName/{username}")
    public ReturnBean<Map> findUserByUserName(@PathVariable("username") String username);

    /**
     * 根据用户ID查询用户
     *
     * @param id 用户ID
     * @return 查询结果
     */
    @GetMapping("api/user/findUserById/{id}")
    ReturnBean<Map> findUserById(@PathVariable("id") Integer id);

    /**
     * 根据多个警察编码查询多个多个用户ID
      * @param jcbms
     * @return
     */
    @GetMapping("api/user/findUserIdsByJcbms/{jcbms}")
    String findUserIdsByJcbms(@PathVariable("jcbms") String jcbms);
}
