package com.bp.sys.controller;


import com.bp.common.base.BaseController;
import com.bp.common.bean.DataTable;
import com.bp.common.bean.ReturnBean;
import com.bp.sys.po.SerDictionaryType;
import com.bp.sys.service.SerDictionaryTypeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;


/**
 * @author
 * @version 1.0
 * @Description: 字典类型表控制层
 * @date 2017年04月11日
 */
@RestController
@RequestMapping(value = "ser/serDictionaryType")
public class SerDictionaryTypeController extends BaseController {

    @Resource
    private SerDictionaryTypeService serDictionaryTypeService;


    /**
     * 字典类型表管理
     *
     * @return
     */
    @PreAuthorize("hasAuthority('ser:serDictionaryType:list')")
    @PostMapping("/list")
    public DataTable list(@RequestParam Map para) {
        return serDictionaryTypeService.selectDatableList(para);
    }


    /**
     * 添加字典类型表
     *
     * @param serDictionaryType 字典类型表
     * @return
     */

    @PreAuthorize("hasAuthority('ser:serDictionaryType:insert')")
    @PostMapping
    public ReturnBean insert(@RequestBody SerDictionaryType serDictionaryType) {
        //validateEntity(dictionaryType);
        Integer count = serDictionaryTypeService.checkCode(serDictionaryType.getCode(), serDictionaryType.getId());
        if (count == 0) {
            serDictionaryTypeService.insert(serDictionaryType);
            return ReturnBean.ok();
        } else {
            return ReturnBean.error("字典编码已存在");
        }
    }

    /**
     * 修改字典类型表页
     *
     * @param id 字典类型表 id
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ser:serDictionaryType:see','ser:serDictionaryType:update')")
    @GetMapping("/{id}")
    public ReturnBean<SerDictionaryType> update(@PathVariable Integer id) {
        SerDictionaryType serDictionaryType = serDictionaryTypeService.selectEntityById(id);
        return ReturnBean.ok(serDictionaryType);
    }

    /**
     * 修改字典类型表
     *
     * @param serDictionaryType 字典类型表
     * @return
     */
    @PreAuthorize("hasAuthority('ser:serDictionaryType:update')")
    @PutMapping
    public ReturnBean update(@RequestBody SerDictionaryType serDictionaryType) {
        //validateEntity(dictionaryType);
        Integer count = serDictionaryTypeService.checkCode(serDictionaryType.getCode(), serDictionaryType.getId());
        if (count == 0) {
            serDictionaryTypeService.update(serDictionaryType);
            return ReturnBean.ok();
        } else {
            return ReturnBean.error("字典编码已存在");
        }
    }

    /**
     * 删除字典类型表
     *
     * @param id   字典类型表 id
     * @param code 字典类型编码
     * @return
     */
    @PreAuthorize("hasAuthority('ser:serDictionaryType:delete')")
    @DeleteMapping("/{id}/{code}")
    public ReturnBean delete(@PathVariable String id, @PathVariable String code) {
        serDictionaryTypeService.delete("null".equals(id) ? null : Integer.valueOf(id), code);
        return ReturnBean.ok();
    }

    /**
     * 批量删除字典类型表
     *
     * @param ids
     * @param codes
     * @return
     */
    @PreAuthorize("hasAuthority('ser:serDictionaryType:delete')")
    @DeleteMapping("/deleteByIdsAndCodes/{ids}/{codes}")
    @ResponseBody
    public ReturnBean deleteByIdsAndCodes(@PathVariable String ids, @PathVariable String codes) {
        serDictionaryTypeService.deleteByIdsAndCodes(ids, codes);
        return ReturnBean.ok();
    }
}
