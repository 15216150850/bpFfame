package com.bp.sixsys.controller.hr;


import com.bp.common.anno.LogAnnotation;
import com.bp.common.base.BaseActController;
import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.constants.SysConstant;
import com.bp.sixsys.po.hr.FlowHrBzjlgz;
import com.bp.sixsys.service.hr.FlowHrBzjlgzService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * @author pengwanli
 * @version 1.0
 * @Description: 表彰奖励工作控制层
 * @date 2019年10月11日
 */
@RestController
@RequestMapping(value = "/flowHrBzjlgz")
@Api(value = "表彰奖励工作")
public class FlowHrBzjlgzController extends BaseActController {

    @Resource
    private FlowHrBzjlgzService flowHrBzjlgzService;

    @Override
    public BaseServiceImplByAct getBaseServiceImplByAct() {
        return flowHrBzjlgzService;
    }

    /**
     * 获取表彰奖励工作管理页list
     *
     * @return
     */
    @PreAuthorize("hasAuthority('sixsys:flowHrBzjlgz:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取表彰奖励工作list")
    public ReturnBean<List<FlowHrBzjlgz>> list(@RequestParam Map para) {
        return flowHrBzjlgzService.selectList(para);
    }


    /**
     * 添加表彰奖励工作
     *
     * @param FlowHrBzjlgz 表彰奖励工作
     * @return
     */
    @LogAnnotation(module = "添加表彰奖励工作")
    @PreAuthorize("hasAuthority('sixsys:flowHrBzjlgz:insert')")
    @PostMapping
    @ApiOperation(value = "添加表彰奖励工作")
    public ReturnBean insert(@RequestBody FlowHrBzjlgz FlowHrBzjlgz) {
        flowHrBzjlgzService.insert(FlowHrBzjlgz);
        return ReturnBean.ok();
    }


    /**
     * 修改表彰奖励工作
     *
     * @param FlowHrBzjlgz 表彰奖励工作
     * @return
     */
    @LogAnnotation(module = "修改表彰奖励工作")
    @PreAuthorize("hasAuthority('sixsys:flowHrBzjlgz:update')")
    @PutMapping
    @ApiOperation(value = "修改表彰奖励工作")
    public ReturnBean update(@RequestBody FlowHrBzjlgz FlowHrBzjlgz) {
        flowHrBzjlgzService.update(FlowHrBzjlgz);
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取表彰奖励工作数据
     *
     * @param uuid 表彰奖励工作 uuid
     * @return
     */
    @PreAuthorize("hasAnyAuthority('sixsys:flowHrBzjlgz:see','sixsys:flowHrBzjlgz:update')")
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取表彰奖励工作数据")
    public ReturnBean<FlowHrBzjlgz> getByUuid(@PathVariable String uuid) {
        FlowHrBzjlgz FlowHrBzjlgz = flowHrBzjlgzService.selectEntityById(uuid);
        return ReturnBean.ok(FlowHrBzjlgz);
    }

    /**
     * 删除表彰奖励工作
     *
     * @param uuids 表彰奖励工作 uuids
     * @return
     */
    @LogAnnotation(module = "删除表彰奖励工作")
    @PreAuthorize("hasAuthority('sixsys:flowHrBzjlgz:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除表彰奖励工作数据")
    public ReturnBean delete(@RequestParam String uuids) {
        if (uuids.contains(SysConstant.COMMA)) {
            flowHrBzjlgzService.deleteByIds(uuids);
        } else {
            flowHrBzjlgzService.delete(uuids);
        }
        return ReturnBean.ok();
    }


    /**
     * 启动流程
     *
     * @param ids
     * @return
     */
    @PostMapping("/submit")
    public ReturnBean submit(@RequestParam("ids") String ids) {
        return flowHrBzjlgzService.submit(ids);
    }

}
