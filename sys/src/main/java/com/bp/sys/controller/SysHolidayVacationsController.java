package com.bp.sys.controller;


import com.bp.common.anno.LogAnnotation;
import com.bp.common.base.BaseController;
import com.bp.common.bean.ReturnBean;
import com.bp.common.constants.SysConstant;
import com.bp.sys.po.SysHolidayVacations;
import com.bp.sys.service.SysHolidayVacationsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * @author zhangyu
 * @version 1.0
 * @Description: 节假日设置控制层
 * @date 2020年01月17日
 */
@RestController
@RequestMapping(value="/sysHolidayVacations")
@Api(value = "节假日设置")
public class SysHolidayVacationsController extends BaseController {

    @Resource
    private SysHolidayVacationsService sysHolidayVacationsService;

    /**
     * 获取节假日设置管理页list
     *
     * @return 返回主页面list
     */
    @PreAuthorize("hasAuthority('sys:sysHolidayVacations:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取节假日设置list")
    public ReturnBean<List<SysHolidayVacations>> list(@RequestParam Map para) {
        return sysHolidayVacationsService.selectList(para);
    }


    /**
     * 添加节假日设置
     *
     * @param sysHolidayVacations 节假日设置
     * @return 返回操作成功
     */
    @LogAnnotation(module ="添加节假日设置")
    @PreAuthorize("hasAuthority('sys:sysHolidayVacations:insert')")
    @PostMapping
    @ApiOperation(value = "添加节假日设置")
    public ReturnBean insert(@RequestBody SysHolidayVacations sysHolidayVacations) {
        sysHolidayVacationsService.insert(sysHolidayVacations);
        return ReturnBean.ok();
    }


    /**
     * 修改节假日设置
     *
     * @param sysHolidayVacations 节假日设置
     * @return 返回操作成功
     */
    @LogAnnotation(module ="修改节假日设置")
    @PreAuthorize("hasAuthority('sys:sysHolidayVacations:update')")
    @PutMapping
    @ApiOperation(value = "修改节假日设置")
    public ReturnBean update(@RequestBody SysHolidayVacations sysHolidayVacations) {
        sysHolidayVacationsService.update(sysHolidayVacations);
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取节假日设置数据
     *
     * @param uuid 节假日设置 uuid
     * @return 返回节假日设置数据
     */
    @PreAuthorize("hasAnyAuthority('sys:sysHolidayVacations:see','sys:sysHolidayVacations:update')")
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取节假日设置数据")
    public ReturnBean<SysHolidayVacations> getByUuid(@PathVariable String uuid) {
        SysHolidayVacations sysHolidayVacations = sysHolidayVacationsService.selectEntityById(uuid);
        return ReturnBean.ok(sysHolidayVacations);
    }

    /**
     * 删除节假日设置
     *
     * @param uuids 节假日设置 uuids
     * @return 返回操作成功
     */
    @LogAnnotation(module ="删除节假日设置")
    @PreAuthorize("hasAuthority('sys:sysHolidayVacations:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除节假日设置数据")
    public ReturnBean delete(@RequestParam String uuids) {
        if(uuids.contains(SysConstant.COMMA)){
            sysHolidayVacationsService.deleteByIds(uuids);
        }else{
            sysHolidayVacationsService.delete(uuids);
        }
        return ReturnBean.ok();
    }


}
