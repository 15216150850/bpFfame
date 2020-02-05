package com.bp.sixsys.client;

import com.bp.common.bean.ReturnBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 流程客户端
 *
 * @author 钟欣凯
 */
@FeignClient(name = "act")
public interface ActClient {

    @GetMapping("api/task/findStartTaskId/{userId}/{pid}")
    public ReturnBean<String> findStartTaskId(@PathVariable("userId") Integer userId, @PathVariable("pid") String pid);


    @PostMapping("api/task/handleStartTask")
    public ReturnBean handleStartTask(@RequestParam Map<String, Object> para);
    /**
     *  根据流程实例ID获取任务发起人
     * @param pid  流程实例ID
     * @return 获取结果
     */
    @GetMapping("ingnore/getProInsStartInfoByPid/{pid}")
    public Map getProInsStartInfoByPid(@PathVariable("pid") String pid);

}
