package com.bp.sixsys.controller.insp;


import com.bp.common.anno.LogAnnotation;
import com.bp.common.base.BaseActController;
import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.constants.SysConstant;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.po.insp.FlowInspJdjb;
import com.bp.sixsys.service.insp.FlowInspJdjbService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * @author guoxiangxu
 * @version 1.0
 * @Description: 监督举报控制层
 * @date 2019年10月12日
 */
@RestController
@RequestMapping(value = "/flowInspJdjb")
@Api(value = "监督举报")
public class FlowInspJdjbController extends BaseActController {

    @Resource
    private FlowInspJdjbService flowInspJdjbService;


    @Override
    public BaseServiceImplByAct getBaseServiceImplByAct() {
        return flowInspJdjbService;
    }


    /**
     * 获取监督举报管理页list
     *
     * @return
     */
    @PreAuthorize("hasAuthority('sixsys:flowInspJdjb:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取监督举报list")
    public ReturnBean<List<FlowInspJdjb>> list(@RequestParam Map para) {
        para.put("currentUserId", UserUtils.getCurrentUser().getId());
        return flowInspJdjbService.selectList(para);
    }


    /**
     * 添加监督举报
     *
     * @param flowInspJdjb 监督举报
     * @return
     */
    @LogAnnotation(module = "添加监督举报")
    @PreAuthorize("hasAuthority('sixsys:flowInspJdjb:insert')")
    @PostMapping
    @ApiOperation(value = "添加监督举报")
    public ReturnBean insert(@RequestBody FlowInspJdjb flowInspJdjb) {
        flowInspJdjbService.insert(flowInspJdjb);
        return ReturnBean.ok();
    }


    /**
     * 修改监督举报
     *
     * @param flowInspJdjb 监督举报
     * @return
     */
    @LogAnnotation(module = "修改监督举报")
    @PreAuthorize("hasAuthority('sixsys:flowInspJdjb:update')")
    @PutMapping
    @ApiOperation(value = "修改监督举报")
    public ReturnBean update(@RequestBody FlowInspJdjb flowInspJdjb) {
        flowInspJdjbService.update(flowInspJdjb);
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取监督举报数据
     *
     * @param uuid 监督举报 uuid
     * @return
     */
    @PreAuthorize("hasAnyAuthority('sixsys:flowInspJdjb:see','sixsys:flowInspJdjb:update')")
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取监督举报数据")
    public ReturnBean<FlowInspJdjb> getByUuid(@PathVariable String uuid) {
        FlowInspJdjb flowInspJdjb = flowInspJdjbService.selectEntityById(uuid);
        return ReturnBean.ok(flowInspJdjb);
    }

    /**
     * 删除监督举报
     *
     * @param uuids 监督举报 uuids
     * @return
     */
    @LogAnnotation(module = "删除监督举报")
    @PreAuthorize("hasAuthority('sixsys:flowInspJdjb:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除监督举报数据")
    public ReturnBean delete(@RequestParam String uuids) {
        if (uuids.contains(SysConstant.COMMA)) {
            flowInspJdjbService.deleteByIds(uuids);
        } else {
            flowInspJdjbService.delete(uuids);
        }
        return ReturnBean.ok();
    }

    @PostMapping("submit")
    public ReturnBean submit(@RequestParam("ids") String ids) {
        System.out.println("提交审核");
        return flowInspJdjbService.submit(ids);
    }



}
