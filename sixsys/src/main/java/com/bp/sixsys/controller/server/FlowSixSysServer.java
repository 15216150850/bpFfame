package com.bp.sixsys.controller.server;


import com.bp.common.base.BaseController;
import com.bp.common.bean.ReturnBean;
import com.bp.common.entity.FlowSixSys;
import com.bp.sixsys.service.FlowSixSysService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
* @author zhongxinkai
* @version 1.0
* @Description: 六大体系流程汇总表控制层
* @date 2019年11月20日
*/
@RestController
public class FlowSixSysServer extends BaseController {

    @Resource
    private FlowSixSysService flowSixSysService;

    @PostMapping("api/flowSixSys/insertFlowSixSys")
    public ReturnBean insertFlowSixSys(@RequestBody FlowSixSys flowSixSys){
        flowSixSysService.insert(flowSixSys);
        return ReturnBean.ok();
    }

    @PostMapping("api/flowSixSys/updateFlowSixSysByPid")
    public ReturnBean updateFlowSixSysByPid(@RequestBody FlowSixSys flowSixSys){
        flowSixSysService.updateByPid(flowSixSys);
        return ReturnBean.ok();
    }

    @GetMapping("api/flowSixSys/deleteFlowSixSysByBusiUuid")
    public ReturnBean deleteFlowSixSysByBusiUuid(@RequestParam("uuid") String uuid){
        flowSixSysService.deleteFlowSixSysByBusiUuid(uuid);
        return ReturnBean.ok();
    }
    @PostMapping("api/flowSixSys/updateFlowSixSysByBusiUuid")
    public ReturnBean updateFlowSixSysByBusiUuid(@RequestBody FlowSixSys flowSixSys){
        flowSixSysService.updateFlowSixSysByBusiUuid(flowSixSys);
        return ReturnBean.ok();
    }

}
