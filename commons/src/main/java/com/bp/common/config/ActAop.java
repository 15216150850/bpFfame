package com.bp.common.config;

import com.alibaba.fastjson.JSON;
import com.bp.common.bean.ReturnBean;
import com.bp.common.entity.FlowSixSys;
import com.bp.common.utils.Common;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Aspect
public class ActAop {


    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 工作流插入业务数据后执行的业务逻辑
     * @param joinPoint
     */
    @AfterReturning(value = "execution(* com.bp.common.base.BaseServiceImplByAct.insert(..))",returning = "rvt")
    public void actInsert(JoinPoint joinPoint,Object rvt){
        //插入流程总表
        String sysType = Common.getObjStr(request.getAttribute("sysType"));
        String tableName = Common.getObjStr(request.getAttribute("tableName"));
        FlowSixSys flowSixSys = new FlowSixSys();
        flowSixSys.setSysType(Common.getObjStr(sysType));
        flowSixSys.setBusiUuid(Common.getObjStr(rvt));
        flowSixSys.setTableName(Common.getObjStr(tableName));
        HttpHeaders requestHeaders = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        requestHeaders.setContentType(type);
        HttpEntity<String> requestEntity = new HttpEntity<>(JSON.toJSONString(flowSixSys), requestHeaders);
        restTemplate.
                postForObject("http://sixsys/api/flowSixSys/insertFlowSixSys",

                        requestEntity, ReturnBean.class);
    }
    /**
     * 工作流插入业务数据后执行的业务逻辑
     * @param joinPoint
     */
    @AfterReturning(value = "execution(* com.bp.common.base.BaseServiceImplByAct.delete*(..))")
    public void actDelete(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        String uuid = Common.getObjStr(args[0]);
        restTemplate.getForObject("http://sixsys/api/flowSixSys/deleteFlowSixSysByBusiUuid?uuid="+uuid, ReturnBean.class);
    }
}
