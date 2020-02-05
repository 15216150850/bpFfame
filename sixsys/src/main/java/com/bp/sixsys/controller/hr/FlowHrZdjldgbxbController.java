package com.bp.sixsys.controller.hr;


import com.bp.common.base.BaseActController;

import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.po.hr.FlowHrZdjldgbxb;
import com.bp.sixsys.service.hr.FlowHrZdjldgbxbService;
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
* @author zhangyu
* @version 1.0
* @Description: 中队级领导干部选拔任用工作控制层
* @date 2019年11月21日
*/
@RestController
@RequestMapping(value="/flowHrZdjldgbxb")
@Api(value = "中队级领导干部选拔任用工作")
public class FlowHrZdjldgbxbController extends BaseActController {

    @Resource
    private FlowHrZdjldgbxbService flowHrZdjldgbxbService;

    /**
     * 获取中队级领导干部选拔任用工作管理页list
     *
     * @return 返回主页面list
     */
    @PreAuthorize("hasAuthority('sixsys:flowHrZdjldgbxb:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取中队级领导干部选拔任用工作list")
    public ReturnBean<List<FlowHrZdjldgbxb>> list(@RequestParam Map para) {
        para.put("currentUserId", UserUtils.getCurrentUser().getId());
        return flowHrZdjldgbxbService.selectList(para);
    }


    /**
     * 添加中队级领导干部选拔任用工作
     *
     * @param flowHrZdjldgbxb 中队级领导干部选拔任用工作
     * @return 返回操作成功
     */
    @LogAnnotation(module ="添加中队级领导干部选拔任用工作")
    @PreAuthorize("hasAuthority('sixsys:flowHrZdjldgbxb:insert')")
    @PostMapping
    @ApiOperation(value = "添加中队级领导干部选拔任用工作")
    public ReturnBean insert(@RequestBody FlowHrZdjldgbxb flowHrZdjldgbxb) {
        flowHrZdjldgbxbService.insert(flowHrZdjldgbxb);
        return ReturnBean.ok();
    }

 
    /**
     * 修改中队级领导干部选拔任用工作
     *
     * @param flowHrZdjldgbxb 中队级领导干部选拔任用工作
     * @return 返回操作成功
     */
    @LogAnnotation(module ="修改中队级领导干部选拔任用工作")
    @PreAuthorize("hasAuthority('sixsys:flowHrZdjldgbxb:update')")
    @PutMapping
    @ApiOperation(value = "修改中队级领导干部选拔任用工作")
    public ReturnBean update(@RequestBody FlowHrZdjldgbxb flowHrZdjldgbxb) {
        flowHrZdjldgbxbService.update(flowHrZdjldgbxb);
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取中队级领导干部选拔任用工作数据
     *
     * @param uuid 中队级领导干部选拔任用工作 uuid
     * @return 返回中队级领导干部选拔任用工作数据
     */
    @PreAuthorize("hasAnyAuthority('sixsys:flowHrZdjldgbxb:see','sixsys:flowHrZdjldgbxb:update')")
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取中队级领导干部选拔任用工作数据")
    public ReturnBean<FlowHrZdjldgbxb> getByUuid(@PathVariable String uuid) {
        FlowHrZdjldgbxb flowHrZdjldgbxb = flowHrZdjldgbxbService.selectEntityById(uuid);
        return ReturnBean.ok(flowHrZdjldgbxb);
    }

    /**
     * 删除中队级领导干部选拔任用工作
     *
     * @param uuids 中队级领导干部选拔任用工作 uuids
     * @return 返回操作成功
     */
    @LogAnnotation(module ="删除中队级领导干部选拔任用工作")
    @PreAuthorize("hasAuthority('sixsys:flowHrZdjldgbxb:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除中队级领导干部选拔任用工作数据")
    public ReturnBean delete(@RequestParam String uuids) {
        if(uuids.contains(SysConstant.COMMA)){
            flowHrZdjldgbxbService.deleteByIds(uuids);
        }else{
            flowHrZdjldgbxbService.delete(uuids);
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
        return flowHrZdjldgbxbService.submit(ids);
    }

    @Override
    public BaseServiceImplByAct getBaseServiceImplByAct() {
        return flowHrZdjldgbxbService;
    }


}
