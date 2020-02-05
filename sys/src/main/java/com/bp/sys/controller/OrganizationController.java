package com.bp.sys.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bp.common.anno.LogAnnotation;
import com.bp.common.base.BaseController;
import com.bp.common.bean.ReturnBean;
import com.bp.sys.po.Organization;
import com.bp.sys.po.OrganizationDto;
import com.bp.sys.service.DictionaryService;
import com.bp.sys.service.OrganizationService;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author 钟欣凯111
 * @version 1.0
 * @Description: 组织机构控制层
 * @date 2017年05月17日
 */
@RestController
@RequestMapping(value = "system/organization")
public class OrganizationController extends BaseController {

    @Resource
    private OrganizationService organizationService;

    @Resource
    private DictionaryService dictionaryService;


    /**
     * 组织机构管理
     *
     * @return
     */
    @PreAuthorize("hasAuthority('system:organization:list')")
    @GetMapping("/list/{pid}")
    public List list(@PathVariable Integer pid) {
        Map para = new HashMap(1);
        logger.debug("pid:{}", pid);
        para.put("pid", pid);
        List list = organizationService.selectListNoPage(para);
        return list;
    }


    /**
     * 添加组织机构
     *
     * @param organization 组织机构
     * @return
     */
    @LogAnnotation(module = "添加组织机构")
    @PreAuthorize("hasAuthority('system:organization:insert')")
    @PostMapping
    public ReturnBean insert(@RequestBody Organization organization) {
        validateEntity(organization);
        organizationService.insert(organization);
        return ReturnBean.ok();
    }


    /**
     * 修改组织机构
     *
     * @param organization 组织机构
     * @return
     */
    @LogAnnotation(module = "修改组织机构")
    @PreAuthorize("hasAuthority('system:organization:update')")
    @PutMapping
    public ReturnBean update(@RequestBody Organization organization) {
        validateEntity(organization);
        organizationService.update(organization);
        return ReturnBean.ok();
    }

    /**
     * 删除组织机构
     *
     * @param id 组织机构 id
     * @return
     */
    @LogAnnotation(module = "删除组织机构")
    @PreAuthorize("hasAuthority('system:organization:delete')")
    @DeleteMapping("/{id}")
    public ReturnBean delete(@PathVariable Integer id) {
        organizationService.delete(id);
        return ReturnBean.ok();
    }

    /**
     * 加载组织机构树
     *
     * @return
     */
    @GetMapping("/organizationTree")
    public ReturnBean<Object> organizationTree() {
        List list = organizationService.selectAllTree();
        JSONArray resTree = JSONArray.parseArray(JSON.toJSONString(list));
        return ReturnBean.ok(JSON.parse(resTree.toString()));
    }

    /**
     * 组织机构树
     *
     * @return
     */
    @GetMapping("/organizationTreeUI")
    public ReturnBean<Object> organizationTreeUI() {
        List list = organizationService.selectAllTree();
        String organizationTree = JSON.toJSONString(list);
        return ReturnBean.ok(JSON.parse(organizationTree));
    }

    /**
     * 检查机构代码是否重复
     *
     * @return
     */
    @PostMapping("/checkCode/{code}/{id}")
    public Boolean checkCode(@PathVariable String code, @PathVariable Integer id) {
        Integer count = organizationService.checkCode(id, code);
        if (count > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 查看组织机构页
     *
     * @param id 组织机构 id
     * @return
     */
    @PreAuthorize("hasAnyAuthority('system:organization:insert','system:organization:update','system:organization:see')")
    @GetMapping("/{id}")
    public ReturnBean<OrganizationDto> see(@PathVariable Integer id) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //机构类别
        OrganizationDto organizationDto = new OrganizationDto();
        Map organizationMap = organizationService.selectMapById(id);
        PropertyUtils.copyProperties(organizationDto, organizationMap);
        return ReturnBean.ok(organizationDto);
    }

    /**
     * 查看组织结构
     *
     * @return
     */
    @GetMapping("/relation")
    public ReturnBean<JSON> relation() {
        List<Map> list = organizationService.selectAllTree();
        return ReturnBean.ok(JSON.toJSON(list));
    }
}
