package com.bp.sixsys.controller.proj;


import com.bp.common.base.BaseActController;
import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.anno.LogAnnotation;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.po.proj.FlowProjGcjssq;
import com.bp.sixsys.service.proj.FlowProjGcjssqService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.bp.common.constants.SysConstant;

import java.util.Map;
import java.util.List;
import javax.annotation.Resource;


/**
* @author zhangyu
* @version 1.0
* @Description: 工程建设申请控制层
* @date 2019年11月25日
*/
@RestController
@RequestMapping(value="/flowProjGcjssq")
@Api(value = "工程建设申请")
public class FlowProjGcjssqController extends BaseActController {

    @Resource
    private FlowProjGcjssqService flowProjGcjssqService;

    /**
     * 获取工程建设申请管理页list
     *
     * @return 返回主页面list
     */
    @PreAuthorize("hasAuthority('sixsys:flowProjGcjssq:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取工程建设申请list")
    public ReturnBean<List<FlowProjGcjssq>> list(@RequestParam Map para) {
        // 只能查看自己创建的记录
        para.put("currentUserId", UserUtils.getCurrentUser().getId());
        return flowProjGcjssqService.selectList(para);
    }


    /**
     * 添加工程建设申请
     *
     * @param flowProjGcjssq 工程建设申请
     * @return 返回操作成功
     */
    @LogAnnotation(module ="添加工程建设申请")
    @PreAuthorize("hasAuthority('sixsys:flowProjGcjssq:insert')")
    @PostMapping
    @ApiOperation(value = "添加工程建设申请")
    public ReturnBean insert(@RequestBody FlowProjGcjssq flowProjGcjssq) {
        flowProjGcjssqService.insert(flowProjGcjssq);
        return ReturnBean.ok();
    }

 
    /**
     * 修改工程建设申请
     *
     * @param flowProjGcjssq 工程建设申请
     * @return 返回操作成功
     */
    @LogAnnotation(module ="修改工程建设申请")
    @PreAuthorize("hasAuthority('sixsys:flowProjGcjssq:update')")
    @PutMapping
    @ApiOperation(value = "修改工程建设申请")
    public ReturnBean update(@RequestBody FlowProjGcjssq flowProjGcjssq) {
        flowProjGcjssqService.update(flowProjGcjssq);
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取工程建设申请数据
     *
     * @param uuid 工程建设申请 uuid
     * @return 返回工程建设申请数据
     */
    @PreAuthorize("hasAnyAuthority('sixsys:flowProjGcjssq:see','sixsys:flowProjGcjssq:update')")
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取工程建设申请数据")
    public ReturnBean<FlowProjGcjssq> getByUuid(@PathVariable String uuid) {
        FlowProjGcjssq flowProjGcjssq = flowProjGcjssqService.selectEntityById(uuid);
        return ReturnBean.ok(flowProjGcjssq);
    }

    /**
     * 删除工程建设申请
     *
     * @param uuids 工程建设申请 uuids
     * @return 返回操作成功
     */
    @LogAnnotation(module ="删除工程建设申请")
    @PreAuthorize("hasAuthority('sixsys:flowProjGcjssq:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除工程建设申请数据")
    public ReturnBean delete(@RequestParam String uuids) {
        if(uuids.contains(SysConstant.COMMA)){
            flowProjGcjssqService.deleteByIds(uuids);
        }else{
            flowProjGcjssqService.delete(uuids);
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
        return flowProjGcjssqService.submit(ids);
    }

    @Override
    public BaseServiceImplByAct getBaseServiceImplByAct() {
        return flowProjGcjssqService;
    }
}
