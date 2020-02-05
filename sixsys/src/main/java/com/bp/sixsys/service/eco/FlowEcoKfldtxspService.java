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
import com.bp.sixsys.client.SysClient;
import com.bp.sixsys.mapper.eco.GovKfldtxspMapper;
import com.bp.sixsys.po.eco.FlowEcoKfldtxsp;
import com.bp.sixsys.po.insp.FlowInspBmtbppsp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yanjisheng
 * @version 1.0
 * @Description: 康复劳动调休审批服务层
 * @date 2019年10月12日
 */
@Service
public class FlowEcoKfldtxspService extends BaseServiceImplByAct<GovKfldtxspMapper, FlowEcoKfldtxsp> {

    @Resource
    private GovKfldtxspMapper flowEcoKfldtxspMapper;


    @Autowired
    private SysClient sysClient;

    @Autowired
    private ActClient actClient;


    @Override
    public BaseMapperUUID<FlowEcoKfldtxsp> getMapper() {
        request.setAttribute("sysType","eco");
        request.setAttribute("tableName","flow_eco_kfldtxsp");
        return flowEcoKfldtxspMapper;
    }

    /**
     * 重写新增方法
     *
     * @param govKfldtxsp
     */

    @Override
    public String insert(FlowEcoKfldtxsp govKfldtxsp) {

        govKfldtxsp.setFlowState(0);
        govKfldtxsp.setFlowSn(sysClient.getNumberFlow("ACTIVITI_SN").msg);
        govKfldtxsp.setDocState("新建");
        govKfldtxsp.setUuid(IdUtils.getUuid());
        govKfldtxsp.setInserter(null == govKfldtxsp.getInserter() ? UserUtils.getCurrentUser() == null ? 0 : (UserUtils.getCurrentUser().getId()) : govKfldtxsp.getInserter());
        govKfldtxsp.setInsertTime(new Date());
        return super.insert(govKfldtxsp);

    }

    /**
     * 重写修改方法
     *
     * @param govKfldtxsp
     */
    @Override
    public void update(FlowEcoKfldtxsp govKfldtxsp) {

        govKfldtxsp.setUpdater(UserUtils.getCurrentUser() == null ? 0 : (UserUtils.getCurrentUser().getId()));
        govKfldtxsp.setUpdateTime(new Date());
        super.update(govKfldtxsp);

    }


    @Override
    public void doFinishBusiness(ActRollBackEntity actRollBackEntity) {

        FlowEcoKfldtxsp govKfldtxsp = super.findByPid(actRollBackEntity.getPid());
        String msg = actRollBackEntity.getMsg();
        if ("生活习艺科确认有无补休".equals(actRollBackEntity.getTaskName()) && "已补休".equals(msg)) {
            govKfldtxsp.setFlowState(FlowState.PASSED.getState()).setDocState(SysConstant.docState.finish.getDocState());
            super.update(govKfldtxsp);
        }

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
        paraMap.put("shxykId", sysClient.findFlowRoleByCode("047").data.getUserIds() + ",1");
        paraMap.put("sldId", sysClient.findFlowRoleByCode("001").data.getUserIds() + ",1");
        paraMap.put("szyldId", sysClient.findFlowRoleByCode("017").data.getUserIds() + ",1");
        paraMap.put("baseUrl", "sixsys/flowEcoKfldtxsp");
        for (String id : ids.split(SysConstant.COMMA)) {
            //GovWlrycljc govWlrycljcjdglsp = getMapper().selectById(id);
            paraMap.put("ddId", sysClient.findFlowRoleByCode("007").data.getUserIds() + ",1");
            // 监督问责参数
            paraMap.put("tableName","flow_eco_kfldtxsp");
            paraMap.put("sysType","eco");
            this.startProcess(id, "flowEcoKfldtxsp", paraMap);
            //将第一个节点的任务办理完
            String pid = getMapper().selectById(id).getPid();
            String taskId = actClient.findStartTaskId(UserUtils.getCurrentUser().getId(), pid).msg;
            Map<String, Object> para = new HashMap<>(12);
            para.put("taskId", taskId);
            para.put("pId", pid);
            para.put("pKey", "flowEcoKfldtxsp");
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
        FlowEcoKfldtxsp govKfldtxsp = super.findByPid(actRollBackEntity.getPid());
        if (null != govKfldtxsp) {
            // 办理进入手续
            if ("补休情况".equals(taskName)) {
                String bxqk = actRollBackEntity.getMap().get("bxqk").toString();
                govKfldtxsp.setBxqk(bxqk);
                super.update(govKfldtxsp);
            }

        }
    }

    @Override
    public FlowEcoKfldtxsp selectEntityById(String uuid){
        return flowEcoKfldtxspMapper.selectEntityById(uuid);
    }

    @Override
    public FlowEcoKfldtxsp findByPid(String pid) {
        return flowEcoKfldtxspMapper.findByPid(pid);
    }




}
