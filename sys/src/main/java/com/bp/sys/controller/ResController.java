package com.bp.sys.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bp.common.anno.LogAnnotation;
import com.bp.common.base.BaseController;
import com.bp.common.bean.ReturnBean;
import com.bp.common.entity.SysUser;
import com.bp.sys.po.Res;
import com.bp.sys.po.dto.ResDto;
import com.bp.sys.service.ResService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static com.bp.common.utils.UserUtils.getCurrentUser;

/**
 * @author
 * @version 1.0
 * @Description: 资源管理控制层
 * @date 18/12/25
 */
@RestController
@RequestMapping(value = "system/res")
public class ResController extends BaseController {

    @Resource
    private ResService resService;


    /**
     * 资源管理页面数据
     *
     * @return
     */
    @GetMapping("/listTable/{pid}")
    public ReturnBean<List<ResDto>> dataList(@PathVariable String pid) {
        List<ResDto> list = resService.selectTableList(pid);
        return ReturnBean.ok(list);
    }


    /**
     * 新增/修改/查看页
     *
     * @param id 资源id
     * @return
     */
    @PreAuthorize("hasAnyAuthority('system:res:insert','system:res:see','system:res:update')")
    @GetMapping("/{id}")
    public ReturnBean<Res> getData(@PathVariable Integer id) {
        Res res = resService.selectEntityById(id);
        return ReturnBean.ok(res);
    }


    /**
     * 获取资源树
     *
     * @return
     */
    @GetMapping("/resTree")
    public ReturnBean<Object> resTreeUI() {
        List list = resService.selectAllTree();
        JSONArray resTree = JSONArray.parseArray(JSON.toJSONString(list));
        return ReturnBean.ok(JSON.parse(resTree.toString()));
    }


    /**
     * 添加资源
     *
     * @param res 资源实体
     * @return
     */
    @LogAnnotation(module = "添加资源")
    @PreAuthorize("hasAuthority('system:res:insert')")
    @PostMapping
    public ReturnBean insert(@RequestBody Res res) {
        //  validateEntity(res);
        if (res.getPid() == null) {
            res.setPid(0);
        }
        resService.insert(res);
        return ReturnBean.ok();
    }


    /**
     * 修改资源
     *
     * @param res 资源实体
     * @return
     */
    @LogAnnotation(module = "修改资源")
    @PreAuthorize("hasAuthority('system:res:update')")
    @PutMapping
    public ReturnBean update(@RequestBody Res res) {
        //   validateEntity(res);
        if (res.getPid() == null) {
            res.setPid(0);
        }
        resService.update(res);
        return ReturnBean.ok();
    }


    /**
     * 删除资源
     *
     * @param id 资源id
     * @return
     */
    @LogAnnotation(module = "删除资源")
    @PreAuthorize("hasAuthority('system:res:delete')")
    @DeleteMapping("/{id}")
    public ReturnBean delete(@PathVariable Integer id) {
        resService.delete(id);
        return ReturnBean.ok();
    }


    /**
     * 获取首页侧边栏资源
     *
     * @return
     */
    @PostMapping("/selectRoleRes")
    public List selectRoleRes() {
        SysUser sysUser = getCurrentUser();
        logger.debug("当前登录用户:currentUser:{}", sysUser);

        Integer userId = Integer.valueOf(String.valueOf(sysUser.getId()));
        //logger.debug("当前用户角色:roleCode:{}",roleCode);
        String roleCode = null;
        if ("admin".equals(sysUser.getUsername())) {
            roleCode = "admin";
        } else {
            roleCode = "user";
        }
        List resList = resService.selectRoleUrlResByUserId(roleCode, userId, 0);
        return resList;
    }


    /**
     * 省局平台侧边资源
     *
     * @return
     */
    @PostMapping("/selectRoleResSjpt")
    public List selectRoleResSjpt() {
        SysUser sysUser = getCurrentUser();
        logger.debug("当前登录用户:currentUser:{}", sysUser);

        Integer userId = Integer.valueOf(String.valueOf(sysUser.getId()));
        //logger.debug("当前用户角色:roleCode:{}",roleCode);
        String roleCode = null;
        if ("admin".equals(sysUser.getUsername())) {
            roleCode = "admin";
        } else {
            roleCode = "user";
        }
        List resList = resService.selectRoleUrlResByUserIdSjpt(roleCode, userId, 0);
        return resList;
    }

    /**
     * 查询用户拥有的资源树
     *
     * @return
     */
    @GetMapping("selectRoleResByUser")
    public ReturnBean<Object> selectRoleResByUser(@RequestParam("userId") Integer userId) {
        List resList = resService.selectRoleResByUser(userId);
        return ReturnBean.ok(JSONArray.parseArray(JSON.toJSONString(resList)));
    }

}
