package com.bp.act.controller;


import com.bp.act.bean.ReturnBean;
import com.bp.act.entity.process.ActUserTask;
import com.bp.act.service.ProcessService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 设定流程办理人控制层
 *
 * @author 钟欣凯
 */
@RestController
public class ActUserTaskController {

    @Autowired
    private ProcessService processService;

    @Autowired
    private RepositoryService repositoryService;

    /**
     * 查询所有的最新版本流程定义
     *
     * @param map
     * @return
     */
    @PostMapping("/workflow/process/selectListProef")
    @ResponseBody
    public ReturnBean<Map<String, Object>> selectListProef(@RequestParam Map map) {
        return processService.selectListProef(map);
    }


    /**
     * 初始化流程定义
     *
     * @param processDefinitionId
     * @return
     */
    @PostMapping("workflow/process/loadProcess")
    @ResponseBody
    public ReturnBean loadProcess(String processDefinitionId) {
        try {
            ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processDefinitionId);
            if (processDefinition == null) {
                return ReturnBean.error("初始化失败");
            }
            //读取节点信息保存到usertask表
            processService.insertSingleActivitiInfo(processDefinition);
            return ReturnBean.ok("初始化成功");
        } catch (Exception e) {
            throw new RuntimeException("初始化失败");
        }
    }

    /**
     * 根据流程key查找
     *
     * @param key
     * @return
     */
    @PostMapping("/workflow/process/findByKey")
    public ReturnBean<List<ActUserTask>> findByKey(String key) {
        return processService.findUserTaskByKey(key);
    }

    /**
     * 修改任务办理人
     *
     * @param actUserTasks
     * @return
     */
    @PostMapping("workflow/process/updateUserId")
    public ReturnBean updateUserId(@RequestBody List<ActUserTask> actUserTasks) {
        return processService.updateUserId(actUserTasks);
    }

    /**
     * 根据流程定义key和任务key查询
     * @param pey
     * @param taskKey
     * @return
     */
    @GetMapping("findByPkeyAndTaskKey/{pKey}/{taskKey}")
    public ReturnBean<ActUserTask> findByPkeyAndTaskKey(@PathVariable("pKey") String pey,@PathVariable("taskKey") String taskKey){
       return processService.findUserTaskByPkeyAndTaskKey(pey,taskKey);
    }
}
