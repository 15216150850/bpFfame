package com.bp.sys.controller;


import com.bp.common.anno.LogAnnotation;
import com.bp.common.base.BaseController;
import com.bp.common.bean.ReturnBean;
import com.bp.sys.po.SysEfficiencyModel;
import com.bp.sys.service.SysEfficiencyModelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * @author yanjisheng
 * @version 1.0
 * @Description: 戒治效能分析评估系统评估模型控制层
 * @date 2019年10月23日
 */
@RestController
@RequestMapping(value = "/sysEfficiencyModel")
@Api(value = "戒治效能分析评估系统评估模型")
public class SysEfficiencyModelController extends BaseController {

    @Resource
    private SysEfficiencyModelService sysEfficiencyModelService;

    /**
     * 获取戒治效能分析评估系统评估模型管理页list
     *
     * @return
     */
    @PreAuthorize("hasAuthority('sys:sysEfficiencyModel:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取戒治效能分析评估系统评估模型list")
    public ReturnBean<List<SysEfficiencyModel>> list(@RequestParam Map para) {
        return sysEfficiencyModelService.selectList(para);
    }


    /**
     * 添加戒治效能分析评估系统评估模型
     *
     * @param sysEfficiencyModel 戒治效能分析评估系统评估模型
     * @return
     */
    @LogAnnotation(module = "添加戒治效能分析评估系统评估模型")
    @PreAuthorize("hasAuthority('sys:sysEfficiencyModel:insert')")
    @PostMapping
    @ApiOperation(value = "添加戒治效能分析评估系统评估模型")
    public ReturnBean insert(@RequestBody SysEfficiencyModel sysEfficiencyModel) {
        sysEfficiencyModelService.insert(sysEfficiencyModel);
        return ReturnBean.ok();
    }


    /**
     * 修改戒治效能分析评估系统评估模型
     *
     * @param sysEfficiencyModel 戒治效能分析评估系统评估模型
     * @return
     */
    @LogAnnotation(module = "修改戒治效能分析评估系统评估模型")
    @PreAuthorize("hasAuthority('sys:sysEfficiencyModel:update')")
    @PutMapping
    @ApiOperation(value = "修改戒治效能分析评估系统评估模型")
    public ReturnBean update(@RequestBody SysEfficiencyModel sysEfficiencyModel) {
        sysEfficiencyModelService.update(sysEfficiencyModel);
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取戒治效能分析评估系统评估模型数据
     *
     * @param uuid 戒治效能分析评估系统评估模型 uuid
     * @return
     */
    @PreAuthorize("hasAnyAuthority('sys:sysEfficiencyModel:see','sys:sysEfficiencyModel:update')")
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取戒治效能分析评估系统评估模型数据")
    public ReturnBean<SysEfficiencyModel> getByUuid(@PathVariable String uuid) {
        SysEfficiencyModel sysEfficiencyModel = sysEfficiencyModelService.selectEntityById(uuid);
        return ReturnBean.ok(sysEfficiencyModel);
    }

    /**
     * 删除戒治效能分析评估系统评估模型
     *
     * @param uuids 戒治效能分析评估系统评估模型 uuids
     * @return
     */
    @LogAnnotation(module = "删除戒治效能分析评估系统评估模型")
    @PreAuthorize("hasAuthority('sys:sysEfficiencyModel:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除戒治效能分析评估系统评估模型数据")
    public ReturnBean delete(@RequestParam String uuids) {
        if (uuids.contains(",")) {
            sysEfficiencyModelService.deleteByIds(uuids);
        } else {
            sysEfficiencyModelService.delete(uuids);
        }
        return ReturnBean.ok();
    }


}
