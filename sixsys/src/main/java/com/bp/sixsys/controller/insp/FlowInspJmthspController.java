package com.bp.sixsys.controller.insp;


import com.bp.common.base.BaseActController;
import com.bp.common.base.BaseController;

import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.anno.LogAnnotation;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.po.insp.FlowInspJmthsp;
import com.bp.sixsys.service.insp.FlowInspJmthspService;
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
* @Description: 诫勉谈话审批表控制层
* @date 2019年12月03日
*/
@RestController
@RequestMapping(value="/flowInspJmthsp")
@Api(value = "诫勉谈话审批表")
public class FlowInspJmthspController extends BaseActController {

    @Resource
    private FlowInspJmthspService flowInspJmthspService;

    /**
     * 获取诫勉谈话审批表管理页list
     *
     * @return 返回主页面list
     */
    @PreAuthorize("hasAuthority('sixsys:flowInspJmthsp:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取诫勉谈话审批表list")
    public ReturnBean<List<FlowInspJmthsp>> list(@RequestParam Map para) {
        para.put("currentUserId", UserUtils.getCurrentUser().getId());
        return flowInspJmthspService.selectList(para);
    }


    /**
     * 添加诫勉谈话审批表
     *
     * @param flowInspJmthsp 诫勉谈话审批表
     * @return 返回操作成功
     */
    @LogAnnotation(module ="添加诫勉谈话审批表")
    @PreAuthorize("hasAuthority('sixsys:flowInspJmthsp:insert')")
    @PostMapping
    @ApiOperation(value = "添加诫勉谈话审批表")
    public ReturnBean insert(@RequestBody FlowInspJmthsp flowInspJmthsp) {
        flowInspJmthspService.insert(flowInspJmthsp);
        return ReturnBean.ok();
    }

 
    /**
     * 修改诫勉谈话审批表
     *
     * @param flowInspJmthsp 诫勉谈话审批表
     * @return 返回操作成功
     */
    @LogAnnotation(module ="修改诫勉谈话审批表")
    @PreAuthorize("hasAuthority('sixsys:flowInspJmthsp:update')")
    @PutMapping
    @ApiOperation(value = "修改诫勉谈话审批表")
    public ReturnBean update(@RequestBody FlowInspJmthsp flowInspJmthsp) {
        flowInspJmthspService.update(flowInspJmthsp);
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取诫勉谈话审批表数据
     *
     * @param uuid 诫勉谈话审批表 uuid
     * @return 返回诫勉谈话审批表数据
     */
    @PreAuthorize("hasAnyAuthority('sixsys:flowInspJmthsp:see','sixsys:flowInspJmthsp:update')")
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取诫勉谈话审批表数据")
    public ReturnBean<FlowInspJmthsp> getByUuid(@PathVariable String uuid) {
        FlowInspJmthsp flowInspJmthsp = flowInspJmthspService.selectEntityById(uuid);
        return ReturnBean.ok(flowInspJmthsp);
    }

    /**
     * 删除诫勉谈话审批表
     *
     * @param uuids 诫勉谈话审批表 uuids
     * @return 返回操作成功
     */
    @LogAnnotation(module ="删除诫勉谈话审批表")
    @PreAuthorize("hasAuthority('sixsys:flowInspJmthsp:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除诫勉谈话审批表数据")
    public ReturnBean delete(@RequestParam String uuids) {
        if(uuids.contains(SysConstant.COMMA)){
            flowInspJmthspService.deleteByIds(uuids);
        }else{
            flowInspJmthspService.delete(uuids);
        }
        return ReturnBean.ok();
    }

    @PostMapping("submit")
    public ReturnBean submit(@RequestParam("ids") String ids) {
        System.out.println("提交审核");
        return flowInspJmthspService.submit(ids);
    }


    @Override
    public BaseServiceImplByAct getBaseServiceImplByAct() {
        return flowInspJmthspService;
    }
}
