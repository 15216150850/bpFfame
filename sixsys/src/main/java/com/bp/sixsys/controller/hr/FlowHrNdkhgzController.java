package com.bp.sixsys.controller.hr;


import com.bp.common.anno.LogAnnotation;
import com.bp.common.base.BaseActController;
import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.constants.SysConstant;
import com.bp.sixsys.po.hr.FlowHrNdkhgz;
import com.bp.sixsys.service.hr.FlowHrNdkhgzService;
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
 * @Description: 年度考核工作控制层
 * @date 2019年10月10日
 */
@RestController
@RequestMapping(value = "/flowHrNdkhgz")
@Api(value = "年度考核工作")
public class FlowHrNdkhgzController extends BaseActController {

    @Resource
    private FlowHrNdkhgzService flowHrNdkhgzService;

    @Override
    public BaseServiceImplByAct getBaseServiceImplByAct() {
        return flowHrNdkhgzService;
    }

    /**
     * 获取年度考核工作管理页list
     *
     * @return
     */
    @PreAuthorize("hasAuthority('sixsys:flowHrNdkhgz:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取年度考核工作list")
    public ReturnBean<List<FlowHrNdkhgz>> list(@RequestParam Map para) {
        return flowHrNdkhgzService.selectList(para);
    }


    /**
     * 添加年度考核工作
     *
     * @param flowHrNdkhgz 年度考核工作
     * @return
     */
    @LogAnnotation(module = "添加年度考核工作")
    @PreAuthorize("hasAuthority('sixsys:flowHrNdkhgz:insert')")
    @PostMapping
    @ApiOperation(value = "添加年度考核工作")
    public ReturnBean insert(@RequestBody FlowHrNdkhgz flowHrNdkhgz) {
        flowHrNdkhgzService.insert(flowHrNdkhgz);
        return ReturnBean.ok();
    }


    /**
     * 修改年度考核工作
     *
     * @param flowHrNdkhgz 年度考核工作
     * @return
     */
    @LogAnnotation(module = "修改年度考核工作")
    @PreAuthorize("hasAuthority('sixsys:flowHrNdkhgz:update')")
    @PutMapping
    @ApiOperation(value = "修改年度考核工作")
    public ReturnBean update(@RequestBody FlowHrNdkhgz flowHrNdkhgz) {
        flowHrNdkhgzService.update(flowHrNdkhgz);
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取年度考核工作数据
     *
     * @param uuid 年度考核工作 uuid
     * @return
     */
    @PreAuthorize("hasAnyAuthority('sixsys:flowHrNdkhgz:see','sixsys:flowHrNdkhgz:update')")
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取年度考核工作数据")
    public ReturnBean<FlowHrNdkhgz> getByUuid(@PathVariable String uuid) {
        FlowHrNdkhgz flowHrNdkhgz = flowHrNdkhgzService.selectEntityById(uuid);
        return ReturnBean.ok(flowHrNdkhgz);
    }

    /**
     * 删除年度考核工作
     *
     * @param uuids 年度考核工作 uuids
     * @return
     */
    @LogAnnotation(module = "删除年度考核工作")
    @PreAuthorize("hasAuthority('sixsys:flowHrNdkhgz:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除年度考核工作数据")
    public ReturnBean delete(@RequestParam String uuids) {
        if (uuids.contains(SysConstant.COMMA)) {
            flowHrNdkhgzService.deleteByIds(uuids);
        } else {
            flowHrNdkhgzService.delete(uuids);
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
        return flowHrNdkhgzService.submit(ids);
    }

}
