package com.bp.sixsys.controller.hr;


import com.bp.common.anno.LogAnnotation;
import com.bp.common.base.BaseActController;
import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.constants.SysConstant;
import com.bp.sixsys.po.hr.FlowHrPyrygz;
import com.bp.sixsys.service.hr.FlowHrPyrygzService;
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
 * @Description: 聘用人员工作控制层
 * @date 2019年10月11日
 */
@RestController
@RequestMapping(value = "/flowHrPyrygz")
@Api(value = "聘用人员工作")
public class FlowHrPyrygzController extends BaseActController {

    @Resource
    private FlowHrPyrygzService flowHrPyrygzService;

    @Override
    public BaseServiceImplByAct getBaseServiceImplByAct() {
        return flowHrPyrygzService;
    }

    /**
     * 获取聘用人员工作管理页list
     *
     * @return
     */
    @PreAuthorize("hasAuthority('sixsys:flowHrPyrygz:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取聘用人员工作list")
    public ReturnBean<List<FlowHrPyrygz>> list(@RequestParam Map para) {
        return flowHrPyrygzService.selectList(para);
    }


    /**
     * 添加聘用人员工作
     *
     * @param flowHrPyrygz 聘用人员工作
     * @return
     */
    @LogAnnotation(module = "添加聘用人员工作")
    @PreAuthorize("hasAuthority('sixsys:flowHrPyrygz:insert')")
    @PostMapping
    @ApiOperation(value = "添加聘用人员工作")
    public ReturnBean insert(@RequestBody FlowHrPyrygz flowHrPyrygz) {
        flowHrPyrygzService.insert(flowHrPyrygz);
        return ReturnBean.ok();
    }


    /**
     * 修改聘用人员工作
     *
     * @param flowHrPyrygz 聘用人员工作
     * @return
     */
    @LogAnnotation(module = "修改聘用人员工作")
    @PreAuthorize("hasAuthority('sixsys:flowHrPyrygz:update')")
    @PutMapping
    @ApiOperation(value = "修改聘用人员工作")
    public ReturnBean update(@RequestBody FlowHrPyrygz flowHrPyrygz) {
        flowHrPyrygzService.update(flowHrPyrygz);
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取聘用人员工作数据
     *
     * @param uuid 聘用人员工作 uuid
     * @return
     */
    @PreAuthorize("hasAnyAuthority('sixsys:flowHrPyrygz:see','sixsys:flowHrPyrygz:update')")
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取聘用人员工作数据")
    public ReturnBean<FlowHrPyrygz> getByUuid(@PathVariable String uuid) {
        FlowHrPyrygz flowHrPyrygz = flowHrPyrygzService.selectEntityById(uuid);
        return ReturnBean.ok(flowHrPyrygz);
    }

    /**
     * 删除聘用人员工作
     *
     * @param uuids 聘用人员工作 uuids
     * @return
     */
    @LogAnnotation(module = "删除聘用人员工作")
    @PreAuthorize("hasAuthority('sixsys:flowHrPyrygz:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除聘用人员工作数据")
    public ReturnBean delete(@RequestParam String uuids) {
        if (uuids.contains(SysConstant.COMMA)) {
            flowHrPyrygzService.deleteByIds(uuids);
        } else {
            flowHrPyrygzService.delete(uuids);
        }
        return ReturnBean.ok();
    }


    /**
     * 启动流程
     *
     * @param ids
     * @return 返回流程状态信息
     */
    @PostMapping("/submit")
    public ReturnBean submit(@RequestParam("ids") String ids) {
        return flowHrPyrygzService.submit(ids);
    }

}
