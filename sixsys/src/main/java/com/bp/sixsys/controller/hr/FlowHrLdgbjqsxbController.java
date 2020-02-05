package com.bp.sixsys.controller.hr;


import com.bp.common.base.BaseActController;
import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.anno.LogAnnotation;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.po.hr.FlowHrLdgbjqsxb;
import com.bp.sixsys.service.hr.FlowHrLdgbjqsxbService;
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
* @Description: 领导干部近亲属选拔任用工作控制层
* @date 2019年11月21日
*/
@RestController
@RequestMapping(value="/flowHrLdgbjqsxb")
@Api(value = "领导干部近亲属选拔任用工作")
public class FlowHrLdgbjqsxbController extends BaseActController {

    @Resource
    private FlowHrLdgbjqsxbService flowHrLdgbjqsxbService;

    /**
     * 获取领导干部近亲属选拔任用工作管理页list
     *
     * @return 返回主页面list
     */
    @PreAuthorize("hasAuthority('sixsys:flowHrLdgbjqsxb:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取领导干部近亲属选拔任用工作list")
    public ReturnBean<List<FlowHrLdgbjqsxb>> list(@RequestParam Map para) {
        // 只能查看自己创建的记录
        para.put("currentUserId", UserUtils.getCurrentUser().getId());
        return flowHrLdgbjqsxbService.selectList(para);
    }


    /**
     * 添加领导干部近亲属选拔任用工作
     *
     * @param flowHrLdgbjqsxb 领导干部近亲属选拔任用工作
     * @return 返回操作成功
     */
    @LogAnnotation(module ="添加领导干部近亲属选拔任用工作")
    @PreAuthorize("hasAuthority('sixsys:flowHrLdgbjqsxb:insert')")
    @PostMapping
    @ApiOperation(value = "添加领导干部近亲属选拔任用工作")
    public ReturnBean insert(@RequestBody FlowHrLdgbjqsxb flowHrLdgbjqsxb) {
        flowHrLdgbjqsxbService.insert(flowHrLdgbjqsxb);
        return ReturnBean.ok();
    }

 
    /**
     * 修改领导干部近亲属选拔任用工作
     *
     * @param flowHrLdgbjqsxb 领导干部近亲属选拔任用工作
     * @return 返回操作成功
     */
    @LogAnnotation(module ="修改领导干部近亲属选拔任用工作")
    @PreAuthorize("hasAuthority('sixsys:flowHrLdgbjqsxb:update')")
    @PutMapping
    @ApiOperation(value = "修改领导干部近亲属选拔任用工作")
    public ReturnBean update(@RequestBody FlowHrLdgbjqsxb flowHrLdgbjqsxb) {
        flowHrLdgbjqsxbService.update(flowHrLdgbjqsxb);
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取领导干部近亲属选拔任用工作数据
     *
     * @param uuid 领导干部近亲属选拔任用工作 uuid
     * @return 返回领导干部近亲属选拔任用工作数据
     */
    @PreAuthorize("hasAnyAuthority('sixsys:flowHrLdgbjqsxb:see','sixsys:flowHrLdgbjqsxb:update')")
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取领导干部近亲属选拔任用工作数据")
    public ReturnBean<FlowHrLdgbjqsxb> getByUuid(@PathVariable String uuid) {
        FlowHrLdgbjqsxb flowHrLdgbjqsxb = flowHrLdgbjqsxbService.selectEntityById(uuid);
        return ReturnBean.ok(flowHrLdgbjqsxb);
    }

    /**
     * 删除领导干部近亲属选拔任用工作
     *
     * @param uuids 领导干部近亲属选拔任用工作 uuids
     * @return 返回操作成功
     */
    @LogAnnotation(module ="删除领导干部近亲属选拔任用工作")
    @PreAuthorize("hasAuthority('sixsys:flowHrLdgbjqsxb:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除领导干部近亲属选拔任用工作数据")
    public ReturnBean delete(@RequestParam String uuids) {
        if(uuids.contains(SysConstant.COMMA)){
            flowHrLdgbjqsxbService.deleteByIds(uuids);
        }else{
            flowHrLdgbjqsxbService.delete(uuids);
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
        return flowHrLdgbjqsxbService.submit(ids);
    }

    @Override
    public BaseServiceImplByAct getBaseServiceImplByAct() {
        return flowHrLdgbjqsxbService;
    }

}
