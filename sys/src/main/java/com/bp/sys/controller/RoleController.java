package com.bp.sys.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bp.common.anno.LogAnnotation;
import com.bp.common.base.BaseController;
import com.bp.common.bean.ReturnBean;
import com.bp.common.utils.Common;
import com.bp.common.utils.UserUtils;
import com.bp.sys.po.Role;
import com.bp.sys.service.RoleService;
import com.bp.sys.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @version 1.0
 * @Description: 角色管理控制层
 * @date 2016年7月16日
 */
@RestController
@RequestMapping(value = "system/role")
public class RoleController extends BaseController {

    @Resource
    private RoleService roleService;
    @Autowired
    private UserService userService;


    /**
     * 角色管理
     *
     * @return
     */
    @PreAuthorize("hasAuthority('system:role:list')")
    @GetMapping("/list")
    public ReturnBean<List<Role>> list(@RequestParam Map para) {
        return roleService.selectList(para);
    }


    /**
     * 添加角色
     *
     * @param role 角色
     * @return
     */
    @LogAnnotation(module = "添加角色")
    @PreAuthorize("hasAuthority('system:role:insert')")
    @PostMapping
    public ReturnBean insert(@RequestBody Role role) {
        validateEntity(role);
        roleService.insert(role);
        return ReturnBean.ok();
    }


    /**
     * 修改角色
     *
     * @param role 角色
     * @return
     */
    @LogAnnotation(module = "修改角色")
    @PreAuthorize("hasAuthority('system:role:update')")
    @PutMapping
    public ReturnBean update(@RequestBody Role role) {
        validateEntity(role);
        roleService.update(role);
        return ReturnBean.ok();
    }


    /**
     * 删除角色
     *
     * @param id 角色id
     * @return
     */
    @LogAnnotation(module = "删除角色")
    @PreAuthorize("hasPermission(null ,'system:role:delete')")
    @PostMapping("system/role/delete")
    @ResponseBody
    public ReturnBean delete(Integer id, String code) {
        roleService.delete(id, code);
        return ReturnBean.ok();
    }


    /**
     * 批量删除角色
     *
     * @return
     */
    @LogAnnotation(module = "批量删除角色")
    @PreAuthorize("hasAuthority('system:role:delete')")
    @DeleteMapping("/deleteByIdsAndCodes")
    public ReturnBean deleteByIdsAndCodes(@RequestBody Map para) {
        roleService.deleteByIdsAndCodes(Common.getObjStr(para.get("ids")), Common.getObjStr(para.get("codes")));
        return ReturnBean.ok();
    }


    /**
     * 查看角色信息
     *
     * @param id 角色id
     * @return
     */
    @PreAuthorize("hasAnyAuthority('system:role:see','system:role:update')")
    @GetMapping("/{id}")
    public ReturnBean<Role> see(@PathVariable Integer id) {
        Role role = roleService.selectEntityById(id);
        return ReturnBean.ok(role);
    }


    /**
     * 检测角色名
     *
     * @param roleName 角色名
     * @param id       角色id
     * @return
     */
    @GetMapping("/checkName/{roleName}/{id}")
    @ResponseBody
    public ReturnBean<Boolean> checkName(@PathVariable String roleName, @PathVariable String id) {
        Integer count = roleService.checkName(roleName, "null".equals(id) ? null : Integer.valueOf(id));
        if (count > 0) {
            return ReturnBean.ok(false);
        } else {
            return ReturnBean.ok(true);
        }
    }


    /**
     * 检测角色编码
     *
     * @param code 角色编码
     * @param id   角色id
     * @return
     */
    @GetMapping("/checkCode/{code}/{id}")
    public ReturnBean<Boolean> checkCode(@PathVariable String code, @PathVariable String id) {
        Integer count = roleService.checkCode(code, "null".equals(id) ? null : Integer.valueOf(id));
        if (count > 0) {
            return ReturnBean.ok(false);
        } else {
            return ReturnBean.ok(true);
        }
    }


    /**
     * 权限设置树形页
     *
     * @param code 角色编码
     * @return
     */
    @GetMapping("/roleResTree/{code}")
    public ReturnBean<Object> roleResTree(@PathVariable String code) {
        Integer userId = UserUtils.getCurrentUser().getId();
        JSONArray resTree = roleService.selectRoleResTree(code, userId);
        return ReturnBean.ok(JSON.parse(resTree.toString()));
    }


    /**
     * 权限设置
     *
     * @return
     */
    @LogAnnotation(module = "角色权限设置")
//    @PreAuthorize("hasPermission(null ,'system:role:res')")
    @PostMapping("/roleRes")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "roleCode", value = "角色编码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "resIds", value = "资源id集合", required = true)
    })
    public ReturnBean roleRes(@RequestBody Map<String, Object> para) {
        roleService.updateRoleRes(Common.getObjStr(para.get("roleCode")), Common.getObjStr(para.get("resIds")));
        return ReturnBean.ok();
    }


    /**
     * 功能权限设置
     *
     * @param userId 用户id
     * @param resIds 资源ids
     * @return
     */
    @LogAnnotation(module = "功能权限设置")
    @PreAuthorize("hasAuthority('system:role:res')")
    @PostMapping("system/role/userRes")
    @ResponseBody
    public ReturnBean userRes(Integer userId, String resIds) {
        roleService.updateUserRes(userId, resIds);
        return ReturnBean.ok();
    }

    /**
     * 用户与部门树形页
     *
     * @param roleCode
     * @return
     */
    @GetMapping("selectDeptAndUserTree/{roleCode}")
    public ReturnBean<Object> selectDeptAndUserTree(@PathVariable String roleCode) {
        JSONArray deptAndUserTree = roleService.selectDeptAndUserTree(roleCode);
        request.setAttribute("deptAndUserTree", deptAndUserTree);
        return ReturnBean.ok(JSON.parse(deptAndUserTree.toString()));
    }

    /**
     * 用户与部门树形页
     *
     * @return
     */
    @GetMapping("selectUserTree")
    public ReturnBean<Object> selectUserTree() {
        JSONArray deptAndUserTree = roleService.selectUserTree();
        request.setAttribute("deptAndUserTree", deptAndUserTree);
        return ReturnBean.ok(JSON.parse(deptAndUserTree.toString()));
    }

    /**
     * 给角色批量设置用户
     *
     * @param para
     * @return
     */
    @LogAnnotation(module = "给角色批量设置用户")
    @PreAuthorize("hasAuthority('system:role:insert')")
    @PostMapping("userRole")
    @ResponseBody
    public ReturnBean userRole(@RequestBody Map<String, Object> para) {
        roleService.insertUsersRole(String.valueOf(para.get("userIds")), String.valueOf(para.get("roleCode")));
        return ReturnBean.ok();
    }
}
