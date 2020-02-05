package com.bp.sixsys.controller.insp;


import com.bp.common.base.BaseActController;
import com.bp.common.base.BaseController;

import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.anno.LogAnnotation;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.po.insp.FlowInspTbppsp;
import com.bp.sixsys.service.insp.FlowInspTbppspService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.bp.common.constants.SysConstant;

import java.util.Map;
import java.util.List;
import javax.annotation.Resource;


/**
* @author guoxiangxu
* @version 1.0
* @Description: 通报批评审批控制层
* @date 2019年12月03日
*/
@RestController
@RequestMapping(value="/flowInspTbppsp")
@Api(value = "通报批评审批")
public class FlowInspTbppspController extends BaseActController {

    @Resource
    private FlowInspTbppspService flowInspTbppspService;

    /**
     * 获取通报批评审批管理页list
     *
     * @return 返回主页面list
     */
    @PreAuthorize("hasAuthority('sixsys:flowInspTbppsp:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取通报批评审批list")
    public ReturnBean<List<FlowInspTbppsp>> list(@RequestParam Map para) {
        para.put("currentUserId", UserUtils.getCurrentUser().getId());
        return flowInspTbppspService.selectList(para);
    }


    /**
     * 添加通报批评审批
     *
     * @param flowInspTbppsp 通报批评审批
     * @return 返回操作成功
     */
    @LogAnnotation(module ="添加通报批评审批")
    @PreAuthorize("hasAuthority('sixsys:flowInspTbppsp:insert')")
    @PostMapping
    @ApiOperation(value = "添加通报批评审批")
    public ReturnBean insert(@RequestBody FlowInspTbppsp flowInspTbppsp) {
        flowInspTbppspService.insert(flowInspTbppsp);
        return ReturnBean.ok();
    }

 
    /**
     * 修改通报批评审批
     *
     * @param flowInspTbppsp 通报批评审批
     * @return 返回操作成功
     */
    @LogAnnotation(module ="修改通报批评审批")
    @PreAuthorize("hasAuthority('sixsys:flowInspTbppsp:update')")
    @PutMapping
    @ApiOperation(value = "修改通报批评审批")
    public ReturnBean update(@RequestBody FlowInspTbppsp flowInspTbppsp) {
        flowInspTbppspService.update(flowInspTbppsp);
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取通报批评审批数据
     *
     * @param uuid 通报批评审批 uuid
     * @return 返回通报批评审批数据
     */
    @PreAuthorize("hasAnyAuthority('sixsys:flowInspTbppsp:see','sixsys:flowInspTbppsp:update')")
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取通报批评审批数据")
    public ReturnBean<FlowInspTbppsp> getByUuid(@PathVariable String uuid) {
        FlowInspTbppsp flowInspTbppsp = flowInspTbppspService.selectEntityById(uuid);
        return ReturnBean.ok(flowInspTbppsp);
    }

    /**
     * 删除通报批评审批
     *
     * @param uuids 通报批评审批 uuids
     * @return 返回操作成功
     */
    @LogAnnotation(module ="删除通报批评审批")
    @PreAuthorize("hasAuthority('sixsys:flowInspTbppsp:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除通报批评审批数据")
    public ReturnBean delete(@RequestParam String uuids) {
        if(uuids.contains(SysConstant.COMMA)){
            flowInspTbppspService.deleteByIds(uuids);
        }else{
            flowInspTbppspService.delete(uuids);
        }
        return ReturnBean.ok();
    }

    @PostMapping("submit")
    public ReturnBean submit(@RequestParam("ids") String ids) {
        System.out.println("提交审核");
        return flowInspTbppspService.submit(ids);
    }


    @Override
    public BaseServiceImplByAct getBaseServiceImplByAct() {
        return flowInspTbppspService;
    }
}
