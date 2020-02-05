package com.bp.sys.controller;


import com.bp.common.anno.LogAnnotation;
import com.bp.common.base.BaseController;
import com.bp.common.bean.ReturnBean;
import com.bp.sys.po.SysDangerModel;
import com.bp.sys.service.SysDangerModelService;
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
 * @Description: 戒毒人员危险性分析评估模型控制层
 * @date 2019年10月23日
 */
@RestController
@RequestMapping(value = "/sysDangerModel")
@Api(description = "戒毒人员危险性分析评估模型")
public class SysDangerModelController extends BaseController {

    @Resource
    private SysDangerModelService sysDangerModelService;

    /**
     * 获取戒毒人员危险性分析评估模型管理页list
     *
     * @return
     */
    @PreAuthorize("hasAuthority('sys:sysDangerModel:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取戒毒人员危险性分析评估模型list")
    public ReturnBean<List<SysDangerModel>> list(@RequestParam Map para) {
        return sysDangerModelService.selectList(para);
    }


    /**
     * 添加戒毒人员危险性分析评估模型
     *
     * @param sysDangerModel 戒毒人员危险性分析评估模型
     * @return
     */
    @LogAnnotation(module = "添加戒毒人员危险性分析评估模型")
    @PreAuthorize("hasAuthority('sys:sysDangerModel:insert')")
    @PostMapping
    @ApiOperation(value = "添加戒毒人员危险性分析评估模型")
    public ReturnBean insert(@RequestBody SysDangerModel sysDangerModel) {
        sysDangerModelService.insert(sysDangerModel);
        return ReturnBean.ok();
    }


    /**
     * 修改戒毒人员危险性分析评估模型
     *
     * @param sysDangerModel 戒毒人员危险性分析评估模型
     * @return
     */
    @LogAnnotation(module = "修改戒毒人员危险性分析评估模型")
    @PreAuthorize("hasAuthority('sys:sysDangerModel:update')")
    @PutMapping
    @ApiOperation(value = "修改戒毒人员危险性分析评估模型")
    public ReturnBean update(@RequestBody SysDangerModel sysDangerModel) {
        sysDangerModelService.update(sysDangerModel);
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取戒毒人员危险性分析评估模型数据
     *
     * @param uuid 戒毒人员危险性分析评估模型 uuid
     * @return
     */
    @PreAuthorize("hasAnyAuthority('sys:sysDangerModel:see','sys:sysDangerModel:update')")
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取戒毒人员危险性分析评估模型数据")
    public ReturnBean<SysDangerModel> getByUuid(@PathVariable String uuid) {
        SysDangerModel sysDangerModel = sysDangerModelService.selectEntityById(uuid);
        return ReturnBean.ok(sysDangerModel);
    }

    /**
     * 删除戒毒人员危险性分析评估模型
     *
     * @param uuids 戒毒人员危险性分析评估模型 uuids
     * @return
     */
    @LogAnnotation(module = "删除戒毒人员危险性分析评估模型")
    @PreAuthorize("hasAuthority('sys:sysDangerModel:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除戒毒人员危险性分析评估模型数据")
    public ReturnBean delete(@RequestParam String uuids) {
        if (uuids.contains(",")) {
            sysDangerModelService.deleteByIds(uuids);
        } else {
            sysDangerModelService.delete(uuids);
        }
        return ReturnBean.ok();
    }


}
