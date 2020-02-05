package com.bp.sys.controller;

import com.alibaba.fastjson.JSON;
import com.bp.common.anno.LogAnnotation;
import com.bp.common.base.BaseController;
import com.bp.common.bean.ReturnBean;
import com.bp.common.entity.LoginUser;
import com.bp.common.entity.SysUser;
import com.bp.common.utils.Common;
import com.bp.common.utils.UserUtils;
import com.bp.sys.po.User;
import com.bp.sys.po.UserDto;
import com.bp.sys.service.RoleService;
import com.bp.sys.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;


/**
 * @author  4444
 * @version 1.0
 * @Description: 用户管理控制层
 * @date 2016年7月16日
 */
@RestController
@RequestMapping(value = "system/user")
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 用户管理
     *
     * @param para
     * @return
     */
    @PreAuthorize("hasAuthority('system:user:list')")
    @GetMapping("/listData")
    public ReturnBean<List<User>> list(@RequestParam Map para) {
        return userService.selectUserDtoList(para);
    }

    /**
     * 用户管理
     *忽略权限
     * @param para
     * @return
     */
    @GetMapping("/ingnore/listData")
    public ReturnBean<List<User>> ignoreList(@RequestParam Map para) {
        return userService.selectUserDtoList(para);
    }

    /**
     * 添加用户
     *
     * @param user 用户
     * @return
     */
    @LogAnnotation(module = "添加用户")
    @PreAuthorize("hasAuthority('system:user:insert')")
    @PostMapping
    public ReturnBean insert(@RequestBody UserDto user) throws UnsupportedEncodingException {
        user.setStatus("0");
        validateEntity(user);
        String password = user.getPassword();
        password = passwordEncoder.encode(password);
        user.setPassword(password);
        userService.insertUser(user, user.getRoleCodes());
        return ReturnBean.ok();
    }


    /**
     * 修改用户
     *
     * @param user 用户
     * @return
     */
    @LogAnnotation(module = "修改用户")
    @PreAuthorize("hasAuthority('system:user:update')")
    @PutMapping
    public ReturnBean update(@RequestBody UserDto user) throws Exception {
        String password = user.getPassword();
        //保持原密码不变
        if (password != null && !"".equals(password)) {
            password = passwordEncoder.encode(password);
            user.setPassword(password);
        } else {
            user.setPassword(null);
        }

        userService.updateUser(user, user.getRoleCodes());
        return ReturnBean.ok();
    }


    /**
     * 查看用户信息
     *
     * @param id 用户id
     * @return
     */
    @PreAuthorize("hasAnyAuthority('system:user:see','system:user:update')")
    @GetMapping("/{id}")
    public ReturnBean<UserDto> see(@PathVariable("id") Integer id) throws InvocationTargetException, IllegalAccessException {
        UserDto userDto = userService.selectUesrDtoById(id);
        return ReturnBean.ok(userDto);
    }


    /**
     * 删除用户
     *
     * @param id 用户id
     * @return
     */
    @LogAnnotation(module = "删除用户")
    @PreAuthorize("hasAuthority('system:user:delete')")
    @DeleteMapping("/{id}")
    public ReturnBean delete(@PathVariable("id") Integer id, HttpSession httpSession) {
        userService.deleteUser(id);
        return ReturnBean.ok();
    }


    /**
     * 删除用户
     *
     * @param ids 用户ids
     * @return
     */
    @LogAnnotation(module = "批量删除用户")
    @PreAuthorize("hasAuthority('system:user:delete')")
    @DeleteMapping("/deleteByIds/{ids}")
    public ReturnBean deleteByIds(@PathVariable("ids") String ids, HttpSession httpSession) {
        userService.deleteByIds(ids);
        return ReturnBean.ok();
    }


    /**
     * 禁用||启用用户
     *
     * @param id
     * @param status
     * @return
     */
    @LogAnnotation(module = "禁用||启用用户")
    @PreAuthorize("hasAuthority('system:user:status')")
    @GetMapping("/status/{id}/{status}")
    public ReturnBean status(@PathVariable("id") Integer id, @PathVariable("status") String status) {
        User user = new User();
        user.setId(id);
        user.setStatus(status);
        userService.update(user);
        return ReturnBean.ok();
    }


    /**
     * 检测用户名
     *
     * @param username 用户名
     * @return
     */
    @GetMapping(value = "/checkUsername/{username}/{id}")
    public ReturnBean<Boolean> checkUsername(@PathVariable("username") String username, @PathVariable("id") String id) {
        Integer count = userService.checkUsername(username, "null".equals(id) ? null : Integer.valueOf(id));
        if (count > 0) {
            return ReturnBean.ok(false);
        } else {
            return ReturnBean.ok(true);
        }
    }

    /**
     * 修改密码页
     *
     * @return
     */
    @GetMapping("/getLoginUser")
    public ReturnBean<LoginUser> updatePswUI() {
        LoginUser user = UserUtils.getCurrentUser();
        return ReturnBean.ok(user);
    }

    /**
     * 修改密码
     *
     * @return
     */
    @LogAnnotation(module = "修改密码")
    @PostMapping("/updatePsw")
    public ReturnBean updatePsw(String password) throws UnsupportedEncodingException {
        Integer id = UserUtils.getCurrentUser().getId();
        User user = new User();
        user.setId(id);
        // 密码加密
        password = passwordEncoder.encode(password);
        ;
        user.setPassword(password);
        userService.update(user);
        return ReturnBean.ok();
    }


    /**
     * 检测用户密码
     *
     * @param oldPassword
     * @return
     */
    @GetMapping("/checkPsw/{oldPassword}")
    public ReturnBean<Boolean> checkPsw(@PathVariable String oldPassword) throws UnsupportedEncodingException {
        SysUser currentUser = UserUtils.getCurrentUser();
        String passwordRe = currentUser.getPassword();
        boolean matches = passwordEncoder.matches(oldPassword, passwordRe);
        if (matches) {
            return ReturnBean.ok(true);
        } else {
            return ReturnBean.ok(false);
        }
    }


    /**
     * 权限设置树形页
     *
     * @return
     */
    @PreAuthorize("hasAuthority('system:user:res')")
    @GetMapping("/userResTree/{userId}")
    public ReturnBean<Object> userResTree(@PathVariable("userId") Integer userId) {
        Integer myUserId = UserUtils.getCurrentUser().getId();
        return ReturnBean.ok(roleService.selectUserResTree(myUserId, userId));
    }


    /**
     * 权限设置
     *
     * @param para
     * @return
     */
    @LogAnnotation(module = "权限设置")
    @PreAuthorize("hasAuthority('system:user:res')")
    @PostMapping("/userRes")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "userId", value = "用户id", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "resIds", value = "资源id集合", required = true)
    })
    public ReturnBean userRes(@RequestBody Map para) {
        roleService.updateUserRes(Integer.valueOf(para.get("userId").toString()), para.get("resIds").toString());
        return ReturnBean.ok();
    }


    /**
     * 角色设置树形页
     *
     * @return
     */
    @PreAuthorize("hasAuthority('system:user:roles')")
    @GetMapping("/userRolesTree")
    public ReturnBean<Object> userRolesTree() {
        Integer userId = UserUtils.getCurrentUser().getId();
        String rolesTree = JSON.toJSONString(roleService.selectUserRolesTree(userId));
        return ReturnBean.ok(JSON.parse(rolesTree));
    }


    /**
     * 获取用户权限
     *
     * @return
     */
    @GetMapping("/findPermissionsByUser")
    public Map findPermissionsByUser() {
        return userService.findPermissionsByUser();
    }

    /**
     * 获取当前的登录用户
     *
     * @return 获取结果
     */
    @GetMapping("ingnore/getCurrentUser")
    public ReturnBean getCurentUser() {
        return ReturnBean.ok(UserUtils.getCurrentUser());
    }

    /**
     * 获取当前的登录用户
     *
     * @param username
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @GetMapping("/getCurrentUser/{username}")
    public ReturnBean<SysUser> getCurrentName(@PathVariable("username") String username) throws InvocationTargetException, IllegalAccessException {
        Map userByUsername = userService.findUserByUsername(username);
        SysUser sysUser = new SysUser();
        BeanUtils.populate(sysUser, userByUsername);
        sysUser.setTx(Common.getObjStr(userByUsername.get("headImg")));
        return ReturnBean.ok(sysUser);
    }

    /**
     * 多个用户ID查询信息
     *
     * @param userIds
     * @return
     */
    @GetMapping("findUserInfoByUserIds/{userIds}")
    public ReturnBean<List<Map<String, Object>>> findUserInfoByUserIds(@PathVariable("userIds") String userIds) {
        return userService.findUserInfoByUserIds(userIds);
    }

    /**
     * 查询所有非警察账户
     *
     * @return
     */
    @GetMapping("notPoliceAccounts")
    public ReturnBean notPoliceAccounts() {
        return ReturnBean.ok(userService.findNotPoliceAccounts());
    }

    /**
     * 根据ID查询
     *
     * @param id 用户ID
     * @return 查询结果
     */
    @GetMapping("findUserInfoById/{id}")
    public ReturnBean<User> findById(@PathVariable("id") Integer id) {
        User user = userService.selectEntityById(id);
        return ReturnBean.ok(user);
    }

    /**
     * 带部门查询所有的警察用户
     * @return
     */
    @GetMapping("findAllAccount")
    public ReturnBean<Map<String,Object>> findAllAccount(){
        return userService.findAllAccount();
    }
}
