package com.bp.act.service;


import com.bp.act.bean.ReturnBean;
import com.bp.act.bean.ReturnCode;
import com.bp.act.entity.ActBpmnRecord;
import com.bp.act.entity.ActKeyUrl;
import com.bp.act.mapper.ActBpmnRecordMapper;
import com.bp.act.mapper.ProcessMapper;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : 钟欣凯
 * @date: 2019/6/12 10:54
 */
@Service
public class ActDefiService {


    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private ProcessMapper processMapper;


    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private ActBpmnRecordMapper actBpmnRecordMapper;

    @Autowired
    private HttpServletRequest request;

    /**
     * 部署流程定义
     *
     * @param para 部署文件参数
     * @return 部署结果
     */
    public ReturnBean delopy(Map<String, Object> para) {
        String bpmnUrl = para.get("bpmnUrl").toString();
        String pngUrl = para.get("imageUrl").toString();
        InputStream bpmnio = getInputStreamByUrl(bpmnUrl);
        InputStream pngio = getInputStreamByUrl(pngUrl);
        repositoryService.createDeployment().addInputStream(para.get("bpmnName").toString(), bpmnio)
                .addInputStream(para.get("pngName").toString(), pngio).name(para.get("name").toString()).deploy();

        // 修改文件状态
        ActBpmnRecord actBpmnRecord = new ActBpmnRecord();
        actBpmnRecord.setId(Integer.valueOf(para.get("id").toString()));
        actBpmnRecord.setIsDelopy(1);
        actBpmnRecordMapper.update(actBpmnRecord);
        return ReturnBean.ok("流程部署成功");
    }

    /**
     * 删除流程定义
     *
     * @param key 流程定义的key
     * @return 删除结果
     */
    public ReturnBean delete(String key) {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().processDefinitionKey(key).list();
        for (ProcessDefinition pd :
                list) {
            String id = pd.getDeploymentId();
            repositoryService.deleteDeployment(id, true);
        }
        //删除自己表中的数据
        actBpmnRecordMapper.deleteByKey(key);
        return ReturnBean.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    public ReturnBean startProcess(Map<String, Object> map) {
        String remoteHost = request.getRemoteHost();
        String keyName = map.get("keyName").toString();
        map.remove("keyName");
        LinkedHashMap linkedHashMap = (LinkedHashMap) map.get("sysUser");
        identityService.setAuthenticatedUserId(linkedHashMap.get("id").toString());

        ProcessInstance processInstance = null;
        //将key 与 url关系插入到数据库当中
        ActKeyUrl actKeyUrl = new ActKeyUrl();
        actKeyUrl.setBaseUrl(map.get("baseUrl").toString());
        actKeyUrl.setKey(keyName);
        if (processMapper.selectKeyUrlByKey(keyName) == null) {
            processMapper.insertActKeyUrl(actKeyUrl);
        } else {
            actKeyUrl.setId(processMapper.selectKeyUrlByKey(keyName).getId());
            processMapper.updateActKeyUrl(actKeyUrl);
        }
        try {
            map.remove("sysUser");
            processInstance = runtimeService.startProcessInstanceByKey(keyName, map);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnBean.error("流程启动失败!");
        }
        return new ReturnBean(ReturnCode.OK.getCode(), "流程启动成功", processInstance.getId());
    }

    private static InputStream getInputStreamByUrl(String strUrl) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(20 * 1000);
            final ByteArrayOutputStream output = new ByteArrayOutputStream();
            IOUtils.copy(conn.getInputStream(), output);
            return new ByteArrayInputStream(output.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
