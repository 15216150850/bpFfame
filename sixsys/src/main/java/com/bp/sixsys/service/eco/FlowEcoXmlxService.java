package com.bp.sixsys.service.eco;
import com.bp.common.base.BaseMapperUUID;
import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.constants.FlowState;
import com.bp.common.constants.SysConstant;
import com.bp.common.dto.ActRollBackEntity;
import com.bp.common.entity.SysUser;
import com.bp.common.utils.IdUtils;
import com.bp.common.utils.UserIdUtils;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.client.ActClient;
import com.bp.sixsys.client.FileStoreClient;
import com.bp.sixsys.client.SysClient;
import com.bp.sixsys.mapper.eco.GovXmlxMapper;
import com.bp.sixsys.po.BaseDepartment;
import com.bp.sixsys.po.FlowRole;
import com.bp.sixsys.po.eco.FlowEcoXmlx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author guoxiangxu
 * @version 1.0
 * @Description: gov_xmlx服务层
 * @date 2019年10月10日
 */
@Service
public class FlowEcoXmlxService extends BaseServiceImplByAct<GovXmlxMapper, FlowEcoXmlx> {

    @Resource
    private GovXmlxMapper flowEcoXmlxMapper;

    @Autowired
    private FileStoreClient fileStoreClient;

    @Autowired
    private SysClient sysClient;

    @Autowired
    private ActClient actClient;


    @Override
    public BaseMapperUUID<FlowEcoXmlx> getMapper() {
        request.setAttribute("sysType","eco");
        request.setAttribute("tableName","flow_eco_xmlx");
        return flowEcoXmlxMapper;
    }

    @Override
    public String insert(FlowEcoXmlx govXmlx) {

        govXmlx.setFlowState(0);
        govXmlx.setFlowSn(sysClient.getNumberFlow("ACTIVITI_SN").msg);
        govXmlx.setDocState("新建");

        govXmlx.setUuid(IdUtils.getUuid());

        govXmlx.setInserter(null == govXmlx.getInserter() ? UserUtils.getCurrentUser() == null ? 0 : (UserUtils.getCurrentUser().getId()) : govXmlx.getInserter());
        govXmlx.setInsertTime(new Date());

        if (!"".equals(govXmlx.getFj()) && govXmlx.getFj() != null) {
            fileStoreClient.updateFileStatus(govXmlx.getFj());
        }
        return super.insert(govXmlx);

    }

    /**
     * 重写修改方法
     *
     * @param govXmlx
     */
    @Override
    public void update(FlowEcoXmlx govXmlx) {
        if (govXmlx.getFj() != null && !"".equals(govXmlx.getFj())) {
            fileStoreClient.updateFileStatus(govXmlx.getFj());
        }
        govXmlx.setUpdater(UserUtils.getCurrentUser() == null ? 0 : (UserUtils.getCurrentUser().getId()));
        govXmlx.setUpdateTime(new Date());

        super.update(govXmlx);

    }


    /**
     * 提交审核
     *
     * @param ids
     * @return
     */
    //@Transactional(rollbackFor = Exception.class)
    public ReturnBean submit(String ids) {
        Map<String, Object> paraMap = new HashMap<>(12);
//		中队
        paraMap.put("zdId", UserUtils.getCurrentUser().getId() + ",1");
        paraMap.put("scjykId", sysClient.findFlowRoleByCode("028").data.getUserIds() + ",1");
        paraMap.put("sldId", sysClient.findFlowRoleByCode("017").data.getUserIds() + ",1");
        paraMap.put("bgsId", sysClient.findFlowRoleByCode("023").data.getUserIds() + ",1");
        paraMap.put("baseUrl", "sixsys/flowEcoXmlx");
        for (String id : ids.split(SysConstant.COMMA)) {
			FlowEcoXmlx flowEcoXmlx = getMapper().selectById(id);
            Map userMap = sysClient.findUserById(flowEcoXmlx.getInserter()).data;
            paraMap.put("ddId", this.getUserIds(userMap.get("organizationCode").toString()) + ",1");
            // 监督问责参数
            paraMap.put("tableName","flow_eco_xmlx");
            paraMap.put("sysType","eco");
            this.startProcess(id, "flowEcoXmlx", paraMap);
            //将第一个节点的任务办理完
            String pid = getMapper().selectById(id).getPid();
            String taskId = actClient.findStartTaskId(UserUtils.getCurrentUser().getId(), pid).msg;
            Map<String, Object> para = new HashMap<>(12);
            para.put("taskId", taskId);
            para.put("pId", pid);
            para.put("pKey", "flowEcoXmlx");
            para.put("msg", "提交");
            para.put("comment", "默认提交");
            System.out.println(para);
            actClient.handleStartTask(para);
        }
        return ReturnBean.ok("流程启动成功");

    }

    @Override
    public void doFinishBusiness(ActRollBackEntity actRollBackEntity) {
        FlowEcoXmlx govXmlx = super.findByPid(actRollBackEntity.getPid());
        String msg = actRollBackEntity.getMsg();
        if ("省局意见".equals(actRollBackEntity.getTaskName()) && "同意".equals(msg)) {
            govXmlx.setFlowState(FlowState.PASSED.getState()).setDocState(SysConstant.docState.finish.getDocState());
            super.update(govXmlx);
        }
    }

    /**
     * 流程节点回调
     *
     * @param actRollBackEntity
     */
    @Override
    public void taskComplete(ActRollBackEntity actRollBackEntity) {
        // 流程节点名称
        String taskName = actRollBackEntity.getTaskName();
        String status = actRollBackEntity.getMsg();
        // 实体Bean
        FlowEcoXmlx govXmlx = super.findByPid(actRollBackEntity.getPid());
        if (null != govXmlx) {
            // 办理进入手续
            if ("领导小组复核意见".equals(taskName)) {
                String xzxzcy = actRollBackEntity.getMap().get("xzxzcy").toString();
                govXmlx.setXzxzcy(xzxzcy);
                super.update(govXmlx);
            }

        }
    }

    /**
     * 获取用户的ID
     *
     * @param bmbm
     * @param
     * @return
     */
    private String getUserIds(String bmbm) {
        ReturnBean<FlowRole> byCode = sysClient.findFlowRoleByCode("007");
        String userIds = "," + byCode.data.getUserIds() + ",";
        ReturnBean<BaseDepartment> byBmbm = sysClient.findByBmbm(bmbm);
        List<SysUser> sysUsers;
        if (bmbm.length() > 10) {
            sysUsers = sysClient.findFlowRoleByUserIdsAndBmbm(userIds, byBmbm.data.getSjbmbm());
        } else {
            sysUsers = sysClient.findFlowRoleByUserIdsAndBmbm(userIds, bmbm);
        }
        return UserIdUtils.idSplit(UserIdUtils.getUserIdList(sysUsers));
    }
}



