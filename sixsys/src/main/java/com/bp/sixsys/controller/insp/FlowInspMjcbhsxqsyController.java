package com.bp.sixsys.controller.insp;


import com.bp.common.anno.LogAnnotation;
import com.bp.common.base.BaseActController;
import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.constants.SysConstant;
import com.bp.sixsys.po.insp.FlowInspMjcbhsxqsy;
import com.bp.sixsys.service.insp.FlowInspMjcbhsxqsyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
* @author pengwanli
* @version 1.0
* @Description: 民警操办婚丧喜庆事宜报告单控制层
* @date 2019年12月02日
*/
@RestController
@RequestMapping(value="/flowInspMjcbhsxqsy")
@Api(value = "民警操办婚丧喜庆事宜报告单")
public class FlowInspMjcbhsxqsyController extends BaseActController {

    @Resource
    private FlowInspMjcbhsxqsyService flowInspMjcbhsxqsyService;

    @Override
    public BaseServiceImplByAct getBaseServiceImplByAct() {
        return flowInspMjcbhsxqsyService;
    }

    /**
     * 获取民警操办婚丧喜庆事宜报告单管理页list
     *
     * @return 返回主页面list
     */
//    @PreAuthorize("hasAuthority('sixsys:flowInspMjcbhsxqsy:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取民警操办婚丧喜庆事宜报告单list")
    public ReturnBean<List<FlowInspMjcbhsxqsy>> list(@RequestParam Map para) {
        return flowInspMjcbhsxqsyService.selectList(para);
    }


    /**
     * 添加民警操办婚丧喜庆事宜报告单
     *
     * @param flowInspMjcbhsxqsy 民警操办婚丧喜庆事宜报告单
     * @return 返回操作成功
     */
    @LogAnnotation(module ="添加民警操办婚丧喜庆事宜报告单")
//    @PreAuthorize("hasAuthority('sixsys:flowInspMjcbhsxqsy:insert')")
    @PostMapping
    @ApiOperation(value = "添加民警操办婚丧喜庆事宜报告单")
    public ReturnBean insert(@RequestBody FlowInspMjcbhsxqsy flowInspMjcbhsxqsy) {
        flowInspMjcbhsxqsyService.insert(flowInspMjcbhsxqsy);
        return ReturnBean.ok();
    }

 
    /**
     * 修改民警操办婚丧喜庆事宜报告单
     *
     * @param flowInspMjcbhsxqsy 民警操办婚丧喜庆事宜报告单
     * @return 返回操作成功
     */
    @LogAnnotation(module ="修改民警操办婚丧喜庆事宜报告单")
//    @PreAuthorize("hasAuthority('sixsys:flowInspMjcbhsxqsy:update')")
    @PutMapping
    @ApiOperation(value = "修改民警操办婚丧喜庆事宜报告单")
    public ReturnBean update(@RequestBody FlowInspMjcbhsxqsy flowInspMjcbhsxqsy) {
        flowInspMjcbhsxqsyService.update(flowInspMjcbhsxqsy);
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取民警操办婚丧喜庆事宜报告单数据
     *
     * @param uuid 民警操办婚丧喜庆事宜报告单 uuid
     * @return 返回民警操办婚丧喜庆事宜报告单数据
     */
//    @PreAuthorize("hasAnyAuthority('sixsys:flowInspMjcbhsxqsy:see','sixsys:flowInspMjcbhsxqsy:update')")
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取民警操办婚丧喜庆事宜报告单数据")
    public ReturnBean<FlowInspMjcbhsxqsy> getByUuid(@PathVariable String uuid) {
        FlowInspMjcbhsxqsy flowInspMjcbhsxqsy = flowInspMjcbhsxqsyService.selectEntityById(uuid);
        return ReturnBean.ok(flowInspMjcbhsxqsy);
    }

    /**
     * 删除民警操办婚丧喜庆事宜报告单
     *
     * @param uuids 民警操办婚丧喜庆事宜报告单 uuids
     * @return 返回操作成功
     */
    @LogAnnotation(module ="删除民警操办婚丧喜庆事宜报告单")
//    @PreAuthorize("hasAuthority('sixsys:flowInspMjcbhsxqsy:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除民警操办婚丧喜庆事宜报告单数据")
    public ReturnBean delete(@RequestParam String uuids) {
        if(uuids.contains(SysConstant.COMMA)){
            flowInspMjcbhsxqsyService.deleteByIds(uuids);
        }else{
            flowInspMjcbhsxqsyService.delete(uuids);
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
        return flowInspMjcbhsxqsyService.submit(ids);
    }
}
