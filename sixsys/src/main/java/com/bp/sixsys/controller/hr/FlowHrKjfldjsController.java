package com.bp.sixsys.controller.hr;


import com.bp.common.base.BaseActController;

import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.anno.LogAnnotation;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.po.hr.FlowHrKjfldjs;
import com.bp.sixsys.service.hr.FlowHrKjfldjsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.bp.common.constants.SysConstant;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;


/**
* @author zhangyu
* @version 1.0
* @Description: 科级非领导职务晋升工作控制层
* @date 2019年11月22日
*/
@RestController
@RequestMapping(value="/flowHrKjfldjs")
@Api(value = "科级非领导职务晋升工作")
public class FlowHrKjfldjsController extends BaseActController {

    @Resource
    private FlowHrKjfldjsService flowHrKjfldjsService;

    /**
     * 获取科级非领导职务晋升工作管理页list
     *
     * @return
     */
    @PreAuthorize("hasAuthority('sixsys:flowHrKjfldjs:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取科级非领导职务晋升工作list")
    public ReturnBean<List<FlowHrKjfldjs>> list(@RequestParam Map para) {
        // 只能查看自己创建的记录
        para.put("currentUserId", UserUtils.getCurrentUser().getId());
        return flowHrKjfldjsService.selectList(para);
    }

    /**
     * 添加科级非领导职务晋升工作
     *
     * @param flowHrKjfldjs 科级非领导职务晋升工作
     * @return
     */
    @LogAnnotation(module = "添加科级非领导职务晋升工作")
    @PreAuthorize("hasAuthority('sixsys:flowHrKjfldjs:insert')")
    @PostMapping
    @ApiOperation(value = "添加科级非领导职务晋升工作")
    public ReturnBean insert(@RequestBody FlowHrKjfldjs flowHrKjfldjs) {
        flowHrKjfldjsService.insert(flowHrKjfldjs);
        return ReturnBean.ok();
    }

    /**
     * 修改科级非领导职务晋升工作
     *
     * @param flowHrKjfldjs 科级非领导职务晋升工作
     * @return
     */
    @LogAnnotation(module = "修改科级非领导职务晋升工作")
    @PreAuthorize("hasAuthority('sixsys:flowHrKjfldjs:update')")
    @PutMapping
    @ApiOperation(value = "修改科级非领导职务晋升工作")
    public ReturnBean update(@RequestBody FlowHrKjfldjs flowHrKjfldjs) {
        flowHrKjfldjsService.update(flowHrKjfldjs);
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取科级非领导职务晋升工作数据
     *
     * @param uuid 科级非领导职务晋升工作 uuid
     * @return
     */
    @PreAuthorize("hasAnyAuthority('sixsys:flowHrKjfldjs:see','sixsys:flowHrKjfldjs:update')")
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取科级非领导职务晋升工作数据")
    public ReturnBean<FlowHrKjfldjs> getByUuid(@PathVariable String uuid) {
        FlowHrKjfldjs flowHrKjfldjs = flowHrKjfldjsService.selectEntityById(uuid);
        return ReturnBean.ok(flowHrKjfldjs);
    }

    /**
     * 删除科级非领导职务晋升工作
     *
     * @param uuids 科级非领导职务晋升工作 uuids
     * @return
     */
    @LogAnnotation(module = "删除科级非领导职务晋升工作")
    @PreAuthorize("hasAuthority('sixsys:flowHrKjfldjs:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除科级非领导职务晋升工作数据")
    public ReturnBean delete(@RequestParam String uuids) {
        if (uuids.contains(SysConstant.COMMA)) {
            flowHrKjfldjsService.deleteByIds(uuids);
        } else {
            flowHrKjfldjsService.delete(uuids);
        }
        return ReturnBean.ok();
    }


    /**
     * 启动流程
     *
     * @param ids
     * @return
     */
    @PostMapping("submit")
    public ReturnBean submit(@RequestParam("ids") String ids) {
        return flowHrKjfldjsService.submit(ids);
    }

    @Override
    public BaseServiceImplByAct getBaseServiceImplByAct() {
        return flowHrKjfldjsService;
    }
}
