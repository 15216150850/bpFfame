package com.bp.sixsys.controller.eco;


import com.bp.common.anno.LogAnnotation;
import com.bp.common.base.BaseActController;
import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.constants.SysConstant;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.po.eco.EcoQygdzccz;
import com.bp.sixsys.service.eco.EcoQygdzcczService;

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
 * @Description: 经济权力-企业固定资产处置控制层
 * @date 2019年10月09日
 */
@RestController
@RequestMapping(value = "/ecoQygdzccz")
@Api(value = "经济权力-企业固定资产处置")
public class EcoQygdzcczController extends BaseActController {

    @Resource
    private EcoQygdzcczService ecoQygdzcczService;

    /**
     * 获取经济权力-企业固定资产处置管理页list
     *
     * @return
     */
    @PreAuthorize("hasAuthority('sixsys:ecoQygdzccz:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取经济权力-企业固定资产处置list")
    public ReturnBean<List<EcoQygdzccz>> list(@RequestParam Map para) {
        para.put("currentUserId", UserUtils.getCurrentUser().getId());
        return ecoQygdzcczService.selectList(para);
    }


    /**
     * 添加经济权力-企业固定资产处置
     *
     * @param ecoQygdzccz 经济权力-企业固定资产处置
     * @return
     */
    @LogAnnotation(module = "添加经济权力-企业固定资产处置")
    @PreAuthorize("hasAuthority('sixsys:ecoQygdzccz:insert')")
    @PostMapping
    @ApiOperation(value = "添加经济权力-企业固定资产处置")
    public ReturnBean insert(@RequestBody EcoQygdzccz ecoQygdzccz) {
        ecoQygdzcczService.insert(ecoQygdzccz);
        return ReturnBean.ok();
    }


    /**
     * 修改经济权力-企业固定资产处置
     *
     * @param ecoQygdzccz 经济权力-企业固定资产处置
     * @return
     */
    @LogAnnotation(module = "修改经济权力-企业固定资产处置")
    @PreAuthorize("hasAuthority('sixsys:ecoQygdzccz:update')")
    @PutMapping
    @ApiOperation(value = "修改经济权力-企业固定资产处置")
    public ReturnBean update(@RequestBody EcoQygdzccz ecoQygdzccz) {
    	if("新建".equals(ecoQygdzccz.getDocState())) {
    		ecoQygdzcczService.update(ecoQygdzccz);
    	} else {
    		ecoQygdzcczService.insert(ecoQygdzccz);
    	}
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取经济权力-企业固定资产处置数据
     *
     * @param uuid 经济权力-企业固定资产处置 uuid
     * @return
     */
    @PreAuthorize("hasAnyAuthority('sixsys:ecoQygdzccz:see','sixsys:ecoQygdzccz:update')")
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取经济权力-企业固定资产处置数据")
    public ReturnBean<EcoQygdzccz> getByUuid(@PathVariable String uuid) {
        EcoQygdzccz ecoQygdzccz = ecoQygdzcczService.selectEntityById(uuid);
        return ReturnBean.ok(ecoQygdzccz);
    }

    /**
     * 删除经济权力-企业固定资产处置
     *
     * @param uuids 经济权力-企业固定资产处置 uuids
     * @return
     */
    @LogAnnotation(module = "删除经济权力-企业固定资产处置")
    @PreAuthorize("hasAuthority('sixsys:ecoQygdzccz:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除经济权力-企业固定资产处置数据")
    public ReturnBean delete(@RequestParam String uuids) {
        if (uuids.contains(SysConstant.COMMA)) {
            ecoQygdzcczService.deleteByIds(uuids);
        } else {
            ecoQygdzcczService.delete(uuids);
        }
        return ReturnBean.ok();
    }

    /**
     * 启动流程
     *
     * @param uuids
     * @return
     */
    @PreAuthorize("hasAuthority('sixsys:ecoQygdzccz:submit')")
    @PostMapping("submit")
    public ReturnBean submit(@RequestParam("ids") String uuids, @RequestParam(value = "isAbility", required = false) String isAbility) {
        for (String id : uuids.split(SysConstant.COMMA)) {
            ecoQygdzcczService.submit(id);
        }
        return ReturnBean.ok("流程启动成功");
    }


    @Override
    public BaseServiceImplByAct getBaseServiceImplByAct() {
        return ecoQygdzcczService;
    }
}
