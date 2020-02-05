package com.bp.sys.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bp.common.anno.LogAnnotation;
import com.bp.common.base.BaseController;
import com.bp.common.bean.ReturnBean;
import com.bp.common.utils.UserUtils;
import com.bp.sys.po.TempRes;
import com.bp.sys.po.TempResDto;
import com.bp.sys.service.RoleService;
import com.bp.sys.service.TempResService;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * @author
 * @version 1.0
 * @Description: 临时授权控制层
 * @date 2018年10月30日
 */
@RestController
@RequestMapping(value = "system/tempRes")
public class
TempResController extends BaseController {

    @Resource
    private TempResService tempResService;

    @Resource
    private RoleService roleService;


    /**
     * 临时授权管理
     *
     * @param para 搜索参数
     * @return
     */
    @PreAuthorize("hasAuthority('system:tempRes:list')")
    @GetMapping("/listData")
    public ReturnBean<List<TempRes>> list(@RequestParam Map para) {
        para.put("roleCode", UserUtils.getCurrentUser().getSysRoles());
        para.put("userId", UserUtils.getCurrentUser().getId());
        return tempResService.selectList(para);
    }


    /**
     * 添加临时授权
     *
     * @param tempResDto 临时授权
     * @return
     */
    @LogAnnotation(module = "添加临时授权")
    @PreAuthorize("hasAuthority('system:tempRes:insert')")
    @PostMapping
    public ReturnBean insert(@RequestBody TempResDto tempResDto) {
        String resId = tempResDto.getResId();
        tempResService.insertTempRes(tempResDto, resId);
        return ReturnBean.ok();
    }


    /**
     * 修改临时授权
     *
     * @param tempResDto 临时授权
     * @return
     */
    @LogAnnotation(module = "修改临时授权")
    @PreAuthorize("hasAuthority('system:tempRes:update')")
    @PutMapping
    public ReturnBean update(@RequestBody TempResDto tempResDto) {
        String resId = tempResDto.getResId();
        tempResService.updateTempRes(tempResDto, resId);
        return ReturnBean.ok();
    }

    /**
     * 查看用户信息
     *
     * @param id 用户id
     * @return
     */
    @PreAuthorize("hasAnyAuthority('system:tempRes:see','system:tempRes:update')")
    @GetMapping("/{id}")
    public ReturnBean<TempResDto> see(@PathVariable("id") Integer id) {
        TempResDto tempResDto = new TempResDto();
        BeanMap beanMap = BeanMap.create(tempResDto);
        TempRes tempRes = tempResService.selectEntityById(id);
        beanMap.putAll(BeanMap.create(tempRes));
        return ReturnBean.ok(tempResDto);
    }

    /**
     * 删除临时授权
     *
     * @param id 临时授权 id
     * @return
     */
    @LogAnnotation(module = "删除临时授权")
    @PreAuthorize("hasAuthority('system:tempRes:delete')")
    @DeleteMapping("/{id}")
    public ReturnBean delete(@PathVariable("id") Integer id) {
        tempResService.delete(id);
        return ReturnBean.ok();
    }

    /**
     * 批量删除临时授权
     *
     * @param ids 临时授权 ids
     * @return
     */
    @LogAnnotation(module = "批量删除临时授权")
    @PreAuthorize("hasAuthority('system:tempRes:delete')")
    @DeleteMapping("/deleteByIds/{ids}")
    public ReturnBean deleteByIds(@PathVariable("ids") String ids) {
        tempResService.deleteByIds(ids);
        return ReturnBean.ok();
    }

    /**
     * 资源树形页
     *
     * @return
     */
    @PreAuthorize("hasAuthority('system:tempRes:insert')")
    @GetMapping("/resTree/{pid}")
    public ReturnBean<Object> resTree(@PathVariable("pid") Integer pid) {
        Integer userId = UserUtils.getCurrentUser().getId();
        JSONArray resTree = roleService.selectResTreeByTempResId(pid, userId);
        request.setAttribute("resTree", resTree);
        return ReturnBean.ok(JSON.parse(resTree.toString()));
    }
}
