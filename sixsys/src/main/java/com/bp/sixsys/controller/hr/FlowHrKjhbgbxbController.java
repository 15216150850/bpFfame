package com.bp.sixsys.controller.hr;


import com.bp.common.base.BaseActController;
import com.bp.common.base.BaseController;

import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.anno.LogAnnotation;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.po.hr.FlowHrKjhbgbxb;
import com.bp.sixsys.service.hr.FlowHrKjhbgbxbService;
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
* @Description: 科级后备干部选拔工作控制层
* @date 2019年11月22日
*/
@RestController
@RequestMapping(value="/flowHrKjhbgbxb")
@Api(value = "科级后备干部选拔工作")
public class FlowHrKjhbgbxbController extends BaseActController {

    @Resource
    private FlowHrKjhbgbxbService flowHrKjhbgbxbService;

    /**
     * 获取科级后备干部选拔工作管理页list
     *
     * @return 返回主页面list
     */
    @PreAuthorize("hasAuthority('sixsys:flowHrKjhbgbxb:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取科级后备干部选拔工作list")
    public ReturnBean<List<FlowHrKjhbgbxb>> list(@RequestParam Map para) {
        // 只能查看自己创建的记录
        para.put("currentUserId", UserUtils.getCurrentUser().getId());
        return flowHrKjhbgbxbService.selectList(para);
    }


    /**
     * 添加科级后备干部选拔工作
     *
     * @param flowHrKjhbgbxb 科级后备干部选拔工作
     * @return 返回操作成功
     */
    @LogAnnotation(module ="添加科级后备干部选拔工作")
    @PreAuthorize("hasAuthority('sixsys:flowHrKjhbgbxb:insert')")
    @PostMapping
    @ApiOperation(value = "添加科级后备干部选拔工作")
    public ReturnBean insert(@RequestBody FlowHrKjhbgbxb flowHrKjhbgbxb) {
        flowHrKjhbgbxbService.insert(flowHrKjhbgbxb);
        return ReturnBean.ok();
    }

 
    /**
     * 修改科级后备干部选拔工作
     *
     * @param flowHrKjhbgbxb 科级后备干部选拔工作
     * @return 返回操作成功
     */
    @LogAnnotation(module ="修改科级后备干部选拔工作")
    @PreAuthorize("hasAuthority('sixsys:flowHrKjhbgbxb:update')")
    @PutMapping
    @ApiOperation(value = "修改科级后备干部选拔工作")
    public ReturnBean update(@RequestBody FlowHrKjhbgbxb flowHrKjhbgbxb) {
        flowHrKjhbgbxbService.update(flowHrKjhbgbxb);
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取科级后备干部选拔工作数据
     *
     * @param uuid 科级后备干部选拔工作 uuid
     * @return 返回科级后备干部选拔工作数据
     */
    @PreAuthorize("hasAnyAuthority('sixsys:flowHrKjhbgbxb:see','sixsys:flowHrKjhbgbxb:update')")
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取科级后备干部选拔工作数据")
    public ReturnBean<FlowHrKjhbgbxb> getByUuid(@PathVariable String uuid) {
        FlowHrKjhbgbxb flowHrKjhbgbxb = flowHrKjhbgbxbService.selectEntityById(uuid);
        return ReturnBean.ok(flowHrKjhbgbxb);
    }

    /**
     * 删除科级后备干部选拔工作
     *
     * @param uuids 科级后备干部选拔工作 uuids
     * @return 返回操作成功
     */
    @LogAnnotation(module ="删除科级后备干部选拔工作")
    @PreAuthorize("hasAuthority('sixsys:flowHrKjhbgbxb:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除科级后备干部选拔工作数据")
    public ReturnBean delete(@RequestParam String uuids) {
        if(uuids.contains(SysConstant.COMMA)){
            flowHrKjhbgbxbService.deleteByIds(uuids);
        }else{
            flowHrKjhbgbxbService.delete(uuids);
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
        return flowHrKjhbgbxbService.submit(ids);
    }

    @Override
    public BaseServiceImplByAct getBaseServiceImplByAct() {
        return flowHrKjhbgbxbService;
    }

}
