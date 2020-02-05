package com.bp.sixsys.controller;


import com.bp.common.anno.LogAnnotation;
import com.bp.common.base.BaseController;
import com.bp.common.bean.ReturnBean;
import com.bp.common.constants.SysConstant;
import com.bp.sixsys.po.FlowSuperviAbility;
import com.bp.sixsys.service.FlowSuperviAbilityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
* @author zhongxinkai
* @version 1.0
* @Description: 监督问责控制层
* @date 2019年11月21日
*/
@RestController
@RequestMapping(value="/flowSuperviAbility")
@Api(value = "监督问责")
public class FlowSuperviAbilityController extends BaseController {

    @Resource
    private FlowSuperviAbilityService flowSuperviAbilityService;

    /**
     * 获取监督问责管理页list
     *
     * @return 返回主页面list
     */
    @PreAuthorize("hasAuthority('sixsys:flowSuperviAbility:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取监督问责list")
    public ReturnBean<List<FlowSuperviAbility>> list(@RequestParam Map para) {
        return flowSuperviAbilityService.selectList(para);
    }


    /**
     * 添加监督问责
     *
     * @param flowSuperviAbility 监督问责
     * @return 返回操作成功
     */
    @LogAnnotation(module ="添加监督问责")
    @PreAuthorize("hasAuthority('sixsys:flowSuperviAbility:insert')")
    @PostMapping
    @ApiOperation(value = "添加监督问责")
    public ReturnBean insert(@RequestBody FlowSuperviAbility flowSuperviAbility) {
        flowSuperviAbilityService.insert(flowSuperviAbility);
        return ReturnBean.ok();
    }

 
    /**
     * 修改监督问责
     *
     * @param flowSuperviAbility 监督问责
     * @return 返回操作成功
     */
    @LogAnnotation(module ="修改监督问责")
//    @PreAuthorize("hasAuthority('sixsys:flowSuperviAbility:update')")
    @PutMapping
    @ApiOperation(value = "修改监督问责")
    public ReturnBean update(@RequestBody FlowSuperviAbility flowSuperviAbility) {
        flowSuperviAbilityService.update(flowSuperviAbility);
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取监督问责数据
     *
     * @param uuid 监督问责 uuid
     * @return 返回监督问责数据
     */
    @PreAuthorize("hasAnyAuthority('sixsys:flowSuperviAbility:see','sixsys:flowSuperviAbility:update')")
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取监督问责数据")
    public ReturnBean<FlowSuperviAbility> getByUuid(@PathVariable String uuid) {
        FlowSuperviAbility flowSuperviAbility = flowSuperviAbilityService.selectEntityById(uuid);
        return ReturnBean.ok(flowSuperviAbility);
    }

    /**
     * 删除监督问责
     *
     * @param uuids 监督问责 uuids
     * @return 返回操作成功
     */
    @LogAnnotation(module ="删除监督问责")
    @PreAuthorize("hasAuthority('sixsys:flowSuperviAbility:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除监督问责数据")
    public ReturnBean delete(@RequestParam String uuids) {
        if(uuids.contains(SysConstant.COMMA)){
            flowSuperviAbilityService.deleteByIds(uuids);
        }else{
            flowSuperviAbilityService.delete(uuids);
        }
        return ReturnBean.ok();
    }


}
