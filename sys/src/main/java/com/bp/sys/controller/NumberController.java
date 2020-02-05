package com.bp.sys.controller;


import com.bp.common.anno.LogAnnotation;
import com.bp.common.base.BaseController;
import com.bp.common.bean.ReturnBean;
import com.bp.model.sys.Number;
import com.bp.sys.service.NumberService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * @author
 * @version 1.0
 * @Description: 流水号控制层
 * @date 2017年05月17日
 */
@RestController
@RequestMapping(value = "system/number")
public class NumberController extends BaseController {

    @Resource
    private NumberService numberService;


    /**
     * 流水号管理
     *
     * @return
     */
    @PreAuthorize("hasAuthority('system:number:list')")
    @GetMapping("/list")
    public ReturnBean<List<Number>> list(@RequestParam Map<String, Object> para) {
        return numberService.queryList(para);
    }


    /**
     * 添加流水号
     *
     * @param number 流水号
     * @return
     */
    @PreAuthorize("hasAuthority('system:number:insert')")
    @LogAnnotation(module = "添加流水号")
    @PostMapping
    public ReturnBean insert(@RequestBody Number number) {
        //validateEntity(number);
        numberService.insert(number);
        return ReturnBean.ok();
    }


    /**
     * 修改流水号
     *
     * @param number 流水号
     * @return
     */
    @PreAuthorize("hasAuthority('system:number:update')")
    @LogAnnotation(module = "修改流水号")
    @PutMapping
    public ReturnBean update(@RequestBody Number number) {
        //validateEntity(number);
        numberService.update(number);
        return ReturnBean.ok();
    }


    /**
     * 删除流水号
     *
     * @param id 流水号 id
     * @return
     */
    @PreAuthorize("hasAuthority('system:number:delete')")
    @LogAnnotation(module = "删除流水号")
    @DeleteMapping("/{id}")
    public ReturnBean delete(@PathVariable Long id) {
        numberService.delete(id);
        return ReturnBean.ok();
    }

    /**
     * 批量删除流水号
     *
     * @param ids 流水号 ids
     * @return
     */
    @PreAuthorize("hasAuthority('system:number:delete')")
    @LogAnnotation(module = "批量删除流水号")
    @DeleteMapping("/deleteByIds/{ids}")
    public ReturnBean deleteByIds(@PathVariable String ids) {
        numberService.deleteByIds(ids);
        return ReturnBean.ok();
    }

    /**
     * 检查流水号编码是否重复
     *
     * @param id
     * @param code
     * @return
     */
    @GetMapping("/checkNumber/{id}/{code}")
    public Boolean checkNumber(@PathVariable String id, @PathVariable String code) {
        Integer count = numberService.checkNumber("null".equals(id) ? null : Long.valueOf(id), code);
        if (count > 0) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * 查看流水号编码
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ReturnBean<Number> getNumber(@PathVariable long id) {
        Number number = numberService.selectEntityById(id);
        return ReturnBean.ok(number);
    }
}
