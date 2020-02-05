package com.bp.sixsys.controller.insp;


import com.bp.common.base.BaseActController;
import com.bp.common.base.BaseController;

import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.sixsys.po.insp.FlowInspJcqkzghf;
import com.bp.sixsys.service.insp.FlowInspJcqkzghfService;
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
* @Description: 对规范权力运行检查情况问题的整改回复控制层
* @date 2019年12月02日
*/
@RestController
@RequestMapping(value="/flowInspJcqkzghf")
@Api(value = "对规范权力运行检查情况问题的整改回复")
public class FlowInspJcqkzghfController extends BaseActController {

    @Resource
    private FlowInspJcqkzghfService flowInspJcqkzghfService;

    @Override
    public BaseServiceImplByAct getBaseServiceImplByAct() {
        return flowInspJcqkzghfService;
    }

    /**
     * 获取对规范权力运行检查情况问题的整改回复管理页list
     *
     * @return 返回主页面list
     */
    @PreAuthorize("hasAuthority('sixsys:flowInspJcqkzghf:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取对规范权力运行检查情况问题的整改回复list")
    public ReturnBean<List<FlowInspJcqkzghf>> list(@RequestParam Map para) {
        return flowInspJcqkzghfService.selectList(para);
    }


    /**
     * 添加对规范权力运行检查情况问题的整改回复
     *
     * @param flowInspJcqkzghf 对规范权力运行检查情况问题的整改回复
     * @return 返回操作成功
     */
    @LogAnnotation(module ="添加对规范权力运行检查情况问题的整改回复")
    @PreAuthorize("hasAuthority('sixsys:flowInspJcqkzghf:insert')")
    @PostMapping
    @ApiOperation(value = "添加对规范权力运行检查情况问题的整改回复")
    public ReturnBean insert(@RequestBody FlowInspJcqkzghf flowInspJcqkzghf) {
        flowInspJcqkzghfService.insert(flowInspJcqkzghf);
        return ReturnBean.ok();
    }

 
    /**
     * 修改对规范权力运行检查情况问题的整改回复
     *
     * @param flowInspJcqkzghf 对规范权力运行检查情况问题的整改回复
     * @return 返回操作成功
     */
    @LogAnnotation(module ="修改对规范权力运行检查情况问题的整改回复")
    @PreAuthorize("hasAuthority('sixsys:flowInspJcqkzghf:update')")
    @PutMapping
    @ApiOperation(value = "修改对规范权力运行检查情况问题的整改回复")
    public ReturnBean update(@RequestBody FlowInspJcqkzghf flowInspJcqkzghf) {
        flowInspJcqkzghfService.update(flowInspJcqkzghf);
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取对规范权力运行检查情况问题的整改回复数据
     *
     * @param uuid 对规范权力运行检查情况问题的整改回复 uuid
     * @return 返回对规范权力运行检查情况问题的整改回复数据
     */
    @PreAuthorize("hasAnyAuthority('sixsys:flowInspJcqkzghf:see','sixsys:flowInspJcqkzghf:update')")
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取对规范权力运行检查情况问题的整改回复数据")
    public ReturnBean<FlowInspJcqkzghf> getByUuid(@PathVariable String uuid) {
        FlowInspJcqkzghf flowInspJcqkzghf = flowInspJcqkzghfService.selectEntityById(uuid);
        return ReturnBean.ok(flowInspJcqkzghf);
    }

    /**
     * 删除对规范权力运行检查情况问题的整改回复
     *
     * @param uuids 对规范权力运行检查情况问题的整改回复 uuids
     * @return 返回操作成功
     */
    @LogAnnotation(module ="删除对规范权力运行检查情况问题的整改回复")
    @PreAuthorize("hasAuthority('sixsys:flowInspJcqkzghf:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除对规范权力运行检查情况问题的整改回复数据")
    public ReturnBean delete(@RequestParam String uuids) {
        if(uuids.contains(SysConstant.COMMA)){
            flowInspJcqkzghfService.deleteByIds(uuids);
        }else{
            flowInspJcqkzghfService.delete(uuids);
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
        return flowInspJcqkzghfService.submit(ids);
    }

}
