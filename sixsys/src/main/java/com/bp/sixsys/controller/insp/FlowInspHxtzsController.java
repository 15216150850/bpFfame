package com.bp.sixsys.controller.insp;


import com.bp.common.base.BaseActController;
import com.bp.common.base.BaseController;

import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.anno.LogAnnotation;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.po.insp.FlowInspHxtzs;
import com.bp.sixsys.service.insp.FlowInspHxtzsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.bp.common.constants.SysConstant;

import java.util.Map;
import java.util.List;
import javax.annotation.Resource;


/**
* @author guoxiangxu
* @version 1.0
* @Description: 函询通知书控制层
* @date 2019年12月03日
*/
@RestController
@RequestMapping(value="/flowInspHxtzs")
@Api(value = "函询通知书")
public class FlowInspHxtzsController extends BaseActController {

    @Resource
    private FlowInspHxtzsService flowInspHxtzsService;

    /**
     * 获取函询通知书管理页list
     *
     * @return 返回主页面list
     */
    @PreAuthorize("hasAuthority('sixsys:flowInspHxtzs:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取函询通知书list")
    public ReturnBean<List<FlowInspHxtzs>> list(@RequestParam Map para) {
        para.put("currentUserId", UserUtils.getCurrentUser().getId());
        return flowInspHxtzsService.selectList(para);
    }


    /**
     * 添加函询通知书
     *
     * @param flowInspHxtzs 函询通知书
     * @return 返回操作成功
     */
    @LogAnnotation(module ="添加函询通知书")
    @PreAuthorize("hasAuthority('sixsys:flowInspHxtzs:insert')")
    @PostMapping
    @ApiOperation(value = "添加函询通知书")
    public ReturnBean insert(@RequestBody FlowInspHxtzs flowInspHxtzs) {
        flowInspHxtzsService.insert(flowInspHxtzs);
        return ReturnBean.ok();
    }

 
    /**
     * 修改函询通知书
     *
     * @param flowInspHxtzs 函询通知书
     * @return 返回操作成功
     */
    @LogAnnotation(module ="修改函询通知书")
    @PreAuthorize("hasAuthority('sixsys:flowInspHxtzs:update')")
    @PutMapping
    @ApiOperation(value = "修改函询通知书")
    public ReturnBean update(@RequestBody FlowInspHxtzs flowInspHxtzs) {
        flowInspHxtzsService.update(flowInspHxtzs);
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取函询通知书数据
     *
     * @param uuid 函询通知书 uuid
     * @return 返回函询通知书数据
     */
    @PreAuthorize("hasAnyAuthority('sixsys:flowInspHxtzs:see','sixsys:flowInspHxtzs:update')")
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取函询通知书数据")
    public ReturnBean<FlowInspHxtzs> getByUuid(@PathVariable String uuid) {
        FlowInspHxtzs flowInspHxtzs = flowInspHxtzsService.selectEntityById(uuid);
        return ReturnBean.ok(flowInspHxtzs);
    }

    /**
     * 删除函询通知书
     *
     * @param uuids 函询通知书 uuids
     * @return 返回操作成功
     */
    @LogAnnotation(module ="删除函询通知书")
    @PreAuthorize("hasAuthority('sixsys:flowInspHxtzs:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除函询通知书数据")
    public ReturnBean delete(@RequestParam String uuids) {
        if(uuids.contains(SysConstant.COMMA)){
            flowInspHxtzsService.deleteByIds(uuids);
        }else{
            flowInspHxtzsService.delete(uuids);
        }
        return ReturnBean.ok();
    }

    @PostMapping("submit")
    public ReturnBean submit(@RequestParam("ids") String ids) {
        System.out.println("提交审核");
        return flowInspHxtzsService.submit(ids);
    }


    @Override
    public BaseServiceImplByAct getBaseServiceImplByAct() {
        return flowInspHxtzsService;
    }
}
