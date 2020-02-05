package com.bp.sixsys.controller.eco;


import com.bp.common.anno.LogAnnotation;
import com.bp.common.base.BaseActController;
import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.constants.SysConstant;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.po.eco.FlowEcoKfldtxsp;
import com.bp.sixsys.service.eco.FlowEcoKfldtxspService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * @author yanjisheng
 * @version 1.0
 * @Description: 康复劳动调休审批控制层
 * @date 2019年10月12日
 */
@RestController
@RequestMapping(value = "/flowEcoKfldtxsp")
@Api(description = "康复劳动调休审批")
public class FlowEcoKfldtxspController extends BaseActController {

    @Resource
    private FlowEcoKfldtxspService flowEcoKfldtxspService;


    @Override
    public BaseServiceImplByAct getBaseServiceImplByAct() {
        return flowEcoKfldtxspService;
    }

    /**
     * 获取康复劳动调休审批管理页list
     *
     * @return
     */
    @PreAuthorize("hasAuthority('sixsys:flowEcoKfldtxsp:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取康复劳动调休审批list")
    public ReturnBean<List<FlowEcoKfldtxsp>> list(@RequestParam Map para) {
        para.put("currentUserId", UserUtils.getCurrentUser().getId());
        return flowEcoKfldtxspService.selectList(para);
    }


    /**
     * 添加康复劳动调休审批
     *
     * @param govKfldtxsp 康复劳动调休审批
     * @return
     */
    @LogAnnotation(module = "添加康复劳动调休审批")
    @PreAuthorize("hasAuthority('sixsys:flowEcoKfldtxsp:insert')")
    @PostMapping
    @ApiOperation(value = "添加康复劳动调休审批")
    public ReturnBean insert(@RequestBody FlowEcoKfldtxsp govKfldtxsp) {
        flowEcoKfldtxspService.insert(govKfldtxsp);
        return ReturnBean.ok();
    }


    /**
     * 修改康复劳动调休审批
     *
     * @param govKfldtxsp 康复劳动调休审批
     * @return
     */
    @LogAnnotation(module = "修改康复劳动调休审批")
    @PreAuthorize("hasAuthority('sixsys:flowEcoKfldtxsp:update')")
    @PutMapping
    @ApiOperation(value = "修改康复劳动调休审批")
    public ReturnBean update(@RequestBody FlowEcoKfldtxsp govKfldtxsp) {
        flowEcoKfldtxspService.update(govKfldtxsp);
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取康复劳动调休审批数据
     *
     * @param uuid 康复劳动调休审批 uuid
     * @return
     */
    @PreAuthorize("hasAnyAuthority('sixsys:flowEcoKfldtxsp:see','sixsys:flowEcoKfldtxsp:update')")
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取康复劳动调休审批数据")
    public ReturnBean<FlowEcoKfldtxsp> getByUuid(@PathVariable String uuid) {
        FlowEcoKfldtxsp govKfldtxsp = flowEcoKfldtxspService.selectEntityById(uuid);
        return ReturnBean.ok(govKfldtxsp);
    }

    /**
     * 删除康复劳动调休审批
     *
     * @param uuids 康复劳动调休审批 uuids
     * @return
     */
    @LogAnnotation(module = "删除康复劳动调休审批")
    @PreAuthorize("hasAuthority('sixsys:flowEcoKfldtxsp:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除康复劳动调休审批数据")
    public ReturnBean delete(@RequestParam String uuids) {
        if (uuids.contains(SysConstant.COMMA)) {
            flowEcoKfldtxspService.deleteByIds(uuids);
        } else {
            flowEcoKfldtxspService.delete(uuids);
        }
        return ReturnBean.ok();
    }

    @PostMapping("submit")
    public ReturnBean submit(@RequestParam("ids") String ids) {
        System.out.println("提交审核");
        return flowEcoKfldtxspService.submit(ids);
    }


}
