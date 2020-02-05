package com.bp.sixsys.controller.hr;


import com.bp.common.base.BaseActController;
import com.bp.common.base.BaseController;

import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.anno.LogAnnotation;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.po.hr.FlowHrKjldgbxb;
import com.bp.sixsys.service.hr.FlowHrKjldgbxbService;
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
* @Description: 科级领导干部选拔任用工作控制层
* @date 2019年11月22日
*/
@RestController
@RequestMapping(value="/flowHrKjldgbxb")
@Api(value = "科级领导干部选拔任用工作")
public class FlowHrKjldgbxbController extends BaseActController {

    @Resource
    private FlowHrKjldgbxbService flowHrKjldgbxbService;

    /**
     * 获取科级领导干部选拔任用工作管理页list
     *
     * @return 返回主页面list
     */
    @PreAuthorize("hasAuthority('sixsys:flowHrKjldgbxb:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取科级领导干部选拔任用工作list")
    public ReturnBean<List<FlowHrKjldgbxb>> list(@RequestParam Map para) {
        // 只能查看自己创建的记录
        para.put("currentUserId", UserUtils.getCurrentUser().getId());
        return flowHrKjldgbxbService.selectList(para);
    }


    /**
     * 添加科级领导干部选拔任用工作
     *
     * @param flowHrKjldgbxb 科级领导干部选拔任用工作
     * @return 返回操作成功
     */
    @LogAnnotation(module ="添加科级领导干部选拔任用工作")
    @PreAuthorize("hasAuthority('sixsys:flowHrKjldgbxb:insert')")
    @PostMapping
    @ApiOperation(value = "添加科级领导干部选拔任用工作")
    public ReturnBean insert(@RequestBody FlowHrKjldgbxb flowHrKjldgbxb) {
        flowHrKjldgbxbService.insert(flowHrKjldgbxb);
        return ReturnBean.ok();
    }

 
    /**
     * 修改科级领导干部选拔任用工作
     *
     * @param flowHrKjldgbxb 科级领导干部选拔任用工作
     * @return 返回操作成功
     */
    @LogAnnotation(module ="修改科级领导干部选拔任用工作")
    @PreAuthorize("hasAuthority('sixsys:flowHrKjldgbxb:update')")
    @PutMapping
    @ApiOperation(value = "修改科级领导干部选拔任用工作")
    public ReturnBean update(@RequestBody FlowHrKjldgbxb flowHrKjldgbxb) {
        flowHrKjldgbxbService.update(flowHrKjldgbxb);
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取科级领导干部选拔任用工作数据
     *
     * @param uuid 科级领导干部选拔任用工作 uuid
     * @return 返回科级领导干部选拔任用工作数据
     */
    @PreAuthorize("hasAnyAuthority('sixsys:flowHrKjldgbxb:see','sixsys:flowHrKjldgbxb:update')")
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取科级领导干部选拔任用工作数据")
    public ReturnBean<FlowHrKjldgbxb> getByUuid(@PathVariable String uuid) {
        FlowHrKjldgbxb flowHrKjldgbxb = flowHrKjldgbxbService.selectEntityById(uuid);
        return ReturnBean.ok(flowHrKjldgbxb);
    }

    /**
     * 删除科级领导干部选拔任用工作
     *
     * @param uuids 科级领导干部选拔任用工作 uuids
     * @return 返回操作成功
     */
    @LogAnnotation(module ="删除科级领导干部选拔任用工作")
    @PreAuthorize("hasAuthority('sixsys:flowHrKjldgbxb:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除科级领导干部选拔任用工作数据")
    public ReturnBean delete(@RequestParam String uuids) {
        if(uuids.contains(SysConstant.COMMA)){
            flowHrKjldgbxbService.deleteByIds(uuids);
        }else{
            flowHrKjldgbxbService.delete(uuids);
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
        return flowHrKjldgbxbService.submit(ids);
    }

    @Override
    public BaseServiceImplByAct getBaseServiceImplByAct() {
        return flowHrKjldgbxbService;
    }

}
