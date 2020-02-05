package com.bp.sixsys.service.eco;
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
import com.bp.sixsys.mapper.eco.FlowEcoZyzlMapper;
import com.bp.sixsys.po.eco.FlowEcoZyzl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author guoxiangxu
 * @version 1.0
 * @Description: 资源租赁审批服务层
 * @date 2019年10月12日
 */
@Service
public class FlowEcoZyzlService extends BaseServiceImplByAct<FlowEcoZyzlMapper, FlowEcoZyzl> {

    @Resource
    private FlowEcoZyzlMapper flowEcoZyzlMapper;

    @Autowired
    private FileStoreClient fileStoreClient;

    @Autowired
    private SysClient sysClient;

    @Autowired
    private ActClient actClient;


    @Override
    public BaseMapperUUID<FlowEcoZyzl> getMapper() {
        request.setAttribute("sysType","eco");
        request.setAttribute("tableName","flow_eco_zyzl");
        return flowEcoZyzlMapper;
    }

    /**
     * 重写新增方法
     *
     * @param govZyzl
     */

    @Override
    public String insert(FlowEcoZyzl govZyzl) {

        govZyzl.setFlowState(0);
        govZyzl.setFlowSn(sysClient.getNumberFlow("ACTIVITI_SN").msg);
        govZyzl.setDocState("新建");

        govZyzl.setUuid(IdUtils.getUuid());

        govZyzl.setInserter(null == govZyzl.getInserter() ? UserUtils.getCurrentUser() == null ? 0 : (UserUtils.getCurrentUser().getId()) : govZyzl.getInserter());
        govZyzl.setInsertTime(new Date());

        if (!"".equals(govZyzl.getFj()) && govZyzl.getFj() != null) {
            fileStoreClient.updateFileStatus(govZyzl.getFj());
        }
        return super.insert(govZyzl);

    }

    /**
     * 重写修改方法
     *
     * @param govZyzl
     */
    @Override
    public void update(FlowEcoZyzl govZyzl) {
        if (govZyzl.getFj() != null && !"".equals(govZyzl.getFj())) {
            fileStoreClient.updateFileStatus(govZyzl.getFj());
        }
        govZyzl.setUpdater(UserUtils.getCurrentUser() == null ? 0 : (UserUtils.getCurrentUser().getId()));
        govZyzl.setUpdateTime(new Date());

        super.update(govZyzl);

    }


    /**
     * 提交审核
     *
     * @param ids
     * @return
     */
    public ReturnBean submit(String ids) {
        Map<String, Object> paraMap = new HashMap<>(12);
        //中队
        paraMap.put("zdId", UserUtils.getCurrentUser().getId() + ",1");
        paraMap.put("zcglkId", sysClient.findFlowRoleByCode("031").data.getUserIds() + ",1");
        paraMap.put("bgsId", sysClient.findFlowRoleByCode("023").data.getUserIds() + ",1");
        paraMap.put("sldId", sysClient.findFlowRoleByCode("017").data.getUserIds() + ",1");
        paraMap.put("baseUrl", "sixsys/flowEcoZyzl");
        for (String id : ids.split(SysConstant.COMMA)) {
            //GovWlrycljc govWlrycljcjdglsp = getMapper().selectById(id);
            //paraMap.put("ddId",sysClient.findFlowRoleByCode("007").data.getUserIds() + ",1")
              // 监督问责参数
            paraMap.put("tableName","flow_eco_zyzl");
            paraMap.put("sysType","eco");;
            this.startProcess(id, "flowEcoZyzl", paraMap);
            //将第一个节点的任务办理完
            String pid = getMapper().selectById(id).getPid();
            String taskId = actClient.findStartTaskId(UserUtils.getCurrentUser().getId(), pid).msg;
            Map<String, Object> para = new HashMap<>(12);
            para.put("taskId", taskId);
            para.put("pId", pid);
            para.put("pKey", "flowEcoZyzl");
            para.put("msg", "提交");
            para.put("comment", "默认提交");
            System.out.println(para);
            actClient.handleStartTask(para);
        }
        return ReturnBean.ok("流程启动成功");

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
        FlowEcoZyzl govZyzl = super.findByPid(actRollBackEntity.getPid());
        if (null != govZyzl) {
            // 资产管理科研究意见
            if ("资产管理科研究意见".equals(taskName)) {
                String xzxzcy = actRollBackEntity.getMap().get("xzxzcy").toString();
                govZyzl.setXzxzcy(xzxzcy);
                super.update(govZyzl);
            }

        }
    }


    @Override
    public void doFinishBusiness(ActRollBackEntity actRollBackEntity) {

        FlowEcoZyzl govZyzl = super.findByPid(actRollBackEntity.getPid());
        String msg = actRollBackEntity.getMsg();
        if ("所党委意见".equals(actRollBackEntity.getTaskName()) && "同意".equals(msg)) {
            govZyzl.setFlowState(FlowState.PASSED.getState()).setDocState(SysConstant.docState.finish.getDocState());
            super.update(govZyzl);
        }
    }


}
