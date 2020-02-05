package com.bp.sys.controller;


import com.bp.common.anno.LogAnnotation;
import com.bp.common.base.BaseController;
import com.bp.common.bean.ReturnBean;
import com.bp.sys.po.SysSafetyPrediction;
import com.bp.sys.service.SysSafetyPredictionService;
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
 * @Description: sys_safety_prediction控制层
 * @date 2019年10月25日
 */
@RestController
@RequestMapping(value = "/sysSafetyPrediction")
@Api(description = "sys_safety_prediction")
public class SysSafetyPredictionController extends BaseController {

    @Resource
    private SysSafetyPredictionService sysSafetyPredictionService;

    /**
     * 获取sys_safety_prediction管理页list
     *
     * @return
     */
    @PreAuthorize("hasAuthority('sys:sysSafetyPrediction:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取sys_safety_predictionlist")
    public ReturnBean<List<SysSafetyPrediction>> list(@RequestParam Map para) {
        return sysSafetyPredictionService.selectList(para);
    }


    /**
     * 添加sys_safety_prediction
     *
     * @param sysSafetyPrediction sys_safety_prediction
     * @return
     */
    @LogAnnotation(module = "添加sys_safety_prediction")
    @PreAuthorize("hasAuthority('sys:sysSafetyPrediction:insert')")
    @PostMapping
    @ApiOperation(value = "添加sys_safety_prediction")
    public ReturnBean insert(@RequestBody SysSafetyPrediction sysSafetyPrediction) {
        sysSafetyPredictionService.insert(sysSafetyPrediction);
        return ReturnBean.ok();
    }


    /**
     * 修改sys_safety_prediction
     *
     * @param sysSafetyPrediction sys_safety_prediction
     * @return
     */
    @LogAnnotation(module = "修改sys_safety_prediction")
    @PreAuthorize("hasAuthority('sys:sysSafetyPrediction:update')")
    @PutMapping
    @ApiOperation(value = "修改sys_safety_prediction")
    public ReturnBean update(@RequestBody SysSafetyPrediction sysSafetyPrediction) {
        sysSafetyPredictionService.update(sysSafetyPrediction);
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取sys_safety_prediction数据
     *
     * @param uuid sys_safety_prediction uuid
     * @return
     */
    @PreAuthorize("hasAnyAuthority('sys:sysSafetyPrediction:see','sys:sysSafetyPrediction:update')")
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取sys_safety_prediction数据")
    public ReturnBean<SysSafetyPrediction> getByUuid(@PathVariable String uuid) {
        SysSafetyPrediction sysSafetyPrediction = sysSafetyPredictionService.selectEntityById(uuid);
        return ReturnBean.ok(sysSafetyPrediction);
    }

    /**
     * 删除sys_safety_prediction
     *
     * @param uuids sys_safety_prediction uuids
     * @return
     */
    @LogAnnotation(module = "删除sys_safety_prediction")
    @PreAuthorize("hasAuthority('sys:sysSafetyPrediction:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除sys_safety_prediction数据")
    public ReturnBean delete(@RequestParam String uuids) {
        if (uuids.contains(",")) {
            sysSafetyPredictionService.deleteByIds(uuids);
        } else {
            sysSafetyPredictionService.delete(uuids);
        }
        return ReturnBean.ok();
    }


}
