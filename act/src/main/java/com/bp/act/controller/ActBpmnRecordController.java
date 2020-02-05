package com.bp.act.controller;



import com.bp.act.bean.ReturnBean;
import com.bp.act.entity.ActBpmnRecord;
import com.bp.act.service.ActBpmnRecordService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * @author 钟欣凯
 * @version 1.0
 * @Description: 流程部署表控制层
 * @date 2019年06月13日
 */
@RestController
@RequestMapping(value = "actBpmnRecord")
public class ActBpmnRecordController {

    @Resource
    private ActBpmnRecordService actBpmnRecordService;

    /**
     * 获取流程部署表管理页list
     *
     * @return
     */
    @PreAuthorize("hasAuthority('act:actBpmnRecord:list')")
    @GetMapping("/list")
    public ReturnBean<List<ActBpmnRecord>> list(@RequestParam Map para) {
        return actBpmnRecordService.selectList(para);
    }


    /**
     * 添加流程部署表
     *
     * @param actBpmnRecord 流程部署表
     * @return
     */
//    @PreAuthorize("hasAuthority('act:actBpmnRecord:insert')")
    @PostMapping
    public ReturnBean insert(@RequestBody ActBpmnRecord actBpmnRecord) {
        actBpmnRecord.setIsDelopy(0);
        return actBpmnRecordService.insert(actBpmnRecord);

    }


    /**
     * 修改流程部署表
     *
     * @param actBpmnRecord 流程部署表
     * @return
     */

    @PreAuthorize("hasAuthority('act:actBpmnRecord:update')")
    @PutMapping
    public ReturnBean update(@RequestBody ActBpmnRecord actBpmnRecord) {
        actBpmnRecordService.update(actBpmnRecord);
        return ReturnBean.ok();
    }

    /**
     * 根据id获取流程部署表数据
     *
     * @param id 流程部署表 id
     * @return
     */
    @PreAuthorize("hasAnyAuthority('act:actBpmnRecord:see','act:actBpmnRecord:update')")
    @GetMapping("/{id}")
    public ReturnBean<ActBpmnRecord> update(@PathVariable Integer id) {
        ActBpmnRecord actBpmnRecord = actBpmnRecordService.selectEntityById(id);
        return ReturnBean.ok(actBpmnRecord);
    }

    /**
     * 删除流程部署表
     *
     * @param key 流程部署表 id
     * @return
     */


    @DeleteMapping("/{key}")
    public ReturnBean delete(@PathVariable String key) {
       actBpmnRecordService.delete(key);
        return ReturnBean.ok();
    }

    /**
     * 根据流程key查询
     *
     * @param key
     * @return
     */
    @GetMapping("findByKey/{key}")
    public ReturnBean<ActBpmnRecord> findByKey(@PathVariable("key") String key) {
        return actBpmnRecordService.findByKey(key);
    }


}
