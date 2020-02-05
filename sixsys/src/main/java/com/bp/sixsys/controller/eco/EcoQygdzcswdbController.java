package com.bp.sixsys.controller.eco;


import com.bp.common.anno.LogAnnotation;
import com.bp.common.base.BaseActController;
import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.constants.SysConstant;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.po.eco.EcoQygdzcswdb;
import com.bp.sixsys.service.eco.EcoQygdzcswdbService;

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
 * @Description: 经济权力-企业固定资产所外调拨控制层
 * @date 2019年10月08日
 */
@RestController
@RequestMapping(value = "/ecoQygdzcswdb")
@Api(value = "经济权力-企业固定资产所外调拨")
public class EcoQygdzcswdbController extends BaseActController {

    @Resource
    private EcoQygdzcswdbService ecoQygdzcswdbService;

    /**
     * 获取经济权力-企业固定资产所外调拨管理页list
     *
     * @return
     */
    @PreAuthorize("hasAuthority('sixsys:ecoQygdzcswdb:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取经济权力-企业固定资产所外调拨list")
    public ReturnBean<List<EcoQygdzcswdb>> list(@RequestParam Map para) {
        para.put("currentUserId", UserUtils.getCurrentUser().getId());
        return ecoQygdzcswdbService.selectList(para);
    }


    /**
     * 添加经济权力-企业固定资产所外调拨
     *
     * @param ecoQygdzcswdb 经济权力-企业固定资产所外调拨
     * @return
     */
    @LogAnnotation(module = "添加经济权力-企业固定资产所外调拨")
    @PreAuthorize("hasAuthority('sixsys:ecoQygdzcswdb:insert')")
    @PostMapping
    @ApiOperation(value = "添加经济权力-企业固定资产所外调拨")
    public ReturnBean insert(@RequestBody EcoQygdzcswdb ecoQygdzcswdb) {
        ecoQygdzcswdbService.insert(ecoQygdzcswdb);
        return ReturnBean.ok();
    }


    /**
     * 修改经济权力-企业固定资产所外调拨
     *
     * @param ecoQygdzcswdb 经济权力-企业固定资产所外调拨
     * @return
     */
    @LogAnnotation(module = "修改经济权力-企业固定资产所外调拨")
    @PreAuthorize("hasAuthority('sixsys:ecoQygdzcswdb:update')")
    @PutMapping
    @ApiOperation(value = "修改经济权力-企业固定资产所外调拨")
    public ReturnBean update(@RequestBody EcoQygdzcswdb ecoQygdzcswdb) {
    	if("新建".equals(ecoQygdzcswdb.getDocState())) {
    		ecoQygdzcswdbService.update(ecoQygdzcswdb);
    	} else {
    		ecoQygdzcswdbService.insert(ecoQygdzcswdb);
    	}
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取经济权力-企业固定资产所外调拨数据
     *
     * @param uuid 经济权力-企业固定资产所外调拨 uuid
     * @return
     */
    @PreAuthorize("hasAnyAuthority('sixsys:ecoQygdzcswdb:see','sixsys:ecoQygdzcswdb:update')")
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取经济权力-企业固定资产所外调拨数据")
    public ReturnBean<EcoQygdzcswdb> getByUuid(@PathVariable String uuid) {
        EcoQygdzcswdb ecoQygdzcswdb = ecoQygdzcswdbService.selectEntityById(uuid);
        return ReturnBean.ok(ecoQygdzcswdb);
    }

    /**
     * 删除经济权力-企业固定资产所外调拨
     *
     * @param uuids 经济权力-企业固定资产所外调拨 uuids
     * @return
     */
    @LogAnnotation(module = "删除经济权力-企业固定资产所外调拨")
    @PreAuthorize("hasAuthority('sixsys:ecoQygdzcswdb:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除经济权力-企业固定资产所外调拨数据")
    public ReturnBean delete(@RequestParam String uuids) {
        if (uuids.contains(SysConstant.COMMA)) {
            ecoQygdzcswdbService.deleteByIds(uuids);
        } else {
            ecoQygdzcswdbService.delete(uuids);
        }
        return ReturnBean.ok();
    }

    /**
     * 启动流程
     *
     * @param uuids
     * @return
     */
    @PreAuthorize("hasAuthority('sixsys:ecoQygdzcswdb:submit')")
    @PostMapping("submit")
    public ReturnBean submit(@RequestParam("ids") String uuids, @RequestParam(value = "isAbility", required = false) String isAbility) {
        for (String id : uuids.split(SysConstant.COMMA)) {
            ecoQygdzcswdbService.submit(id);
        }
        return ReturnBean.ok("流程启动成功");
    }



    @Override
    public BaseServiceImplByAct getBaseServiceImplByAct() {
        return ecoQygdzcswdbService;
    }

}
