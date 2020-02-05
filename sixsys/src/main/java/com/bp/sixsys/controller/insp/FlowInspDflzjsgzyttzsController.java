package com.bp.sixsys.controller.insp;


import com.bp.common.base.BaseActController;
import com.bp.common.base.BaseController;

import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.anno.LogAnnotation;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.po.insp.FlowInspDflzjsgzyttzs;
import com.bp.sixsys.service.insp.FlowInspDflzjsgzyttzsService;
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
* @Description: 党风廉政建设工作约谈通知书控制层
* @date 2019年12月03日
*/
@RestController
@RequestMapping(value="/flowInspDflzjsgzyttzs")
@Api(value = "党风廉政建设工作约谈通知书")
public class FlowInspDflzjsgzyttzsController extends BaseActController {

    @Resource
    private FlowInspDflzjsgzyttzsService flowInspDflzjsgzyttzsService;

    /**
     * 获取党风廉政建设工作约谈通知书管理页list
     *
     * @return 返回主页面list
     */
    @PreAuthorize("hasAuthority('sixsys:flowInspDflzjsgzyttzs:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取党风廉政建设工作约谈通知书list")
    public ReturnBean<List<FlowInspDflzjsgzyttzs>> list(@RequestParam Map para) {
        para.put("currentUserId", UserUtils.getCurrentUser().getId());
        return flowInspDflzjsgzyttzsService.selectList(para);
    }


    /**
     * 添加党风廉政建设工作约谈通知书
     *
     * @param flowInspDflzjsgzyttzs 党风廉政建设工作约谈通知书
     * @return 返回操作成功
     */
    @LogAnnotation(module ="添加党风廉政建设工作约谈通知书")
    @PreAuthorize("hasAuthority('sixsys:flowInspDflzjsgzyttzs:insert')")
    @PostMapping
    @ApiOperation(value = "添加党风廉政建设工作约谈通知书")
    public ReturnBean insert(@RequestBody FlowInspDflzjsgzyttzs flowInspDflzjsgzyttzs) {
        flowInspDflzjsgzyttzsService.insert(flowInspDflzjsgzyttzs);
        return ReturnBean.ok();
    }

 
    /**
     * 修改党风廉政建设工作约谈通知书
     *
     * @param flowInspDflzjsgzyttzs 党风廉政建设工作约谈通知书
     * @return 返回操作成功
     */
    @LogAnnotation(module ="修改党风廉政建设工作约谈通知书")
    @PreAuthorize("hasAuthority('sixsys:flowInspDflzjsgzyttzs:update')")
    @PutMapping
    @ApiOperation(value = "修改党风廉政建设工作约谈通知书")
    public ReturnBean update(@RequestBody FlowInspDflzjsgzyttzs flowInspDflzjsgzyttzs) {
        flowInspDflzjsgzyttzsService.update(flowInspDflzjsgzyttzs);
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取党风廉政建设工作约谈通知书数据
     *
     * @param uuid 党风廉政建设工作约谈通知书 uuid
     * @return 返回党风廉政建设工作约谈通知书数据
     */
    @PreAuthorize("hasAnyAuthority('sixsys:flowInspDflzjsgzyttzs:see','sixsys:flowInspDflzjsgzyttzs:update')")
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取党风廉政建设工作约谈通知书数据")
    public ReturnBean<FlowInspDflzjsgzyttzs> getByUuid(@PathVariable String uuid) {
        FlowInspDflzjsgzyttzs flowInspDflzjsgzyttzs = flowInspDflzjsgzyttzsService.selectEntityById(uuid);
        return ReturnBean.ok(flowInspDflzjsgzyttzs);
    }

    /**
     * 删除党风廉政建设工作约谈通知书
     *
     * @param uuids 党风廉政建设工作约谈通知书 uuids
     * @return 返回操作成功
     */
    @LogAnnotation(module ="删除党风廉政建设工作约谈通知书")
    @PreAuthorize("hasAuthority('sixsys:flowInspDflzjsgzyttzs:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除党风廉政建设工作约谈通知书数据")
    public ReturnBean delete(@RequestParam String uuids) {
        if(uuids.contains(SysConstant.COMMA)){
            flowInspDflzjsgzyttzsService.deleteByIds(uuids);
        }else{
            flowInspDflzjsgzyttzsService.delete(uuids);
        }
        return ReturnBean.ok();
    }

    @PostMapping("submit")
    public ReturnBean submit(@RequestParam("ids") String ids) {
        System.out.println("提交审核");
        return flowInspDflzjsgzyttzsService.submit(ids);
    }


    @Override
    public BaseServiceImplByAct getBaseServiceImplByAct() {
        return flowInspDflzjsgzyttzsService;
    }
}
