package com.bp.sixsys.controller.eco;


import com.bp.common.base.BaseActController;
import com.bp.common.base.BaseController;

import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.anno.LogAnnotation;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.po.eco.FlowEcoDbdj;
import com.bp.sixsys.service.eco.FlowEcoDbdjService;
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
* @Description: 打版定价控制层
* @date 2019年11月25日
*/
@RestController
@RequestMapping(value="/flowEcoDbdj")
@Api(value = "打版定价")
public class FlowEcoDbdjController extends BaseActController {

    @Resource
    private FlowEcoDbdjService flowEcoDbdjService;

    /**
     * 获取打版定价管理页list
     *
     * @return 返回主页面list
     */
    @PreAuthorize("hasAuthority('sixsys:flowEcoDbdj:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取打版定价list")
    public ReturnBean<List<FlowEcoDbdj>> list(@RequestParam Map para) {
        // 只能查看自己创建的记录
        para.put("currentUserId", UserUtils.getCurrentUser().getId());
        return flowEcoDbdjService.selectList(para);
    }


    /**
     * 添加打版定价
     *
     * @param flowEcoDbdj 打版定价
     * @return 返回操作成功
     */
    @LogAnnotation(module ="添加打版定价")
    @PreAuthorize("hasAuthority('sixsys:flowEcoDbdj:insert')")
    @PostMapping
    @ApiOperation(value = "添加打版定价")
    public ReturnBean insert(@RequestBody FlowEcoDbdj flowEcoDbdj) {
        flowEcoDbdjService.insert(flowEcoDbdj);
        return ReturnBean.ok();
    }

 
    /**
     * 修改打版定价
     *
     * @param flowEcoDbdj 打版定价
     * @return 返回操作成功
     */
    @LogAnnotation(module ="修改打版定价")
    @PreAuthorize("hasAuthority('sixsys:flowEcoDbdj:update')")
    @PutMapping
    @ApiOperation(value = "修改打版定价")
    public ReturnBean update(@RequestBody FlowEcoDbdj flowEcoDbdj) {
        flowEcoDbdjService.update(flowEcoDbdj);
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取打版定价数据
     *
     * @param uuid 打版定价 uuid
     * @return 返回打版定价数据
     */
    @PreAuthorize("hasAnyAuthority('sixsys:flowEcoDbdj:see','sixsys:flowEcoDbdj:update')")
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取打版定价数据")
    public ReturnBean<FlowEcoDbdj> getByUuid(@PathVariable String uuid) {
        FlowEcoDbdj flowEcoDbdj = flowEcoDbdjService.selectEntityById(uuid);
        return ReturnBean.ok(flowEcoDbdj);
    }

    /**
     * 删除打版定价
     *
     * @param uuids 打版定价 uuids
     * @return 返回操作成功
     */
    @LogAnnotation(module ="删除打版定价")
    @PreAuthorize("hasAuthority('sixsys:flowEcoDbdj:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除打版定价数据")
    public ReturnBean delete(@RequestParam String uuids) {
        if(uuids.contains(SysConstant.COMMA)){
            flowEcoDbdjService.deleteByIds(uuids);
        }else{
            flowEcoDbdjService.delete(uuids);
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
        return flowEcoDbdjService.submit(ids);
    }

    @Override
    public BaseServiceImplByAct getBaseServiceImplByAct() {
        return flowEcoDbdjService;
    }
}
