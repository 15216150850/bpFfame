package com.bp.act.service;

import com.bp.act.bean.ReturnBean;
import com.bp.act.contants.SysConstant;
import com.bp.act.entity.ActBpmnRecord;
import com.bp.act.mapper.ActBpmnRecordMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 钟欣凯
 * @version 1.0
 * @Description: 流程部署表服务层
 * @date 2019年06月13日
 */
@Service
public class ActBpmnRecordService {

    @Resource
    private ActBpmnRecordMapper actBpmnRecordMapper;
    @Autowired
    private RepositoryService repositoryService;


    public ReturnBean<ActBpmnRecord> findByKey(String key) {
        ActBpmnRecord byKey = actBpmnRecordMapper.findByKey(key);
        return ReturnBean.ok(byKey);
    }

    public ReturnBean<List<ActBpmnRecord>> selectList(Map para) {


        if (para != null && para.get("page") != null) {
            int page = Integer.parseInt(para.get(SysConstant.PAGE).toString());
            int limit = Integer.parseInt(para.get(SysConstant.LIMIT).toString());
            PageHelper.startPage(page, limit);
            List<Map<String, Object>> list = actBpmnRecordMapper.selectList(para);
            PageInfo<Map> pageInfo = new PageInfo(list);
            return ReturnBean.list(list, pageInfo.getTotal());
        } else {

            List<Map<String, Object>> list = actBpmnRecordMapper.selectList(para);
            return ReturnBean.list(list, (long) list.size());
        }
    }

    public ReturnBean insert(ActBpmnRecord actBpmnRecord) {
        ActBpmnRecord byKey = actBpmnRecordMapper.findByKey(actBpmnRecord.getFlowKey());
        if (byKey!=null){
            return ReturnBean.error("该流程key已存在");
        }
        actBpmnRecordMapper.insert(actBpmnRecord);
        return ReturnBean.ok();
    }

    public void update(ActBpmnRecord actBpmnRecord) {
        actBpmnRecordMapper.update(actBpmnRecord);
    }

    public ActBpmnRecord selectEntityById(Integer id) {
        return actBpmnRecordMapper.selectEntityById(id);
    }

    public void deleteByIds(String ids) {

    }

    public void delete(String key) {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().processDefinitionKey(key).list();
        for (ProcessDefinition pd :
                list) {
            String id = pd.getDeploymentId();
            repositoryService.deleteDeployment(id, true);
        }
        //删除自己表中的数据
        actBpmnRecordMapper.deleteByKey(key);
    }
}
