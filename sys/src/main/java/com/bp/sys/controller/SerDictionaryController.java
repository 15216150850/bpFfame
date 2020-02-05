package com.bp.sys.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bp.common.anno.LogAnnotation;
import com.bp.common.base.BaseController;
import com.bp.common.bean.DataTable;
import com.bp.common.bean.ReturnBean;
import com.bp.common.constants.SysConstant;
import com.bp.sys.po.SerDictionary;
import com.bp.sys.po.SerDictionaryDto;
import com.bp.sys.service.SerDictionaryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author
 * @version 1.0
 * @Description: 字典表控制层
 * @date 2017年04月11日
 */
@RestController
@RequestMapping(value = "ser/serDictionary")
public class SerDictionaryController extends BaseController {

    @Resource
    private SerDictionaryService serDictionaryService;

    /**
     * 新闻字典树状图
     *
     * @param type
     * @return
     */
    @GetMapping("selectTypeTree/{type}")
    public ReturnBean<Object> selectTypeTree(@PathVariable("type") String type) {
        JSONArray typeTree = JSONArray.parseArray(JSON.toJSONString(serDictionaryService.selectDicByTypeTree(type)));
        request.setAttribute("typeTree", typeTree);
        return ReturnBean.ok(JSON.parse(typeTree.toString()));
    }

    /**
     * 字典表管理页
     *
     * @return
     */
    @PreAuthorize("hasAuthority('ser:serDictionary:list')")
    @GetMapping("/list/{pid}/{type}")
    public ReturnBean<Map<String, String>> list(@PathVariable String pid, @PathVariable String type) {
        String fid = "-1";
        Map para = new HashMap(2);
        para.put("type", type);
        para.put("pid", pid);
        if (!SysConstant.DIC_PID.equals(pid) && !"".equals(pid)) {
            SerDictionary m = serDictionaryService.selectEntityById(Integer.valueOf(pid));
            fid = m.getPid() + "";
        }
        para.put("fid", fid);
        return ReturnBean.ok(para);
    }

    /**
     * 字典表管理
     *
     * @return
     */
    @PreAuthorize("hasAuthority('ser:serDictionary:list')")
    @PostMapping("/listData")
    public DataTable list(@RequestParam Map para) {
        return serDictionaryService.selectDatableList(para);
    }

    /**
     * 添加字典表
     *
     * @param serDictionary 字典表
     * @return
     */
    @LogAnnotation(module = "添加字典表")
    @PreAuthorize("hasAuthority('ser:serDictionary:insert')")
    @PostMapping
    public ReturnBean insert(@RequestBody SerDictionary serDictionary) {
        //validateEntity(dictionary);
        Integer count = serDictionaryService.checkCode(serDictionary.getType(), serDictionary.getCode(), serDictionary.getId());
        logger.debug("=======================count:{}", count);
        if (count == 0) {
            serDictionaryService.insert(serDictionary);
            return ReturnBean.ok();
        } else {
            return ReturnBean.error("字典编码已存在");
        }
    }

    /**
     * 查看字典表页
     *
     * @param id 字典表 id
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ser:serDictionary:update','ser:serDictionary:see')")
    @GetMapping("/{id}")
    public ReturnBean<SerDictionary> update(@PathVariable Integer id) {
        SerDictionary serDictionary = serDictionaryService.selectEntityById(id);
        return ReturnBean.ok(serDictionary);
    }


    /**
     * 根据类型获取字典1.0
     *
     * @param type 字典类型
     * @return
     */
    @GetMapping("/selectDicByType/{type}")
    public ReturnBean<List<SerDictionary>> selectDicByType(@PathVariable String type) {
        List<SerDictionary> serDictionaryList = serDictionaryService.selectDicByType(type);
        return ReturnBean.ok(serDictionaryList);
    }

    /**
     * 根据类型字符串集合获取字典2.0
     *
     * @param types 字典类型字符串逗号隔开
     * @return
     */
    @GetMapping("/selectDicByTypes/{types}")
    public ReturnBean<List<SerDictionaryDto>> selectDicByTypes(@PathVariable String types) {
        List<SerDictionaryDto> lists = new LinkedList<>();
        List<SerDictionary> serDictionaryList = serDictionaryService.selectDicByTypes(types);
        for (String type : types.split(",")) {
            SerDictionaryDto serDictionaryDto = new SerDictionaryDto();
            List<SerDictionary> serDictionaryFilterList = serDictionaryList.stream().filter(list -> list.getType().equals(type)).collect(Collectors.toList());
            serDictionaryDto.setTypeName(type);
            serDictionaryDto.setSerDictionaryList(serDictionaryFilterList);
            lists.add(serDictionaryDto);
        }
        return ReturnBean.ok(lists);
    }


    /**
     * 修改字典表
     *
     * @param serDictionary 字典表
     * @return
     */
    @LogAnnotation(module = "修改字典表")
    @PreAuthorize("hasAuthority('ser:serDictionary:update')")
    @PutMapping
    public ReturnBean update(@RequestBody SerDictionary serDictionary) {
        //validateEntity(dictionary);
        // 检测字典代码是否重复
        Integer count = serDictionaryService.checkCode(serDictionary.getType(), serDictionary.getCode(), serDictionary.getId());
        if (count == 0) {
            serDictionaryService.update(serDictionary);
            return ReturnBean.ok();
        } else {
            return ReturnBean.error("字典编码已存在");
        }
    }

    /**
     * 删除字典表
     *
     * @param id 字典表 id
     * @return
     */
    @LogAnnotation(module = "删除字典表")
    @PreAuthorize("hasAuthority('ser:serDictionary:delete')")
    @DeleteMapping("/{id}")
    public ReturnBean delete(@PathVariable Integer id) {
        serDictionaryService.delete(id);
        return ReturnBean.ok();
    }

    /**
     * 批量删除字典表
     *
     * @param ids 字典表 ids
     * @return
     */
    @LogAnnotation(module = "批量删除字典表")
    @PreAuthorize("hasAuthority('ser:serDictionary:delete')")
    @DeleteMapping("/deleteByIds/{ids}")
    public ReturnBean deleteByIds(@PathVariable String ids) {
        serDictionaryService.deleteByIds(ids);
        return ReturnBean.ok();
    }

    /**
     * 根据类型和code值获取字典
     * @param type  类型
     * @param code  code值
     * @return
     */
    @GetMapping("/selectDicByType/{type}/{code}")
    public ReturnBean selectDicByType(@PathVariable String type, @PathVariable String code) {
        SerDictionary serDictionary = serDictionaryService.selectByCodeAndType(type, code);
        return ReturnBean.ok(serDictionary);
    }
}
