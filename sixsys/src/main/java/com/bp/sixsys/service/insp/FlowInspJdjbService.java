package com.bp.sixsys.service.insp;

import com.bp.common.base.BaseMapperUUID;
import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.constants.FlowState;
import com.bp.common.constants.SysConstant;
import com.bp.common.dto.ActRollBackEntity;
import com.bp.common.utils.IdUtils;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.client.ActClient;
import com.bp.sixsys.client.FileStoreClient;
import com.bp.sixsys.client.SysClient;
import com.bp.sixsys.mapper.insp.FlowInspJdjbMapper;
import com.bp.sixsys.po.insp.FlowInspJdjb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author guoxiangxu
 * @version 1.0
 * @Description: 监督举报服务层
 * @date 2019年10月12日
 */
@Service
public class FlowInspJdjbService extends BaseServiceImplByAct<FlowInspJdjbMapper, FlowInspJdjb> {

    @Resource
    private FlowInspJdjbMapper flowInspJdjbMapper;

    @Autowired
    private FileStoreClient fileStoreClient;

    @Autowired
    private SysClient sysClient;

    @Autowired
    private ActClient actClient;

    @Override
    public BaseMapperUUID<FlowInspJdjb> getMapper() {
        request.setAttribute("sysType","insp");
        request.setAttribute("tableName","flow_insp_jdjb");
        return flowInspJdjbMapper;
    }


    /**
     * 重写新增方法
     *
     * @param flowInspJdjb
     */

    @Override
    public String insert(FlowInspJdjb flowInspJdjb) {

        flowInspJdjb.setFlowState(0);
        flowInspJdjb.setFlowSn(sysClient.getNumberFlow("ACTIVITI_SN").msg);
        flowInspJdjb.setDocState("新建");

        flowInspJdjb.setUuid(IdUtils.getUuid());
        flowInspJdjb.setInserter(null == flowInspJdjb.getInserter() ? UserUtils.getCurrentUser() == null ? 0 : (UserUtils.getCurrentUser().getId()) : flowInspJdjb.getInserter());
        flowInspJdjb.setInsertTime(new Date());

        if (!"".equals(flowInspJdjb.getFj()) && flowInspJdjb.getFj() != null) {
            fileStoreClient.updateFileStatus(flowInspJdjb.getFj());
        }
        return super.insert(flowInspJdjb);

    }

    /**
     * 重写修改方法
     *
     * @param flowInspJdjb
     */
    @Override
    public void update(FlowInspJdjb flowInspJdjb) {
        if (flowInspJdjb.getFj() != null && !"".equals(flowInspJdjb.getFj())) {
            fileStoreClient.updateFileStatus(flowInspJdjb.getFj());
        }
        //!!!!!!
        flowInspJdjb.setUpdater(UserUtils.getCurrentUser() == null ? 0 : (UserUtils.getCurrentUser().getId()));
        flowInspJdjb.setUpdateTime(new Date());
        super.update(flowInspJdjb);

    }

    /**
     * 提交审核
     *
     * @param ids
     * @return
     */
    public ReturnBean submit(String ids) {
        Map<String, Object> paraMap = new HashMap<>(12);
        //		中队
        paraMap.put("zdId", UserUtils.getCurrentUser().getId() + ",1");
        paraMap.put("sldId", sysClient.findFlowRoleByCode("017").data.getUserIds() + ",1");
        paraMap.put("baseUrl", "sixsys/flowInspJdjb");
        for (String id : ids.split(SysConstant.COMMA)) {
//            paraMap.put("ddId", sysClient.findFlowRoleByCode("007").data.getUserIds() + ",1");
            // 监督问责参数
            paraMap.put("tableName","flow_insp_jdjb");
            paraMap.put("sysType","insp");
            this.startProcess(id, "flowInspJdjb", paraMap);
            //将第一个节点的任务办理完
            String pid = getMapper().selectById(id).getPid();
            String taskId = actClient.findStartTaskId(UserUtils.getCurrentUser().getId(), pid).msg;
            Map<String, Object> para = new HashMap<>(12);
            para.put("taskId", taskId);
            para.put("pId", pid);
            para.put("pKey", "flowInspJdjb");
            para.put("msg", "提交");
            para.put("comment", "默认提交");
            System.out.println(para);
            actClient.handleStartTask(para);
        }
        return ReturnBean.ok("流程启动成功");

    }


    @Override
    public void doFinishBusiness(ActRollBackEntity actRollBackEntity) {

        FlowInspJdjb flowInspJdjb = super.findByPid(actRollBackEntity.getPid());
        String msg = actRollBackEntity.getMsg();
        if ("领导处理".equals(actRollBackEntity.getTaskName()) && "已阅".equals(msg)) {
            flowInspJdjb.setFlowState(FlowState.PASSED.getState()).setDocState(SysConstant.docState.finish.getDocState());
            super.update(flowInspJdjb);
        }
    }

    @Override
    public void taskComplete(ActRollBackEntity actRollBackEntity) {

    }


}
