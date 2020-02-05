package com.bp.sixsys.controller.insp;


import com.bp.common.base.BaseActController;
import com.bp.common.base.BaseController;

import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.sixsys.po.insp.FlowInspJcqktb;
import com.bp.sixsys.service.insp.FlowInspJcqktbService;
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
* @author pengwanli
* @version 1.0
* @Description: 规范权力运行检查情况通报控制层
* @date 2019年12月02日
*/
@RestController
@RequestMapping(value="/flowInspJcqktb")
@Api(value = "规范权力运行检查情况通报")
public class FlowInspJcqktbController extends BaseActController {

    @Resource
    private FlowInspJcqktbService flowInspJcqktbService;

    @Override
    public BaseServiceImplByAct getBaseServiceImplByAct() {
        return flowInspJcqktbService;
    }

    /**
     * 获取规范权力运行检查情况通报管理页list
     *
     * @return 返回主页面list
     */
    @PreAuthorize("hasAuthority('sixsys:flowInspJcqktb:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取规范权力运行检查情况通报list")
    public ReturnBean<List<FlowInspJcqktb>> list(@RequestParam Map para) {
        return flowInspJcqktbService.selectList(para);
    }


    /**
     * 添加规范权力运行检查情况通报
     *
     * @param flowInspJcqktb 规范权力运行检查情况通报
     * @return 返回操作成功
     */
    @LogAnnotation(module ="添加规范权力运行检查情况通报")
    @PreAuthorize("hasAuthority('sixsys:flowInspJcqktb:insert')")
    @PostMapping
    @ApiOperation(value = "添加规范权力运行检查情况通报")
    public ReturnBean insert(@RequestBody FlowInspJcqktb flowInspJcqktb) {
        flowInspJcqktbService.insert(flowInspJcqktb);
        return ReturnBean.ok();
    }

 
    /**
     * 修改规范权力运行检查情况通报
     *
     * @param flowInspJcqktb 规范权力运行检查情况通报
     * @return 返回操作成功
     */
    @LogAnnotation(module ="修改规范权力运行检查情况通报")
    @PreAuthorize("hasAuthority('sixsys:flowInspJcqktb:update')")
    @PutMapping
    @ApiOperation(value = "修改规范权力运行检查情况通报")
    public ReturnBean update(@RequestBody FlowInspJcqktb flowInspJcqktb) {
        flowInspJcqktbService.update(flowInspJcqktb);
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取规范权力运行检查情况通报数据
     *
     * @param uuid 规范权力运行检查情况通报 uuid
     * @return 返回规范权力运行检查情况通报数据
     */
    @PreAuthorize("hasAnyAuthority('sixsys:flowInspJcqktb:see','sixsys:flowInspJcqktb:update')")
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取规范权力运行检查情况通报数据")
    public ReturnBean<FlowInspJcqktb> getByUuid(@PathVariable String uuid) {
        FlowInspJcqktb flowInspJcqktb = flowInspJcqktbService.selectEntityById(uuid);
        return ReturnBean.ok(flowInspJcqktb);
    }

    /**
     * 删除规范权力运行检查情况通报
     *
     * @param uuids 规范权力运行检查情况通报 uuids
     * @return 返回操作成功
     */
    @LogAnnotation(module ="删除规范权力运行检查情况通报")
    @PreAuthorize("hasAuthority('sixsys:flowInspJcqktb:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除规范权力运行检查情况通报数据")
    public ReturnBean delete(@RequestParam String uuids) {
        if(uuids.contains(SysConstant.COMMA)){
            flowInspJcqktbService.deleteByIds(uuids);
        }else{
            flowInspJcqktbService.delete(uuids);
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
        return flowInspJcqktbService.submit(ids);
    }
}
