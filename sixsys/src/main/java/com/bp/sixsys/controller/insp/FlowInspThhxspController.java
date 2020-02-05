package com.bp.sixsys.controller.insp;


import com.bp.common.base.BaseActController;
import com.bp.common.base.BaseController;

import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.anno.LogAnnotation;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.po.insp.FlowInspThhxsp;
import com.bp.sixsys.service.insp.FlowInspThhxspService;
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
* @Description: 谈话函询审批控制层
* @date 2019年12月03日
*/
@RestController
@RequestMapping(value="/flowInspThhxsp")
@Api(value = "谈话函询审批")
public class FlowInspThhxspController extends BaseActController {

    @Resource
    private FlowInspThhxspService flowInspThhxspService;

    /**
     * 获取谈话函询审批管理页list
     *
     * @return 返回主页面list
     */
    @PreAuthorize("hasAuthority('sixsys:flowInspThhxsp:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取谈话函询审批list")
    public ReturnBean<List<FlowInspThhxsp>> list(@RequestParam Map para) {
        para.put("currentUserId", UserUtils.getCurrentUser().getId());
        return flowInspThhxspService.selectList(para);
    }


    /**
     * 添加谈话函询审批
     *
     * @param flowInspThhxsp 谈话函询审批
     * @return 返回操作成功
     */
    @LogAnnotation(module ="添加谈话函询审批")
    @PreAuthorize("hasAuthority('sixsys:flowInspThhxsp:insert')")
    @PostMapping
    @ApiOperation(value = "添加谈话函询审批")
    public ReturnBean insert(@RequestBody FlowInspThhxsp flowInspThhxsp) {
        flowInspThhxspService.insert(flowInspThhxsp);
        return ReturnBean.ok();
    }

 
    /**
     * 修改谈话函询审批
     *
     * @param flowInspThhxsp 谈话函询审批
     * @return 返回操作成功
     */
    @LogAnnotation(module ="修改谈话函询审批")
    @PreAuthorize("hasAuthority('sixsys:flowInspThhxsp:update')")
    @PutMapping
    @ApiOperation(value = "修改谈话函询审批")
    public ReturnBean update(@RequestBody FlowInspThhxsp flowInspThhxsp) {
        flowInspThhxspService.update(flowInspThhxsp);
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取谈话函询审批数据
     *
     * @param uuid 谈话函询审批 uuid
     * @return 返回谈话函询审批数据
     */
    @PreAuthorize("hasAnyAuthority('sixsys:flowInspThhxsp:see','sixsys:flowInspThhxsp:update')")
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取谈话函询审批数据")
    public ReturnBean<FlowInspThhxsp> getByUuid(@PathVariable String uuid) {
        FlowInspThhxsp flowInspThhxsp = flowInspThhxspService.selectEntityById(uuid);
        return ReturnBean.ok(flowInspThhxsp);
    }

    /**
     * 删除谈话函询审批
     *
     * @param uuids 谈话函询审批 uuids
     * @return 返回操作成功
     */
    @LogAnnotation(module ="删除谈话函询审批")
    @PreAuthorize("hasAuthority('sixsys:flowInspThhxsp:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除谈话函询审批数据")
    public ReturnBean delete(@RequestParam String uuids) {
        if(uuids.contains(SysConstant.COMMA)){
            flowInspThhxspService.deleteByIds(uuids);
        }else{
            flowInspThhxspService.delete(uuids);
        }
        return ReturnBean.ok();
    }

    @PostMapping("submit")
    public ReturnBean submit(@RequestParam("ids") String ids) {
        System.out.println("提交审核");
        return flowInspThhxspService.submit(ids);
    }


    @Override
    public BaseServiceImplByAct getBaseServiceImplByAct() {
        return flowInspThhxspService;
    }
}
