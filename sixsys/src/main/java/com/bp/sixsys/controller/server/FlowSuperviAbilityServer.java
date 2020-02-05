package com.bp.sixsys.controller.server;

import com.bp.common.bean.ReturnBean;
import com.bp.sixsys.service.FlowSuperviAbilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlowSuperviAbilityServer {

    @Autowired
    private FlowSuperviAbilityService flowSuperviAbilityService;

    @GetMapping("api/flowSuperviAbility/insertSuperviAbility/{actTitle}")
    public String insertSuperviAbility(@PathVariable("actTitle") String actTitle){
        return flowSuperviAbilityService.insertSuperviAbility(actTitle);
    }
}
