package com.bp.sixsys.controller.insp;


import com.bp.common.base.BaseActController;
import com.bp.common.base.BaseController;

import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.anno.LogAnnotation;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.po.insp.FlowInspJcjytzs;
import com.bp.sixsys.service.insp.FlowInspJcjytzsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.bp.common.constants.SysConstant;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;


/**
* @author guoxiangxu
* @version 1.0
* @Description: 监查建议通知书控制层
* @date 2019年12月02日
*/
@RestController
@RequestMapping(value="/flowInspJcjytzs")
@Api(value = "监查建议通知书")
public class FlowInspJcjytzsController extends BaseActController {

    @Resource
    private FlowInspJcjytzsService flowInspJcjytzsService;

    /**
     * 获取监查建议通知书管理页list
     *
     * @return 返回主页面list
     */
    @PreAuthorize("hasAuthority('sixsys:flowInspJcjytzs:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取监查建议通知书list")
    public ReturnBean<List<FlowInspJcjytzs>> list(@RequestParam Map para) {
        para.put("currentUserId", UserUtils.getCurrentUser().getId());
        return flowInspJcjytzsService.selectList(para);
    }


    /**
     * 添加监查建议通知书
     *
     * @param flowInspJcjytzs 监查建议通知书
     * @return 返回操作成功
     */
    @LogAnnotation(module ="添加监查建议通知书")
    @PreAuthorize("hasAuthority('sixsys:flowInspJcjytzs:insert')")
    @PostMapping
    @ApiOperation(value = "添加监查建议通知书")
    public ReturnBean insert(@RequestBody FlowInspJcjytzs flowInspJcjytzs) {
        flowInspJcjytzsService.insert(flowInspJcjytzs);
        return ReturnBean.ok();
    }

 
    /**
     * 修改监查建议通知书
     *
     * @param flowInspJcjytzs 监查建议通知书
     * @return 返回操作成功
     */
    @LogAnnotation(module ="修改监查建议通知书")
    @PreAuthorize("hasAuthority('sixsys:flowInspJcjytzs:update')")
    @PutMapping
    @ApiOperation(value = "修改监查建议通知书")
    public ReturnBean update(@RequestBody FlowInspJcjytzs flowInspJcjytzs) {
        flowInspJcjytzsService.update(flowInspJcjytzs);
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取监查建议通知书数据
     *
     * @param uuid 监查建议通知书 uuid
     * @return 返回监查建议通知书数据
     */
    @PreAuthorize("hasAnyAuthority('sixsys:flowInspJcjytzs:see','sixsys:flowInspJcjytzs:update')")
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取监查建议通知书数据")
    public ReturnBean<FlowInspJcjytzs> getByUuid(@PathVariable String uuid) {
        FlowInspJcjytzs flowInspJcjytzs = flowInspJcjytzsService.selectEntityById(uuid);
        return ReturnBean.ok(flowInspJcjytzs);
    }

    /**
     * 删除监查建议通知书
     *
     * @param uuids 监查建议通知书 uuids
     * @return 返回操作成功
     */
    @LogAnnotation(module ="删除监查建议通知书")
    @PreAuthorize("hasAuthority('sixsys:flowInspJcjytzs:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除监查建议通知书数据")
    public ReturnBean delete(@RequestParam String uuids) {
        if(uuids.contains(SysConstant.COMMA)){
            flowInspJcjytzsService.deleteByIds(uuids);
        }else{
            flowInspJcjytzsService.delete(uuids);
        }
        return ReturnBean.ok();
    }

    @PostMapping("submit")
    public ReturnBean submit(@RequestParam("ids") String ids) {
        System.out.println("提交审核");
        return flowInspJcjytzsService.submit(ids);
    }


    @Override
    public BaseServiceImplByAct getBaseServiceImplByAct() {
        return flowInspJcjytzsService;
    }
}
