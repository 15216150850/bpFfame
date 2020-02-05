package com.bp.act.controller.server;



import com.bp.act.bean.ReturnBean;
import com.bp.act.service.MyTaskService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author 钟欣凯
 * 任务对外接口
 */

@RestController
public class TaskServer {


    @Autowired
    private MyTaskService myTaskService;

    @GetMapping("api/task/findStartTaskId/{userId}/{pid}")
    public ReturnBean<String> findStartTaskId(@PathVariable("userId") Integer userId, @PathVariable("pid") String pid
    ) {
        return myTaskService.findStartTaskId(userId, pid);

    }

    /**
     * @param para
     * @return
     */
    @PostMapping(value = "api/task/handleStartTask")
    public ReturnBean handleStartTask(@RequestParam Map<String, Object> para) {
        return myTaskService.handleTask(para);
    }
}
