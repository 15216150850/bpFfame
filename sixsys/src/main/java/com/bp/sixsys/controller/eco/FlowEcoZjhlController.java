package com.bp.sixsys.controller.eco;


import com.bp.common.anno.LogAnnotation;
import com.bp.common.base.BaseActController;
import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.constants.SysConstant;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.po.eco.FlowEcoZjhl;
import com.bp.sixsys.service.eco.FlowEcoZjhlService;
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
 * @Description: 资金回笼控制层
 * @date 2019年10月15日
 */
@RestController
@RequestMapping(value = "/flowEcoZjhl")
@Api(description = "资金回笼")
public class FlowEcoZjhlController extends BaseActController {

    @Resource
    private FlowEcoZjhlService flowEcoZjhlService;


    @Override
    public BaseServiceImplByAct getBaseServiceImplByAct() {
        return flowEcoZjhlService;
    }

    /**
     * 获取资金回笼管理页list
     *
     * @return
     */
    @PreAuthorize("hasAuthority('sixsys:flowEcoZjhl:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取资金回笼list")
    public ReturnBean<List<FlowEcoZjhl>> list(@RequestParam Map para) {
        // 只能查看自己创建的记录
        para.put("currentUserId", UserUtils.getCurrentUser().getId());
        return flowEcoZjhlService.selectList(para);
    }


    /**
     * 添加资金回笼
     *
     * @param govZjhl 资金回笼
     * @return
     */
    @LogAnnotation(module = "添加资金回笼")
    @PreAuthorize("hasAuthority('sixsys:flowEcoZjhl:insert')")
    @PostMapping
    @ApiOperation(value = "添加资金回笼")
    public ReturnBean insert(@RequestBody FlowEcoZjhl govZjhl) {
        flowEcoZjhlService.insert(govZjhl);
        return ReturnBean.ok();
    }


    /**
     * 修改资金回笼
     *
     * @param govZjhl 资金回笼
     * @return
     */
    @LogAnnotation(module = "修改资金回笼")
    @PreAuthorize("hasAuthority('sixsys:flowEcoZjhl:update')")
    @PutMapping
    @ApiOperation(value = "修改资金回笼")
    public ReturnBean update(@RequestBody FlowEcoZjhl govZjhl) {
        flowEcoZjhlService.update(govZjhl);
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取资金回笼数据
     *
     * @param uuid 资金回笼 uuid
     * @return
     */
    @PreAuthorize("hasAnyAuthority('sixsys:flowEcoZjhl:see','sixsys:flowEcoZjhl:update')")
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取资金回笼数据")
    public ReturnBean<FlowEcoZjhl> getByUuid(@PathVariable String uuid) {
        FlowEcoZjhl govZjhl = flowEcoZjhlService.selectEntityById(uuid);
        return ReturnBean.ok(govZjhl);
    }

    /**
     * 删除资金回笼
     *
     * @param uuids 资金回笼 uuids
     * @return
     */
    @LogAnnotation(module = "删除资金回笼")
    @PreAuthorize("hasAuthority('sixsys:flowEcoZjhl:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除资金回笼数据")
    public ReturnBean delete(@RequestParam String uuids) {
        if (uuids.contains(SysConstant.COMMA)) {
            flowEcoZjhlService.deleteByIds(uuids);
        } else {
            flowEcoZjhlService.delete(uuids);
        }
        return ReturnBean.ok();
    }
    @PostMapping("submit")
    public ReturnBean submit(@RequestParam("ids") String ids) {
        System.out.println("提交审核");
        return flowEcoZjhlService.submit(ids);
    }



}
