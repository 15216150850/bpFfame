package com.bp.act.controller;



import com.bp.act.bean.ReturnBean;
import com.bp.act.entity.ActFinishTask;



import com.bp.act.service.ActFinishTaskService;

import com.bp.act.utils.UserUtils;

import org.springframework.stereotype.Controller;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Map;
import java.util.List;
import javax.annotation.Resource;


/**
 * @author 钟欣凯
 * @version 1.0
 * @Description: 个人已办理的任务控制层
 * @date 2019年09月28日
 */
@RestController
@RequestMapping(value = "/actFinishTask")
@Api(description = "个人已办理的任务")
public class ActFinishTaskController {

    @Resource
    private ActFinishTaskService actFinishTaskService;

    /**
     * 获取个人已办理的任务管理页list
     *
     * @return
     */
    @PreAuthorize("hasAuthority('act:actFinishTask:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取个人已办理的任务list")
    public ReturnBean<List<ActFinishTask>> list(@RequestParam Map para) {
        para.put("userId", UserUtils.getCurrentUser().getId());
        return actFinishTaskService.selectList(para);
    }


    /**
     * 添加个人已办理的任务
     *
     * @param actFinishTask 个人已办理的任务
     * @return
     */

    @PreAuthorize("hasAuthority('act:actFinishTask:insert')")
    @PostMapping
    @ApiOperation(value = "添加个人已办理的任务")
    public ReturnBean insert(@RequestBody ActFinishTask actFinishTask) {
        actFinishTaskService.insert(actFinishTask);
        return ReturnBean.ok();
    }


    /**
     * 修改个人已办理的任务
     *
     * @param actFinishTask 个人已办理的任务
     * @return
     */

    @PreAuthorize("hasAuthority('act:actFinishTask:update')")
    @PutMapping
    @ApiOperation(value = "修改个人已办理的任务")
    public ReturnBean update(@RequestBody ActFinishTask actFinishTask) {
        actFinishTaskService.update(actFinishTask);
        return ReturnBean.ok();
    }

    /**
     * 根据uuid获取个人已办理的任务数据
     *
     * @param id 个人已办理的任务 uuid
     * @return
     */
    @PreAuthorize("hasAnyAuthority('act:actFinishTask:see','act:actFinishTask:update')")
    @GetMapping("/{id}")
    @ApiOperation(value = "根据uuid获取个人已办理的任务数据")
    public ReturnBean<ActFinishTask> getByUuid(@PathVariable Integer id) {
        ActFinishTask actFinishTask = actFinishTaskService.selectEntityById(id);
        return ReturnBean.ok(actFinishTask);
    }

    /**
     * 删除个人已办理的任务
     *
     * @param ids 个人已办理的任务 ids
     * @return
     */

    @PreAuthorize("hasAuthority('act:actFinishTask:delete')")
    @DeleteMapping
    @ApiOperation(value = "根据uuid删除个人已办理的任务数据")
    public ReturnBean delete(@RequestParam String ids) {
        if (ids.contains(",")) {
            actFinishTaskService.deleteByIds(ids);
        } else {
            actFinishTaskService.delete(Integer.valueOf(ids));
        }
        return ReturnBean.ok();
    }

    /**
     * 根据流程Pid获取流程历史审批记录
     *
     * @param pid
     * @return
     */
    @GetMapping("workflow/actFinishTask/getRecordByPid/{pid}")
    @ResponseBody
    public ReturnBean<List<ActFinishTask>> getRecordByPid(@PathVariable("pid") String pid) {
        List<ActFinishTask> list = actFinishTaskService.getRecordByPid(pid);
        return ReturnBean.ok(list);
    }

    /**
     * 获取流程的审批节点数据,取得每个节点最新的意见信息:用于回显流程各个部门的审批意见
     *
     * @param pid 流程pid
     * @return 返回该流程所有节点最新的审批意见
     */
    @GetMapping("workflow/actFinishTask/selectFinallyTaskByPid/{pid}")
    @ResponseBody
    public ReturnBean<List<ActFinishTask>> selectFinallyTaskByPid(@PathVariable("pid") String pid) {
        return actFinishTaskService.selectFinallyTaskByPid(pid);
    }


}
