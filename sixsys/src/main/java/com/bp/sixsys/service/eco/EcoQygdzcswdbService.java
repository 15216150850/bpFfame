package com.bp.sixsys.service.eco;

import com.bp.common.anno.FileAnnotation;
import com.bp.common.base.BaseMapperUUID;
import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.constants.FlowState;
import com.bp.common.dto.ActRollBackEntity;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.client.ActClient;
import com.bp.sixsys.client.SysClient;
import com.bp.sixsys.mapper.eco.EcoQygdzcswdbMapper;
import com.bp.sixsys.po.eco.EcoQygdzcswdb;
import com.bp.sixsys.util.FlowUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yanjisheng
 * @version 1.0
 * @Description: 经济权力-企业固定资产所外调拨服务层
 * @date 2019年10月08日
 */
@Service
public class EcoQygdzcswdbService extends BaseServiceImplByAct<EcoQygdzcswdbMapper, EcoQygdzcswdb> {

    @Resource
    private EcoQygdzcswdbMapper ecoQygdzcswdbMapper;

    @Autowired
    private SysClient sysClient;
    
    @Autowired
    private ActClient actClient;

    @Override
    public BaseMapperUUID<EcoQygdzcswdb> getMapper() {
    	request.setAttribute("tableName","flow_eco_qygdzcswdb");
        request.setAttribute("sysType","eco");
        return ecoQygdzcswdbMapper;
    }

    @FileAnnotation(paramName = "file")
    @Override
    public String insert(EcoQygdzcswdb entity) {
        entity.setActTitle(entity.getSxmc());
        entity.setFlowSn(sysClient.getNumberFlow("ACTIVITI_SN").msg);
        entity.setFlowState(FlowState.NOT_COMMITTED.getState());
        entity.setDocState("新建");
        return super.insert(entity);
    }

    @FileAnnotation(paramName = "file")
    @Override
    public void update(EcoQygdzcswdb entity) {
        entity.setActTitle(entity.getSxmc());
        entity.setFlowSn(sysClient.getNumberFlow("ACTIVITI_SN").msg);
        entity.setFlowState(FlowState.NOT_COMMITTED.getState());
        entity.setDocState("新建");
        super.update(entity);
    }
    
    public void submit(String id) {
        Map<String, Object> para = new HashMap<>(12);
        para.put("baseUrl", "sixsys/ecoQygdzcswdb");
        //监督问责参数
        para.put("tableName","flow_eco_qygdzcswdb");
        para.put("sysType","eco");
        //GovQygdzcswdb entity = this.selectEntityById(id);
        //申请人
        para.put("sqr", UserUtils.getCurrentUser().getId() + ",1");
        //TODO 以下人员留待修改
        //单位负责人
        para.put("dwfzr", FlowUtil.getChargeablePerson() + ",1");
        //物流中心
        para.put("wlzx", FlowUtil.getUserIdByFlowRole("wlzx") + ",1");
        //固定资产领导小组
        para.put("gdzcldxz", FlowUtil.getUserIdByFlowRole("gdzcldxz") + ",1");
        super.startProcess(id, "ecoQygdzcswdb", para);
        Map<String, Object> para2 = new HashMap<>(12);
      //将第一个节点的任务办理完
        String pid = getMapper().selectById(id).getPid();
        String taskId = actClient.findStartTaskId(UserUtils.getCurrentUser().getId(), pid).msg;
        para2.put("taskId", taskId);
        para2.put("pId", pid);
        para2.put("pKey", "ecoQygdzcswdb");
        para2.put("msg", "提交");
        para2.put("comment", (getMapper().selectById(id).getYjsm()==null)?"":getMapper().selectById(id).getYjsm());
        actClient.handleStartTask(para2);
    }


    @Override
    public void doFinishBusiness(ActRollBackEntity actRollBackEntity) {
        EcoQygdzcswdb entity = super.findByPid(actRollBackEntity.getPid());
        if ("固定资产领导小组".equals(actRollBackEntity.getTaskName()) && "同意".equals(actRollBackEntity.getMsg())) {
            entity.setFlowState(FlowState.PASSED.getState());
            super.update(entity);
        }
    }

    @Override
    public void taskComplete(ActRollBackEntity actRollBackEntity) {

    }

}
