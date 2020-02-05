package com.bp.sixsys.controller.eco;


import com.bp.common.anno.LogAnnotation;
import com.bp.common.base.BaseActController;
import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.constants.SysConstant;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.po.eco.FlowEcoZyzl;
import com.bp.sixsys.service.eco.FlowEcoZyzlService;
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
 * @Description: 资源租赁审批控制层
 * @date 2019年10月12日
 */
@RestController
@RequestMapping(value = "/flowEcoZyzl")
@Api(description = "资源租赁审批")
public class FlowEcoZyzlController extends BaseActController {

    @Resource
    private FlowEcoZyzlService flowEcoZyzlService;


    @Override
    public BaseServiceImplByAct getBaseServiceImplByAct() {
        return flowEcoZyzlService;
    }

    /**
     * 获取资源租赁审批管理页list
     *
     * @return
     */
    @PreAuthorize("hasAuthority('sixsys:flowEcoZyzl:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取资源租赁审批list")
    public ReturnBean<List<FlowEcoZyzl>> list(@RequestParam Map para) {
        // 只能查看自己创建的记录
        para.put("currentUserId", UserUtils.getCurrentUser().getId());
        return flowEcoZyzlService.selectList(para);
    }


    /**
     * 添加资源租赁审批
     *
     * @param govZyzl 资源租赁审批
     * @return
     */
    @LogAnnotation(module = "添加资源租赁审批")
    @PreAuthorize("hasAuthority('sixsys:flowEcoZyzl:insert')")
    @PostMapping
    @ApiOperation(value = "添加资源租赁审批")
    public ReturnBean insert(@RequestBody FlowEcoZyzl govZyzl) {
        flowEcoZyzlService.insert(govZyzl);
        return ReturnBean.ok();
    }


    /**
     * 修改资源租赁审批
     *
     * @param govZyzl 资源租赁审批
     * @return
     */
    @LogAnnotation(module = "修改资源租赁审批")
    @PreAuthorize("hasAuthority('sixsys:flowEcoZyzl:update')")
    @PutMapping
    @ApiOperation(value = "修改资源租赁审批")
    public ReturnBean update(@RequestBody FlowEcoZyzl govZyzl) {
        flowEcoZyzlService.update(govZyzl);
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取资源租赁审批数据
     *
     * @param uuid 资源租赁审批 uuid
     * @return
     */
    @PreAuthorize("hasAnyAuthority('sixsys:flowEcoZyzl:see','sixsys:flowEcoZyzl:update')")
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取资源租赁审批数据")
    public ReturnBean<FlowEcoZyzl> getByUuid(@PathVariable String uuid) {
        FlowEcoZyzl govZyzl = flowEcoZyzlService.selectEntityById(uuid);
        return ReturnBean.ok(govZyzl);
    }

    /**
     * 删除资源租赁审批
     *
     * @param uuids 资源租赁审批 uuids
     * @return
     */
    @LogAnnotation(module = "删除资源租赁审批")
    @PreAuthorize("hasAuthority('sixsys:flowEcoZyzl:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除资源租赁审批数据")
    public ReturnBean delete(@RequestParam String uuids) {
        if (uuids.contains(SysConstant.COMMA)) {
            flowEcoZyzlService.deleteByIds(uuids);
        } else {
            flowEcoZyzlService.delete(uuids);
        }
        return ReturnBean.ok();
    }

    @PostMapping("submit")
    public ReturnBean submit(@RequestParam("ids") String ids) {
        System.out.println("提交审核");
        return flowEcoZyzlService.submit(ids);
    }


}
