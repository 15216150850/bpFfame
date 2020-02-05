package com.bp.sixsys.controller.insp;


import com.bp.common.base.BaseActController;
import com.bp.common.base.BaseController;

import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.sixsys.po.insp.FlowInspJmthtz;
import com.bp.sixsys.service.insp.FlowInspJmthtzService;
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
* @Description: 诫勉谈话通知控制层
* @date 2019年12月02日
*/
@RestController
@RequestMapping(value="/flowInspJmthtz")
@Api(value = "诫勉谈话通知")
public class FlowInspJmthtzController extends BaseActController {

    @Resource
    private FlowInspJmthtzService flowInspJmthtzService;

    @Override
    public BaseServiceImplByAct getBaseServiceImplByAct() {
        return flowInspJmthtzService;
    }

    /**
     * 获取诫勉谈话通知管理页list
     *
     * @return 返回主页面list
     */
    @PreAuthorize("hasAuthority('sixsys:flowInspJmthtz:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取诫勉谈话通知list")
    public ReturnBean<List<FlowInspJmthtz>> list(@RequestParam Map para) {
        return flowInspJmthtzService.selectList(para);
    }


    /**
     * 添加诫勉谈话通知
     *
     * @param flowInspJmthtz 诫勉谈话通知
     * @return 返回操作成功
     */
    @LogAnnotation(module ="添加诫勉谈话通知")
    @PreAuthorize("hasAuthority('sixsys:flowInspJmthtz:insert')")
    @PostMapping
    @ApiOperation(value = "添加诫勉谈话通知")
    public ReturnBean insert(@RequestBody FlowInspJmthtz flowInspJmthtz) {
        flowInspJmthtzService.insert(flowInspJmthtz);
        return ReturnBean.ok();
    }

 
    /**
     * 修改诫勉谈话通知
     *
     * @param flowInspJmthtz 诫勉谈话通知
     * @return 返回操作成功
     */
    @LogAnnotation(module ="修改诫勉谈话通知")
    @PreAuthorize("hasAuthority('sixsys:flowInspJmthtz:update')")
    @PutMapping
    @ApiOperation(value = "修改诫勉谈话通知")
    public ReturnBean update(@RequestBody FlowInspJmthtz flowInspJmthtz) {
        flowInspJmthtzService.update(flowInspJmthtz);
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取诫勉谈话通知数据
     *
     * @param uuid 诫勉谈话通知 uuid
     * @return 返回诫勉谈话通知数据
     */
    @PreAuthorize("hasAnyAuthority('sixsys:flowInspJmthtz:see','sixsys:flowInspJmthtz:update')")
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取诫勉谈话通知数据")
    public ReturnBean<FlowInspJmthtz> getByUuid(@PathVariable String uuid) {
        FlowInspJmthtz flowInspJmthtz = flowInspJmthtzService.selectEntityById(uuid);
        return ReturnBean.ok(flowInspJmthtz);
    }

    /**
     * 删除诫勉谈话通知
     *
     * @param uuids 诫勉谈话通知 uuids
     * @return 返回操作成功
     */
    @LogAnnotation(module ="删除诫勉谈话通知")
    @PreAuthorize("hasAuthority('sixsys:flowInspJmthtz:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除诫勉谈话通知数据")
    public ReturnBean delete(@RequestParam String uuids) {
        if(uuids.contains(SysConstant.COMMA)){
            flowInspJmthtzService.deleteByIds(uuids);
        }else{
            flowInspJmthtzService.delete(uuids);
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
        return flowInspJmthtzService.submit(ids);
    }


}
