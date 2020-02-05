package com.bp.sixsys.controller;


import com.bp.common.base.BaseController;

import com.bp.common.bean.ReturnBean;
import com.bp.common.entity.FlowSixSys;
import com.bp.sixsys.service.FlowSixSysService;
import com.bp.common.anno.LogAnnotation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.bp.common.constants.SysConstant;

import java.util.Map;
import java.util.List;
import javax.annotation.Resource;


/**
* @author zhongxinkai
* @version 1.0
* @Description: 六大体系流程汇总表控制层
* @date 2019年11月20日
*/
@RestController
@RequestMapping(value="/flowSixSys")
@Api(value = "六大体系流程汇总表")
public class FlowSixSysController extends BaseController {

    @Resource
    private FlowSixSysService flowSixSysService;

    /**
     * 获取六大体系流程汇总表管理页list
     *
     * @return 返回主页面list
     */
    @PreAuthorize("hasAuthority('sixsys:flowSixSys:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取六大体系流程汇总表list")
    public ReturnBean<List<FlowSixSys>> list(@RequestParam Map para) {
        return flowSixSysService.selectList(para);
    }


    /**
     * 添加六大体系流程汇总表
     *
     * @param flowSixSys 六大体系流程汇总表
     * @return 返回操作成功
     */
    @LogAnnotation(module ="添加六大体系流程汇总表")
    @PreAuthorize("hasAuthority('sixsys:flowSixSys:insert')")
    @PostMapping
    @ApiOperation(value = "添加六大体系流程汇总表")
    public ReturnBean insert(@RequestBody FlowSixSys flowSixSys) {
        flowSixSysService.insert(flowSixSys);
        return ReturnBean.ok();
    }

 
    /**
     * 修改六大体系流程汇总表
     *
     * @param flowSixSys 六大体系流程汇总表
     * @return 返回操作成功
     */
    @LogAnnotation(module ="修改六大体系流程汇总表")
    @PreAuthorize("hasAuthority('sixsys:flowSixSys:update')")
    @PutMapping
    @ApiOperation(value = "修改六大体系流程汇总表")
    public ReturnBean update(@RequestBody FlowSixSys flowSixSys) {
        flowSixSysService.update(flowSixSys);
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取六大体系流程汇总表数据
     *
     * @param uuid 六大体系流程汇总表 uuid
     * @return 返回六大体系流程汇总表数据
     */
//    @PreAuthorize("hasAnyAuthority('sixsys:flowSixSys:see','sixsys:flowSixSys:update')")
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取六大体系流程汇总表数据")
    public ReturnBean<FlowSixSys> getByUuid(@PathVariable String uuid) {
        FlowSixSys flowSixSys = flowSixSysService.selectEntityById(uuid);
        return ReturnBean.ok(flowSixSys);
    }

    /**
     * 删除六大体系流程汇总表
     *
     * @param uuids 六大体系流程汇总表 uuids
     * @return 返回操作成功
     */
    @LogAnnotation(module ="删除六大体系流程汇总表")
    @PreAuthorize("hasAuthority('sixsys:flowSixSys:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除六大体系流程汇总表数据")
    public ReturnBean delete(@RequestParam String uuids) {
        if(uuids.contains(SysConstant.COMMA)){
            flowSixSysService.deleteByIds(uuids);
        }else{
            flowSixSysService.delete(uuids);
        }
        return ReturnBean.ok();
    }
    @GetMapping("findBysupseUuid/{uuid}")
    public ReturnBean<FlowSixSys> findBysupseUuid(@PathVariable("uuid") String uuid){
        return flowSixSysService.findBysupseUuid(uuid);
    }

    /**
     * 我的申请
     * @param map
     * @return
     */
    @GetMapping("myApplications")
    public ReturnBean myApplications(@RequestParam Map map){
        return flowSixSysService.myApplications(map);
    }
}
