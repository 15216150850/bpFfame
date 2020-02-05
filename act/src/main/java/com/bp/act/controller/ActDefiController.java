package com.bp.act.controller;


import com.bp.act.bean.ReturnBean;
import com.bp.act.service.ActDefiService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.Map;

/**
 * @author : 钟欣凯
 * @date: 2019/6/12 10:36
 */
@RestController
public class ActDefiController {


    @Autowired
    private ActDefiService actDefiService;

    @Autowired
    private TaskService taskService;

    /**
     * 部署流程定义
     *
     * @param para 部署文件参数
     * @return 部署结果
     */
    @GetMapping("delopy")
    public ReturnBean delopy(@RequestParam Map<String, Object> para) throws FileNotFoundException {

        return actDefiService.delopy(para);
    }

    /**
     * 删除流程定义
     *
     * @param key 流程部署ID
     * @return 删除结果
     */

    @DeleteMapping("delete/{key}")
    public ReturnBean delete(@PathVariable("key") String key) {
        return actDefiService.delete(key);
    }


    /**
     * 启动流程定义
     *
     * @param para 启动参数
     * @return 启动结果
     */

    @PostMapping("ingnore/startProcess")
    public ReturnBean startProcess(@RequestBody Map<String, Object> para) {
        return actDefiService.startProcess(para);
    }

    @GetMapping("ingnore/finishTest")
    public ReturnBean finishTest(String taskId) {
        taskService.complete(taskId);
        return ReturnBean.ok("办理完成");
    }
}
