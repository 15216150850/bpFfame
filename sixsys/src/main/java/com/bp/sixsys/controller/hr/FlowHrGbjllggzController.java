package com.bp.sixsys.controller.hr;


import com.bp.common.anno.LogAnnotation;
import com.bp.common.base.BaseActController;
import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.constants.SysConstant;
import com.bp.sixsys.po.hr.FlowHrGbjllggz;
import com.bp.sixsys.service.hr.FlowHrGbjllggzService;
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
 * @Description: 干部交流轮岗工作控制层
 * @date 2019年10月10日
 */
@RestController
@RequestMapping(value = "/flowHrGbjllggz")
@Api(value = "干部交流轮岗工作")
public class FlowHrGbjllggzController extends BaseActController {

    @Resource
    private FlowHrGbjllggzService flowHrGbjllggzService;

    @Override
    public BaseServiceImplByAct getBaseServiceImplByAct() {
        return flowHrGbjllggzService;
    }

    /**
     * 获取干部交流轮岗工作管理页list
     *
     * @return
     */
    @PreAuthorize("hasAuthority('sixsys:flowHrGbjllggz:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取干部交流轮岗工作list")
    public ReturnBean<List<FlowHrGbjllggz>> list(@RequestParam Map para) {
        return flowHrGbjllggzService.selectList(para);
    }


    /**
     * 添加干部交流轮岗工作
     *
     * @param flowHrGbjllggz 干部交流轮岗工作
     * @return
     */
    @LogAnnotation(module = "添加干部交流轮岗工作")
    @PreAuthorize("hasAuthority('sixsys:flowHrGbjllggz:insert')")
    @PostMapping
    @ApiOperation(value = "添加干部交流轮岗工作")
    public ReturnBean insert(@RequestBody FlowHrGbjllggz flowHrGbjllggz) {
        flowHrGbjllggzService.insert(flowHrGbjllggz);
        return ReturnBean.ok();
    }


    /**
     * 修改干部交流轮岗工作
     *
     * @param flowHrGbjllggz 干部交流轮岗工作
     * @return
     */
    @LogAnnotation(module = "修改干部交流轮岗工作")
    @PreAuthorize("hasAuthority('sixsys:flowHrGbjllggz:update')")
    @PutMapping
    @ApiOperation(value = "修改干部交流轮岗工作")
    public ReturnBean update(@RequestBody FlowHrGbjllggz flowHrGbjllggz) {
        flowHrGbjllggzService.update(flowHrGbjllggz);
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取干部交流轮岗工作数据
     *
     * @param uuid 干部交流轮岗工作 uuid
     * @return
     */
    @PreAuthorize("hasAnyAuthority('sixsys:flowHrGbjllggz:see','sixsys:flowHrGbjllggz:update')")
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取干部交流轮岗工作数据")
    public ReturnBean<FlowHrGbjllggz> getByUuid(@PathVariable String uuid) {
        FlowHrGbjllggz flowHrGbjllggz = flowHrGbjllggzService.selectEntityById(uuid);
        return ReturnBean.ok(flowHrGbjllggz);
    }

    /**
     * 删除干部交流轮岗工作
     *
     * @param uuids 干部交流轮岗工作 uuids
     * @return
     */
    @LogAnnotation(module = "删除干部交流轮岗工作")
    @PreAuthorize("hasAuthority('sixsys:flowHrGbjllggz:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除干部交流轮岗工作数据")
    public ReturnBean delete(@RequestParam String uuids) {
        if (uuids.contains(SysConstant.COMMA)) {
            flowHrGbjllggzService.deleteByIds(uuids);
        } else {
            flowHrGbjllggzService.delete(uuids);
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
        return flowHrGbjllggzService.submit(ids);
    }


}
