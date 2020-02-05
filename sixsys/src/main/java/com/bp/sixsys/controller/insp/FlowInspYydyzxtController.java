package com.bp.sixsys.controller.insp;


import com.bp.common.anno.LogAnnotation;
import com.bp.common.base.BaseActController;
import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.constants.SysConstant;
import com.bp.sixsys.po.insp.FlowInspYydyzxt;
import com.bp.sixsys.service.insp.FlowInspYydyzxtService;
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
* @Description: 运用第一种形态通知单控制层
* @date 2019年12月02日
*/
@RestController
@RequestMapping(value="/flowInspYydyzxt")
@Api(value = "运用第一种形态通知单")
public class FlowInspYydyzxtController extends BaseActController {

    @Resource
    private FlowInspYydyzxtService flowInspYydyzxtService;

    @Override
    public BaseServiceImplByAct getBaseServiceImplByAct() {
        return flowInspYydyzxtService;
    }

    /**
     * 获取运用第一种形态通知单管理页list
     *
     * @return 返回主页面list
     */
    @PreAuthorize("hasAuthority('sixsys:flowInspYydyzxt:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取运用第一种形态通知单list")
    public ReturnBean<List<FlowInspYydyzxt>> list(@RequestParam Map para) {
        return flowInspYydyzxtService.selectList(para);
    }


    /**
     * 添加运用第一种形态通知单
     *
     * @param flowInspYydyzxt 运用第一种形态通知单
     * @return 返回操作成功
     */
    @LogAnnotation(module ="添加运用第一种形态通知单")
    @PreAuthorize("hasAuthority('sixsys:flowInspYydyzxt:insert')")
    @PostMapping
    @ApiOperation(value = "添加运用第一种形态通知单")
    public ReturnBean insert(@RequestBody FlowInspYydyzxt flowInspYydyzxt) {
        flowInspYydyzxtService.insert(flowInspYydyzxt);
        return ReturnBean.ok();
    }

 
    /**
     * 修改运用第一种形态通知单
     *
     * @param flowInspYydyzxt 运用第一种形态通知单
     * @return 返回操作成功
     */
    @LogAnnotation(module ="修改运用第一种形态通知单")
    @PreAuthorize("hasAuthority('sixsys:flowInspYydyzxt:update')")
    @PutMapping
    @ApiOperation(value = "修改运用第一种形态通知单")
    public ReturnBean update(@RequestBody FlowInspYydyzxt flowInspYydyzxt) {
        flowInspYydyzxtService.update(flowInspYydyzxt);
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取运用第一种形态通知单数据
     *
     * @param uuid 运用第一种形态通知单 uuid
     * @return 返回运用第一种形态通知单数据
     */
    @PreAuthorize("hasAnyAuthority('sixsys:flowInspYydyzxt:see','sixsys:flowInspYydyzxt:update')")
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取运用第一种形态通知单数据")
    public ReturnBean<FlowInspYydyzxt> getByUuid(@PathVariable String uuid) {
        FlowInspYydyzxt flowInspYydyzxt = flowInspYydyzxtService.selectEntityById(uuid);
        return ReturnBean.ok(flowInspYydyzxt);
    }

    /**
     * 删除运用第一种形态通知单
     *
     * @param uuids 运用第一种形态通知单 uuids
     * @return 返回操作成功
     */
    @LogAnnotation(module ="删除运用第一种形态通知单")
//    @PreAuthorize("hasAuthority('sixsys:flowInspYydyzxt:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除运用第一种形态通知单数据")
    public ReturnBean delete(@RequestParam String uuids) {
        if(uuids.contains(SysConstant.COMMA)){
            flowInspYydyzxtService.deleteByIds(uuids);
        }else{
            flowInspYydyzxtService.delete(uuids);
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
        return flowInspYydyzxtService.submit(ids);
    }
}
