package com.bp.sixsys.client;

import com.bp.common.bean.ReturnBean;
import com.bp.common.dto.BaseAddicts;
import com.bp.common.entity.SysUser;
import com.bp.sixsys.po.BaseDepartment;
import com.bp.sixsys.po.FlowRole;
import com.bp.sixsys.po.hr.BasePolice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author: 刘毓瑞
 * @date: 2019/7/8 16:34
 */
@FeignClient(name = "sys")
public interface SysClient {
    /**
     * 查询指定角色下的所有用userId
     *
     * @param roleCode
     * @return
     */
    @GetMapping("api/selectUserIdByRole/{roleCode}")
    public ReturnBean<List<Integer>> selectUserIdByRole(@PathVariable("roleCode") String roleCode);

    /**
     * 获取流水号
     *
     * @param flowCode
     * @return
     */
    @GetMapping("api/getNumberFlow/{flowCode}")
    public ReturnBean<String> getNumberFlow(@PathVariable("flowCode") String flowCode);

    /**
     * 通过编码查询流程角色
     *
     * @param code
     * @return
     */
    @GetMapping("api/flowRole/findByCode/{code}")
    public ReturnBean<FlowRole> findFlowRoleByCode(@PathVariable("code") String code);



    @GetMapping("api/flowRole/findFlowRoleByUserIdsAndBmbm/{userIds}/{bmbm}")
    List<SysUser> findFlowRoleByUserIdsAndBmbm(@PathVariable("userIds") String userIds, @PathVariable("bmbm") String bmbm);


    /**
     * 根据用户名（警察编码）获得用户
     *
     * @param username
     * @return
     */
    @GetMapping("ingnore/findUserByUserName/{username}")
    public ReturnBean<Map> findUserByUserName(@PathVariable("username") String username);

    /**
     * 根据警察姓名和部门编码查询警察编码
     *
     * @param jcxm
     * @param bmbm
     * @return
     */
    @GetMapping("api/findPolicebyJcxm/{jcxm}/{bmbm}")
    public ReturnBean findPolicebyJcxm(@PathVariable("jcxm") String jcxm, @PathVariable("bmbm") String bmbm);

    /**
     * 根据姓名查询用户信息
     *
     * @param name
     * @return
     */
    @GetMapping("api/user/findUserByName/{name}")
    public ReturnBean findUserByName(@PathVariable("name") String name);

    /**
     * 查询在所状态戒毒人员实体根据戒毒人员编码
     *
     * @param jdrybm
     * @return
     */
    @GetMapping("api/baseAddicts/findByJdrybm/{jdrybm}")
    @ResponseBody
    ReturnBean<BaseAddicts> findByJdrybm(@PathVariable("jdrybm") String jdrybm);

    /**
     * 根据部门编码查询
     *
     * @param bmbm
     * @return
     */
    @GetMapping("api/baseDepatment/findByBmbm/{bmbm}")
    public ReturnBean<BaseDepartment> findByBmbm(@PathVariable("bmbm") String bmbm);

    @GetMapping("api/user/findUserById/{id}")
    ReturnBean<Map> findUserById(@PathVariable("id") int id);

    @PutMapping("api/basePolice/updatePolice")
    public ReturnBean updatePolice(@RequestBody BasePolice basePolice);

    @GetMapping("api/findPolicebyJcby/{jcbm}")
    public ReturnBean<BasePolice> findPolicebyJcby(@PathVariable("jcbm") String jcbm);

    /**
     * 根据code和type查询name
     *
     * @param code
     * @param type
     * @return
     */
    @GetMapping("api/serDictionary/getName/{code}/{type}")
    public ReturnBean getNameByCodeAndType(@PathVariable("code") String code, @PathVariable("type") String type);
}
