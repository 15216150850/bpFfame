package com.bp.sys.controller;


import com.bp.common.anno.LogAnnotation;
import com.bp.common.base.BaseController;
import com.bp.common.bean.ReturnBean;
import com.bp.sys.po.Wxxsjfx;
import com.bp.sys.service.WxxsjfxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * @author zhangyu
 * @version 1.0
 * @Description: 危险性数据分析控制层
 * @date 2019年10月25日
 */
@RestController
@RequestMapping(value = "/wxxsjfx")
@Api(description = "危险性数据分析")
public class WxxsjfxController extends BaseController {

    @Resource
    private WxxsjfxService wxxsjfxService;

    /**
     * 获取危险性数据分析管理页list
     *
     * @return
     */
    @PreAuthorize("hasAuthority('sys:wxxsjfx:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取危险性数据分析list")
    public ReturnBean<List<Wxxsjfx>> list(@RequestParam Map para) {
        return wxxsjfxService.selectList(para);
    }


    /**
     * 添加危险性数据分析
     *
     * @param wxxsjfx 危险性数据分析
     * @return
     */
    @LogAnnotation(module = "添加危险性数据分析")
    @PreAuthorize("hasAuthority('sys:wxxsjfx:insert')")
    @PostMapping
    @ApiOperation(value = "添加危险性数据分析")
    public ReturnBean insert(@RequestBody Wxxsjfx wxxsjfx) {
        wxxsjfxService.insert(wxxsjfx);
        return ReturnBean.ok();
    }


    /**
     * 修改危险性数据分析
     *
     * @param wxxsjfx 危险性数据分析
     * @return
     */
    @LogAnnotation(module = "修改危险性数据分析")
    @PreAuthorize("hasAuthority('sys:wxxsjfx:update')")
    @PutMapping
    @ApiOperation(value = "修改危险性数据分析")
    public ReturnBean update(@RequestBody Wxxsjfx wxxsjfx) {
        wxxsjfxService.update(wxxsjfx);
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取危险性数据分析数据
     *
     * @param uuid 危险性数据分析 uuid
     * @return
     */
    @PreAuthorize("hasAnyAuthority('sys:wxxsjfx:see','sys:wxxsjfx:update')")
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取危险性数据分析数据")
    public ReturnBean<Wxxsjfx> getByUuid(@PathVariable String uuid) {
        Wxxsjfx wxxsjfx = wxxsjfxService.selectEntityById(uuid);
        return ReturnBean.ok(wxxsjfx);
    }

    /**
     * 删除危险性数据分析
     *
     * @param uuids 危险性数据分析 uuids
     * @return
     */
    @LogAnnotation(module = "删除危险性数据分析")
    @PreAuthorize("hasAuthority('sys:wxxsjfx:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除危险性数据分析数据")
    public ReturnBean delete(@RequestParam String uuids) {
        if (uuids.contains(",")) {
            wxxsjfxService.deleteByIds(uuids);
        } else {
            wxxsjfxService.delete(uuids);
        }
        return ReturnBean.ok();
    }

    /**
     * 同步数据
     *
     * @return
     */
    @GetMapping("/syncData")
    @ApiOperation(value = "同步数据")
    public ReturnBean<Wxxsjfx> syncData() {
        wxxsjfxService.syncData();
        return ReturnBean.ok("同步成功");
    }

    /**
     * 根据戒毒人员编码查询各项得分
     *
     * @return
     */
    @GetMapping("/selectScoreByJdrybm/{jdrybm}")
    @ApiOperation(value = "根据戒毒人员编码查询各项得分")
    public ReturnBean<Wxxsjfx> selectScoreByJdrybm(@PathVariable String jdrybm) {
        return wxxsjfxService.selectScoreByJdrybm(jdrybm);
    }


    /**
     * 获取戒治效能数据分析管理页list
     *
     * @return
     */
    @GetMapping("/wxxsjList")
    @ApiOperation(value = "获取戒治效能数据分析list")
    public ReturnBean wxxsjList(@RequestParam Map para) {
        return wxxsjfxService.selectWxxsjList(para);
    }


    /**
     * 查询本月全所平均分
     *
     * @return
     */
    @GetMapping("/avgWithMonth")
    @ApiOperation(value = "查询本月全所平均分")
    public ReturnBean avgWithMonth() {
        return ReturnBean.ok(wxxsjfxService.selectAvg());
    }


    /**
     * 获取戒毒人员详细数据
     *
     * @return
     */
    @GetMapping("/getDetailData/{jdrybm}")
    @ApiOperation(value = "获取戒毒人员详细数据")
    public ReturnBean getDetailData(@PathVariable String jdrybm) {
        return ReturnBean.ok(wxxsjfxService.getDetailData(jdrybm));
    }


}
