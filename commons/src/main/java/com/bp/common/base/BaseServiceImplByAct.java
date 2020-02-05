package com.bp.common.base;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bp.common.bean.ReturnBean;
import com.bp.common.constants.ActivitiContants;
import com.bp.common.dto.ActRollBackEntity;
import com.bp.common.entity.FlowSixSys;
import com.bp.common.exception.BpException;
import com.bp.common.utils.Common;
import com.bp.common.utils.JwtUtil;
import com.bp.common.utils.UserUtils;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * ssaass
 *
 * @auther: 钟欣凯
 * @date: 2019/1/18 13:52
 */
public abstract class BaseServiceImplByAct<K extends BaseMapperUUID<T>, T extends BaseEntityUUID> extends BaseServiceImplUUID<K, T> {


    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    protected HttpServletRequest request;

    protected String sysType;

    protected String tableName;

    /**
     * 启动流程实例
     *
     * @param id      业务Id
     * @param keyName 流程定义key
     * @param paraMap 参数集合
     */
    @Transactional(rollbackFor = Exception.class)
    public void startProcess(String id, String keyName, Map<String, Object> paraMap) {
        T t = this.selectEntityById(id);
        Class clazz = t.getClass();
        Object data = null;
        try {
            Method mh = ReflectionUtils.findMethod(clazz, "getRemark");
            data = ReflectionUtils.invokeMethod(mh, t);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("反射找不到getRemark()方法");
            throw new BpException("反射找不到getRemark()方法");
        }
        Object actTitle = null;
        try {
            Method mh = ReflectionUtils.findMethod(clazz, "getActTitle");
            actTitle = ReflectionUtils.invokeMethod(mh, t);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("反射找不到getActTitle方法");
            throw new BpException("反射找不到getActTitle方法()方法");
        }

        //流程编号
        Object flowSn = null;
        try {
            Method mh = ReflectionUtils.findMethod(clazz, "getFlowSn");
            flowSn = ReflectionUtils.invokeMethod(mh, t);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("反射找不到getFlowSn()方法");
            throw new BpException("反射找不到getFlowSn()方法");
        }
        Map<String, Object> variables = new HashMap<>();
        variables.put("descripiton", data);
        variables.put("flowSn", flowSn);
        variables.putAll(paraMap);
        variables.put("keyName", keyName);
        variables.put("actTitle", actTitle);
        variables.put("startUser", UserUtils.getCurrentUser() == null ? "" : UserUtils.getCurrentUser().getUsername());
        variables.put("startUserName", UserUtils.getCurrentUser() == null ? "" : UserUtils.getCurrentUser().getName());
        variables.put("sysUser", UserUtils.getCurrentUser());
        ///调用流程服务启动流程实例
        HttpHeaders requestHeaders = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        requestHeaders.setContentType(type);
        String s = JSON.toJSONString(variables);
        HttpEntity<String> requestEntity = new HttpEntity<String>(s, requestHeaders);
        ReturnBean returnBean = restTemplate.
                postForObject(ActivitiContants.BASE_URL + "/ingnore/startProcess/",

                        requestEntity, ReturnBean.class);
        try {
            Method mh = ReflectionUtils.findMethod(clazz, "setPid",
                    String.class);
            ReflectionUtils.invokeMethod(mh, t, returnBean.data);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("反射找不到setPid()方法");
            throw new BpException("反射找不到setPid()方法");
        }
        //修改单据状态
        try {
            Method mh = ReflectionUtils.findMethod(clazz, "setDocState",
                    String.class);
            ReflectionUtils.invokeMethod(mh, t, ActivitiContants.FLOW_HANDLE);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("反射找不到setDocState()方法");
            throw new BpException("反射找不到setDocState()方法");
        }
        //修改流程状态
        try {
            Method mh = ReflectionUtils.findMethod(clazz, "setFlowState",
                    Integer.class);
            ReflectionUtils.invokeMethod(mh, t, 1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("反射找不到setFlowState()方法");
            throw new BpException("反射找不到setFlowState()方法");
        }
        this.update(t);
        //插入总流程记录表
        FlowSixSys flowSixSys = new FlowSixSys();
//        if (StringUtils.isEmpty(paraMap.get("baseUrl"))) {
//            throw new RuntimeException("基础链接baseUrl不能为空");
//        }
//        if (StringUtils.isEmpty(paraMap.get("sysType"))) {
//            throw new RuntimeException("体系种类sysType不能为空");
//        }
//        if (StringUtils.isEmpty(paraMap.get("tableName"))) {
//            throw new RuntimeException("业务表名tableName不能为空");
//        }
        String isAbility = request.getParameter("isAbility");
        flowSixSys.setIsAbility(isAbility);
        flowSixSys.setBusiPid(Common.getObjStr(returnBean.data));
        flowSixSys.setBusiUuid(id);
        flowSixSys.setBaseUrl(Common.getObjStr(paraMap.get("baseUrl")));
        Method mh = ReflectionUtils.findMethod(clazz, "getActTitle");
        Object o = ReflectionUtils.invokeMethod(mh, t);
        flowSixSys.setActTitle(Common.getObjStr(o));
        requestEntity = new HttpEntity<String>(JSON.toJSONString(flowSixSys), requestHeaders);
        restTemplate.
                postForObject("http://sixsys/api/flowSixSys/updateFlowSixSysByBusiUuid",
                        requestEntity, ReturnBean.class);
    }

    public void updateWorkFlowStateByPid(String pid, String docState, Integer flowState) {

        if ("null".equals(docState)){
            docState = null;
        }
        T t = setPara(pid, docState, flowState);
        updateStateByPid(t);
    }

    /**
     * 设置实体类参数
     *
     * @param pid
     * @param docState
     * @param flowState
     * @return
     */
    private T setPara(String pid, String docState, Integer flowState) {
        Type superClass = getClass().getGenericSuperclass();
        Type type = ((ParameterizedType) superClass).getActualTypeArguments()[1];
        Class<T> entityClass = (Class<T>) type;
        try {
            T t = entityClass.newInstance();
            Method mh = ReflectionUtils.findMethod(t.getClass(), "setPid",
                    String.class);
            ReflectionUtils.invokeMethod(mh, t, pid);
            mh = ReflectionUtils.findMethod(t.getClass(), "setDocState",
                    String.class);
            if (!StringUtils.isEmpty(docState)) {
                ReflectionUtils.invokeMethod(mh, t, docState);
            }
            mh = ReflectionUtils.findMethod(t.getClass(), "setFlowState",
                    Integer.class);
            if (!StringUtils.isEmpty(flowState)) {
                ReflectionUtils.invokeMethod(mh, t, flowState);
            }

            return t;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("反射异常，请检查实体类参数");
        }
    }



    public abstract void doFinishBusiness(ActRollBackEntity actRollBackEntity);

    public abstract void taskComplete(ActRollBackEntity actRollBackEntity);

    public void taskCreate(ActRollBackEntity actRollBackEntity) {
        if (!"close".equals(actRollBackEntity.getMsg())){
            T t = setPara(actRollBackEntity.getPid(), actRollBackEntity.getTaskName(), null);
            updateStateByPid(t);
        }

    }

    public void updateStateByPid(T t) {
        Method mh = ReflectionUtils.findMethod(t.getClass(), "getPid");
        Object o = ReflectionUtils.invokeMethod(mh, t);
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid", o);
        getMapper().update(t, queryWrapper);
    }

    /**
     * 根據流程實例ID查詢
     *
     * @param pid
     * @return
     */
    public T findByPid(String pid) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid", pid);
        return getMapper().selectOne(queryWrapper);
    }

    /**
     * 事前逻辑
     *
     * @param t
     */
    protected void beforehand(T t) {
        Method mh = ReflectionUtils.findMethod(t.getClass(), "getActTitle");
        Object o = ReflectionUtils.invokeMethod(mh, t);
        String  uuid = restTemplate.
                getForObject("http://sixsys/api/flowSuperviAbility/insertSuperviAbility/" + o, String.class);
        FlowSixSys flowSixSys = new FlowSixSys();
        flowSixSys.setSupseUuid(uuid);
        mh = ReflectionUtils.findMethod(t.getClass(), "getPid");
        assert mh != null;
        Object pid = ReflectionUtils.invokeMethod(mh, t);
        flowSixSys.setBusiPid(Common.getObjStr(pid));
        flowSixSys.setAbilityState("1");
        HttpHeaders requestHeaders = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        requestHeaders.setContentType(type);
        String s = JSON.toJSONString(flowSixSys);
        HttpEntity<String> requestEntity = new HttpEntity<String>(s, requestHeaders);
        restTemplate.
                postForObject("http://sixsys/api/flowSixSys/updateFlowSixSysByPid",
                        requestEntity, ReturnBean.class);
    }

    /**
     * 事中
     *
     * @param t
     */
    protected void inFact(T t) {
        Method mh = ReflectionUtils.findMethod(t.getClass(), "getPid");
        assert mh != null;
        Object pid = ReflectionUtils.invokeMethod(mh, t);
        FlowSixSys flowSixSys = new FlowSixSys();
        flowSixSys.setBusiPid(Common.getObjStr(pid));
        flowSixSys.setAbilityState("2");
        HttpHeaders requestHeaders = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        requestHeaders.setContentType(type);
        String s = JSON.toJSONString(flowSixSys);
        HttpEntity<String> requestEntity = new HttpEntity<String>(s, requestHeaders);
        restTemplate.
                postForObject("http://sixsys/api/flowSixSys/updateFlowSixSysByPid",
                        requestEntity, ReturnBean.class);
    }

    protected void afterwards(T t) {
        Method mh = ReflectionUtils.findMethod(t.getClass(), "getPid");
        assert mh != null;
        Object pid = ReflectionUtils.invokeMethod(mh, t);
        FlowSixSys flowSixSys = new FlowSixSys();
        flowSixSys.setBusiPid(Common.getObjStr(pid));
        flowSixSys.setAbilityState("3");
        HttpHeaders requestHeaders = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        requestHeaders.setContentType(type);
        String s = JSON.toJSONString(flowSixSys);
        HttpEntity<String> requestEntity = new HttpEntity<String>(s, requestHeaders);
        restTemplate.
                postForObject("http://sixsys/api/flowSixSys/updateFlowSixSysByPid",
                        requestEntity, ReturnBean.class);
    }

    @Override
    public void delete(String uuid) {
        super.delete(uuid);
    }

    @Override
    public void deleteByIds(String idsStr) {
        super.deleteByIds(idsStr);
    }

    @Override
    public String insert(T entity) {
        return super.insert(entity);
    }
}
