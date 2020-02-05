package com.bp.sixsys.controller.insp;


import com.bp.common.base.BaseActController;
import com.bp.common.base.BaseController;

import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.anno.LogAnnotation;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.po.insp.FlowInspBmtbppsp;
import com.bp.sixsys.service.insp.FlowInspBmtbppspService;
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
* @Description: 通报批评审批（部门）控制层
* @date 2019年12月03日
*/
@RestController
@RequestMapping(value="/flowInspBmtbppsp")
@Api(value = "通报批评审批（部门）")
public class FlowInspBmtbppspController extends BaseActController {

    @Resource
    private FlowInspBmtbppspService flowInspBmtbppspService;

    /**
     * 获取通报批评审批（部门）管理页list
     *
     * @return 返回主页面list
     */
    @PreAuthorize("hasAuthority('sixsys:flowInspBmtbppsp:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取通报批评审批（部门）list")
    public ReturnBean<List<FlowInspBmtbppsp>> list(@RequestParam Map para) {
        para.put("currentUserId", UserUtils.getCurrentUser().getId());
        return flowInspBmtbppspService.selectList(para);
    }


    /**
     * 添加通报批评审批（部门）
     *
     * @param flowInspBmtbppsp 通报批评审批（部门）
     * @return 返回操作成功
     */
    @LogAnnotation(module ="添加通报批评审批（部门）")
    @PreAuthorize("hasAuthority('sixsys:flowInspBmtbppsp:insert')")
    @PostMapping
    @ApiOperation(value = "添加通报批评审批（部门）")
    public ReturnBean insert(@RequestBody FlowInspBmtbppsp flowInspBmtbppsp) {
        flowInspBmtbppspService.insert(flowInspBmtbppsp);
        return ReturnBean.ok();
    }

 
    /**
     * 修改通报批评审批（部门）
     *
     * @param flowInspBmtbppsp 通报批评审批（部门）
     * @return 返回操作成功
     */
    @LogAnnotation(module ="修改通报批评审批（部门）")
    @PreAuthorize("hasAuthority('sixsys:flowInspBmtbppsp:update')")
    @PutMapping
    @ApiOperation(value = "修改通报批评审批（部门）")
    public ReturnBean update(@RequestBody FlowInspBmtbppsp flowInspBmtbppsp) {
        flowInspBmtbppspService.update(flowInspBmtbppsp);
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取通报批评审批（部门）数据
     *
     * @param uuid 通报批评审批（部门） uuid
     * @return 返回通报批评审批（部门）数据
     */
    @PreAuthorize("hasAnyAuthority('sixsys:flowInspBmtbppsp:see','sixsys:flowInspBmtbppsp:update')")
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取通报批评审批（部门）数据")
    public ReturnBean<FlowInspBmtbppsp> getByUuid(@PathVariable String uuid) {
        FlowInspBmtbppsp flowInspBmtbppsp = flowInspBmtbppspService.selectEntityById(uuid);
        return ReturnBean.ok(flowInspBmtbppsp);
    }

    /**
     * 删除通报批评审批（部门）
     *
     * @param uuids 通报批评审批（部门） uuids
     * @return 返回操作成功
     */
    @LogAnnotation(module ="删除通报批评审批（部门）")
    @PreAuthorize("hasAuthority('sixsys:flowInspBmtbppsp:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除通报批评审批（部门）数据")
    public ReturnBean delete(@RequestParam String uuids) {
        if(uuids.contains(SysConstant.COMMA)){
            flowInspBmtbppspService.deleteByIds(uuids);
        }else{
            flowInspBmtbppspService.delete(uuids);
        }
        return ReturnBean.ok();
    }

    @PostMapping("submit")
    public ReturnBean submit(@RequestParam("ids") String ids) {
        System.out.println("提交审核");
        return flowInspBmtbppspService.submit(ids);
    }


    @Override
    public BaseServiceImplByAct getBaseServiceImplByAct() {
        return flowInspBmtbppspService;
    }
}
