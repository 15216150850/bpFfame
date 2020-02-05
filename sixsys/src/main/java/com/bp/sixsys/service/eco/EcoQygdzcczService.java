package com.bp.sixsys.service.eco;

import com.bp.common.anno.FileAnnotation;
import com.bp.common.base.BaseMapperUUID;
import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.constants.FlowState;
import com.bp.common.dto.ActRollBackEntity;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.client.ActClient;
import com.bp.sixsys.client.SysClient;
import com.bp.sixsys.mapper.eco.EcoQygdzcczMapper;
import com.bp.sixsys.po.eco.EcoQygdzccz;
import com.bp.sixsys.util.FlowUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yanjisheng
 * @version 1.0
 * @Description: 经济权力-企业固定资产处置服务层
 * @date 2019年10月09日
 */
@Service
public class EcoQygdzcczService extends BaseServiceImplByAct<EcoQygdzcczMapper, EcoQygdzccz> {

    @Resource
    private EcoQygdzcczMapper ecoQygdzcczMapper;

    @Autowired
    private SysClient sysClient;
    
    @Autowired
    private ActClient actClient;

    @Override
    public BaseMapperUUID<EcoQygdzccz> getMapper() {
    	request.setAttribute("tableName","flow_eco_qygdzccz");
        request.setAttribute("sysType","eco");
        return ecoQygdzcczMapper;
    }

    @FileAnnotation(paramName = "file")
    @Override
    public String insert(EcoQygdzccz entity) {
        entity.setActTitle(entity.getSxmc());
        entity.setFlowSn(sysClient.getNumberFlow("ACTIVITI_SN").msg);
        entity.setFlowState(FlowState.NOT_COMMITTED.getState());
        entity.setDocState("新建");
        return super.insert(entity);
    }

    @FileAnnotation(paramName = "file")
    @Override
    public void update(EcoQygdzccz entity) {
        entity.setActTitle(entity.getSxmc());
        entity.setFlowSn(sysClient.getNumberFlow("ACTIVITI_SN").msg);
        entity.setFlowState(FlowState.NOT_COMMITTED.getState());
        entity.setDocState("新建");
        super.update(entity);
    }

    public void submit(String id) {
        Map<String, Object> para = new HashMap<>(12);
        para.put("baseUrl", "sixsys/ecoQygdzccz");
        //监督问责参数
        para.put("tableName","flow_eco_qygdzccz");
        para.put("sysType","eco");
        //GovQygdzccz entity = this.selectEntityById(id);
        //申请人
        para.put("sqr", UserUtils.getCurrentUser().getId() + ",1");
        //TODO 以下人员留待修改
        //单位负责人
        para.put("dwfzr", FlowUtil.getChargeablePerson() + ",1");
        //物流中心
        para.put("wlzx", FlowUtil.getUserIdByFlowRole("wlzx") + ",1");
        //固定资产领导小组
        para.put("gdzcldxz", FlowUtil.getUserIdByFlowRole("gdzcldxz") + ",1");
        //材料归档
        para.put("clgd", FlowUtil.getUserIdByName("王桂芳") + ",1");
        //所党委意见
        para.put("sdwyj", FlowUtil.getUserIdByName("高朝晖") + ",1");
        super.startProcess(id, "ecoQygdzccz", para);
        Map<String, Object> para2 = new HashMap<>(12);
      //将第一个节点的任务办理完
        String pid = getMapper().selectById(id).getPid();
        String taskId = actClient.findStartTaskId(UserUtils.getCurrentUser().getId(), pid).msg;
        para2.put("taskId", taskId);
        para2.put("pId", pid);
        para2.put("pKey", "ecoQygdzccz");
        para2.put("msg", "提交");
        para2.put("comment", (getMapper().selectById(id).getYjsm()==null)?"":getMapper().selectById(id).getYjsm());
        actClient.handleStartTask(para2);
    }

    @Override
    public void doFinishBusiness(ActRollBackEntity actRollBackEntity) {
        EcoQygdzccz entity = super.findByPid(actRollBackEntity.getPid());
        if ("所党委意见".equals(actRollBackEntity.getTaskName()) && "同意".equals(actRollBackEntity.getMsg())) {
            entity.setFlowState(FlowState.PASSED.getState());
            super.update(entity);
        }
    }

    @Override
    public void taskComplete(ActRollBackEntity actRollBackEntity) {

    }

}
