package com.bp.act.controller.server;

import com.bp.act.utils.TypeConversionUtils;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
public class ProinstansServer {

    @Autowired
    private HistoryService historyService;

    /**
     *  根据流程实例ID获取任务信息
     * @param pid  流程实例ID
     * @return 获取结果
     */
    @GetMapping("ingnore/getProInsStartInfoByPid/{pid}")
    public Map  getProInsStartInfoByPid(@PathVariable("pid") String pid) {
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(pid).singleResult();

        return TypeConversionUtils.objToMap(historicProcessInstance);
    }
}
