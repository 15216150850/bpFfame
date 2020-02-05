package com.bp.sys.controller;

import com.alibaba.fastjson.JSON;
import com.bp.common.anno.LogAnnotation;
import com.bp.common.base.BaseController;
import com.bp.common.bean.ReturnBean;
import com.bp.sys.po.Region;
import com.bp.sys.service.RegionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * @version 1.0
 * @Description：区域控制层
 * @author：
 * @date：2016-07-26
 */
@RestController
@RequestMapping(value = "system/region")
public class RegionController extends BaseController {

    @Resource
    private RegionService regionService;


    /**
     * 获取子集
     *
     * @param pid
     * @return
     */
    @GetMapping("/regionChild/{pid}")
    public ReturnBean regionChild(@PathVariable Integer pid) {
        List list = regionService.selectListByPid(pid);
        return ReturnBean.ok(list);
    }


    /**
     * 查看区域信息
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyAuthority('system:region:see','system:region:update')")
    @GetMapping("/{id}")
    public ReturnBean<Region> getRegionMap(@PathVariable Integer id) {
        Region region = regionService.selectEntityById(id);
        return ReturnBean.ok(region);
    }


    /**
     * 添加区域
     *
     * @param region 区域
     * @return
     */
    @LogAnnotation(module = "添加区域")
    @PreAuthorize("hasAuthority('system:region:insert')")
    @PostMapping
    public ReturnBean insert(@RequestBody Region region) {
        if (region.getPid() == null) {
            region.setPid(0);
        }
        validateEntity(region);
        regionService.insert(region);
        return ReturnBean.ok();
    }


    /**
     * 修改区域
     *
     * @param region 区域
     * @return
     */
    @LogAnnotation(module = "修改区域")
    @PreAuthorize("hasAuthority('system:region:update')")
    @PutMapping
    public ReturnBean update(@RequestBody Region region) {
        if (region.getPid() == null) {
            region.setPid(0);
        }
        validateEntity(region);
        regionService.update(region);
        return ReturnBean.ok();
    }


    /**
     * 删除区域
     *
     * @param id 区域 id
     * @return
     */
    @LogAnnotation(module = "删除区域")
    @PreAuthorize("hasAuthority('system:region:delete')")
    @DeleteMapping("/{id}")
    public ReturnBean delete(@PathVariable Integer id) {
        regionService.delete(id);
        return ReturnBean.ok();
    }


    /**
     * 区域树
     *
     * @return
     */
    @GetMapping("/regionTree")
    public ReturnBean<Object> regionTreeData() {
        JSON regionTree = regionService.selectRegionTree();
        return ReturnBean.ok(JSON.parse(regionTree.toString()));
    }


    /**
     * 检测区域编码
     *
     * @param id
     * @param code
     * @return
     */
    @PostMapping("/checkCode")
    public boolean checkCode(Integer id, String code) {
        Integer count = regionService.checkCode(id, code);
        if (count > 0) {
            return false;
        } else {
            return true;
        }
    }
}
