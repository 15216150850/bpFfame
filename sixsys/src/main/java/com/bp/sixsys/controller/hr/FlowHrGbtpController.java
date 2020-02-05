package com.bp.sixsys.controller.hr;


import com.bp.common.anno.LogAnnotation;
import com.bp.common.base.BaseActController;
import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.constants.SysConstant;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.po.hr.FlowHrGbtp;
import com.bp.sixsys.po.hr.FlowHrKjfldjs;
import com.bp.sixsys.service.hr.FlowHrGbtpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * @author guoxiangxu
 * @version 1.0
 * @Description: 干部调配控制层
 * @date 2019年10月17日
 */
@RestController
@RequestMapping(value = "/flowHrGbtp")
@Api(value = "干部调配")
public class FlowHrGbtpController extends BaseActController {

    @Resource
    private FlowHrGbtpService flowHrGbtpService;

    @Override
    public BaseServiceImplByAct getBaseServiceImplByAct() {
        return flowHrGbtpService;
    }

    /**
     * 获取干部调配管理页list
     *
     * @return
     */
    @PreAuthorize("hasAuthority('sixsys:flowHrGbtp:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取干部调配list")
    public ReturnBean<List<FlowHrGbtp>> list(@RequestParam Map para) {
        para.put("currentUserId", UserUtils.getCurrentUser().getId());
        return flowHrGbtpService.selectList(para);
    }


    /**
     * 添加干部调配
     *
     * @param govGbtp 干部调配
     * @return
     */
    @LogAnnotation(module = "添加干部调配")
    @PreAuthorize("hasAuthority('sixsys:flowHrGbtp:insert')")
    @PostMapping
    @ApiOperation(value = "添加干部调配")
    public ReturnBean insert(@RequestBody FlowHrGbtp govGbtp) {
        flowHrGbtpService.insert(govGbtp);
        return ReturnBean.ok();
    }


    /**
     * 修改干部调配
     *
     * @param govGbtp 干部调配
     * @return
     */
    @LogAnnotation(module = "修改干部调配")
    @PreAuthorize("hasAuthority('sixsys:flowHrGbtp:update')")
    @PutMapping
    @ApiOperation(value = "修改干部调配")
    public ReturnBean update(@RequestBody FlowHrGbtp govGbtp) {
        flowHrGbtpService.update(govGbtp);
        return ReturnBean.ok();
    }

    /**
     * 删除干部调配
     *
     * @param uuids 干部调配 uuids
     * @return
     */
    @LogAnnotation(module = "删除干部调配")
    @PreAuthorize("hasAuthority('sixsys:flowHrGbtp:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除干部调配数据")
    public ReturnBean delete(@RequestParam String uuids) {
        if (uuids.contains(SysConstant.COMMA)) {
            flowHrGbtpService.deleteByIds(uuids);
        } else {
            flowHrGbtpService.delete(uuids);
        }
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取干部调配数据
     *
     * @param uuid 干部调配 uuid
     * @return
     */
    @PreAuthorize("hasAnyAuthority('sixsys:flowHrGbtp:see','sixsys:flowHrGbtp:update')")
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取干部调配数据")
    public ReturnBean<FlowHrGbtp> getByUuid(@PathVariable String uuid) {
        FlowHrGbtp flowHrGbtp = flowHrGbtpService.selectEntityById(uuid);
        return ReturnBean.ok(flowHrGbtp);
    }


    @PostMapping("submit")
    public ReturnBean submit(@RequestParam("ids") String ids) {
        System.out.println("提交审核");
        return flowHrGbtpService.submit(ids);

    }



}
