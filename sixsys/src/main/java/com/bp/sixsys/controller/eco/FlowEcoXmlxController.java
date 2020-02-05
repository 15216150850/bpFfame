package com.bp.sixsys.controller.eco;


import com.bp.common.anno.LogAnnotation;
import com.bp.common.base.BaseActController;
import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.constants.SysConstant;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.po.eco.FlowEcoXmlx;
import com.bp.sixsys.service.eco.FlowEcoXmlxService;
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
 * @Description: gov_xmlx控制层
 * @date 2019年10月10日
 */
@RestController
@RequestMapping(value = "/flowEcoXmlx")
@Api(description = "gov_xmlx")
public class FlowEcoXmlxController extends BaseActController {

    @Resource
    private FlowEcoXmlxService flowEcoXmlxService;

    @Override
    public BaseServiceImplByAct getBaseServiceImplByAct() {
        return flowEcoXmlxService;
    }

    /**
     * 获取gov_xmlx管理页list
     *
     * @return
     */
    @PreAuthorize("hasAuthority('sixsys:flowEcoXmlx:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取gov_xmlxlist")
    public ReturnBean<List<FlowEcoXmlx>> list(@RequestParam Map para) {
        // 只能查看自己创建的记录
        para.put("currentUserId", UserUtils.getCurrentUser().getId());
        return flowEcoXmlxService.selectList(para);
    }


    /**
     * 添加gov_xmlx
     *
     * @param govXmlx gov_xmlx
     * @return
     */
    @LogAnnotation(module = "添加gov_xmlx")
    @PreAuthorize("hasAuthority('sixsys:flowEcoXmlx:insert')")
    @PostMapping
    @ApiOperation(value = "添加gov_xmlx")
    public ReturnBean insert(@RequestBody FlowEcoXmlx govXmlx) {
        flowEcoXmlxService.insert(govXmlx);
        return ReturnBean.ok();
    }


    /**
     * 修改gov_xmlx
     *
     * @param govXmlx gov_xmlx
     * @return
     */
    @LogAnnotation(module = "修改gov_xmlx")
    @PreAuthorize("hasAuthority('sixsys:flowEcoXmlx:update')")
    @PutMapping
    @ApiOperation(value = "修改gov_xmlx")
    public ReturnBean update(@RequestBody FlowEcoXmlx govXmlx) {
        flowEcoXmlxService.update(govXmlx);
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取gov_xmlx数据
     *
     * @param uuid gov_xmlx uuid
     * @return
     */
    @PreAuthorize("hasAnyAuthority('sixsys:flowEcoXmlx:see','sixsys:flowEcoXmlx:update')")
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取gov_xmlx数据")
    public ReturnBean<FlowEcoXmlx> getByUuid(@PathVariable String uuid) {
        FlowEcoXmlx govXmlx = flowEcoXmlxService.selectEntityById(uuid);
        return ReturnBean.ok(govXmlx);
    }

    /**
     * 删除gov_xmlx
     *
     * @param uuids gov_xmlx uuids
     * @return
     */
    @LogAnnotation(module = "删除gov_xmlx")
    @PreAuthorize("hasAuthority('sixsys:flowEcoXmlx:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除gov_xmlx数据")
    public ReturnBean delete(@RequestParam String uuids) {
        if (uuids.contains(SysConstant.COMMA)) {
            flowEcoXmlxService.deleteByIds(uuids);
        } else {
            flowEcoXmlxService.delete(uuids);
        }
        return ReturnBean.ok();
    }

    @PostMapping("submit")
    public ReturnBean submit(@RequestParam("ids") String ids) {
        System.out.println("提交审核");
        return flowEcoXmlxService.submit(ids);
    }


}
