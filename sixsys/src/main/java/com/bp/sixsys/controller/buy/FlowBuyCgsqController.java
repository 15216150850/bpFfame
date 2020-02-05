package com.bp.sixsys.controller.buy;


import com.bp.common.base.BaseActController;
import com.bp.common.base.BaseController;

import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.anno.LogAnnotation;
import com.bp.sixsys.po.buy.FlowBuyCgsq;
import com.bp.sixsys.service.buy.FlowBuyCgsqService;
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
* @Description: 采购申请控制层
* @date 2019年11月25日
*/
@RestController
@RequestMapping(value="/flowBuyCgsq")
@Api(value = "采购申请")
public class FlowBuyCgsqController extends BaseActController {

    @Resource
    private FlowBuyCgsqService flowBuyCgsqService;

    @Override
    public BaseServiceImplByAct getBaseServiceImplByAct() {
        return flowBuyCgsqService;
    }

    /**
     * 获取采购申请管理页list
     *
     * @return 返回主页面list
     */
    @PreAuthorize("hasAuthority('sixsys:flowBuyCgsq:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取采购申请list")
    public ReturnBean<List<FlowBuyCgsq>> list(@RequestParam Map para) {
        return flowBuyCgsqService.selectList(para);
    }


    /**
     * 添加采购申请
     *
     * @param flowBuyCgsq 采购申请
     * @return 返回操作成功
     */
    @LogAnnotation(module ="添加采购申请")
    @PreAuthorize("hasAuthority('sixsys:flowBuyCgsq:insert')")
    @PostMapping
    @ApiOperation(value = "添加采购申请")
    public ReturnBean insert(@RequestBody FlowBuyCgsq flowBuyCgsq) {
        flowBuyCgsqService.insert(flowBuyCgsq);
        return ReturnBean.ok();
    }

 
    /**
     * 修改采购申请
     *
     * @param flowBuyCgsq 采购申请
     * @return 返回操作成功
     */
    @LogAnnotation(module ="修改采购申请")
    @PreAuthorize("hasAuthority('sixsys:flowBuyCgsq:update')")
    @PutMapping
    @ApiOperation(value = "修改采购申请")
    public ReturnBean update(@RequestBody FlowBuyCgsq flowBuyCgsq) {
        flowBuyCgsqService.update(flowBuyCgsq);
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取采购申请数据
     *
     * @param uuid 采购申请 uuid
     * @return 返回采购申请数据
     */
    @PreAuthorize("hasAnyAuthority('sixsys:flowBuyCgsq:see','sixsys:flowBuyCgsq:update')")
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取采购申请数据")
    public ReturnBean<FlowBuyCgsq> getByUuid(@PathVariable String uuid) {
        FlowBuyCgsq flowBuyCgsq = flowBuyCgsqService.selectEntityById(uuid);
        return ReturnBean.ok(flowBuyCgsq);
    }

    /**
     * 删除采购申请
     *
     * @param uuids 采购申请 uuids
     * @return 返回操作成功
     */
    @LogAnnotation(module ="删除采购申请")
    @PreAuthorize("hasAuthority('sixsys:flowBuyCgsq:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除采购申请数据")
    public ReturnBean delete(@RequestParam String uuids) {
        if(uuids.contains(SysConstant.COMMA)){
            flowBuyCgsqService.deleteByIds(uuids);
        }else{
            flowBuyCgsqService.delete(uuids);
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
    public ReturnBean submit(@RequestParam String ids) {
        return flowBuyCgsqService.submit(ids);
    }

    /**
     * 带参数查询物资采购表导出列表
     */
    @GetMapping("/export")
    public void export(@RequestParam Map para) {
        flowBuyCgsqService.export(para);
    }

    /**
     * 汇总表列表
     */
    @GetMapping("/total")
    public ReturnBean<List<FlowBuyCgsq>> totalList(@RequestParam Map para){
        return flowBuyCgsqService.totalList(para);
    }
}
