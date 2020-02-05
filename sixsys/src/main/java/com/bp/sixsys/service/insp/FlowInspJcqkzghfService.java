package com.bp.sixsys.service.insp;

import javax.annotation.Resource;

import com.bp.common.anno.FileAnnotation;
import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.constants.FlowState;
import com.bp.common.constants.SysConstant;
import com.bp.common.dto.ActRollBackEntity;
import com.bp.common.dto.BaseAddicts;
import com.bp.common.entity.SysUser;
import com.bp.common.utils.UserIdUtils;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.client.ActClient;
import com.bp.sixsys.client.SysClient;
import com.bp.sixsys.po.BaseDepartment;
import com.bp.sixsys.po.FlowRole;
import com.bp.sixsys.po.insp.FlowInspJcqktb;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bp.sixsys.po.insp.FlowInspJcqkzghf;
import com.bp.sixsys.mapper.insp.FlowInspJcqkzghfMapper;
import com.bp.common.base.BaseMapperUUID;
import com.bp.common.base.BaseServiceImplUUID;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pengwanli
 * @version 1.0
 * @Description: 对规范权力运行检查情况问题的整改回复服务层
 * @date 2019年12月02日
*/
@Service
public class FlowInspJcqkzghfService extends BaseServiceImplByAct<FlowInspJcqkzghfMapper, FlowInspJcqkzghf> {

	@Resource
	private FlowInspJcqkzghfMapper flowInspJcqkzghfMapper;

    @Resource
    private SysClient sysClient;

    @Autowired
    private ActClient actClient;

	@Override
	public BaseMapperUUID<FlowInspJcqkzghf> getMapper() {
        request.setAttribute("sysType","insp");
        request.setAttribute("tableName","flow_insp_jcqkzghf");
		return flowInspJcqkzghfMapper;
	}

    @Override
    @Transactional(rollbackFor = Exception.class)
    @FileAnnotation(paramName = "xgwj")
    @LcnTransaction
    public String insert(FlowInspJcqkzghf entity) {
        entity.setFlowState(0);
        entity.setFlowSn(sysClient.getNumberFlow("ACTIVITI_SN").msg);
        entity.setDocState("新建");
        return super.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @FileAnnotation(paramName = "xgwj")
    @LcnTransaction
    public void update(FlowInspJcqkzghf entity) {
        entity.setFlowState(0);
        entity.setFlowSn(sysClient.getNumberFlow("ACTIVITI_SN").msg);
        entity.setDocState("新建");
        super.update(entity);
    }
    /**
     * 提交单据
     *
     * @param uuids
     * @return 提交结果
     */
    public ReturnBean submit(String uuids) {
        Map<String, Object> paraMap = new HashMap<>(12);
        // 系统用户
        paraMap.put("sysId", UserUtils.getCurrentUser().getId() + ",1");
        // 单位部门负责人意见
        paraMap.put("dwbmfzrId",sysClient.findFlowRoleByCode("007").data.getUserIds() + ",1");
        // 监察室意见
        paraMap.put("jcsId", sysClient.findFlowRoleByCode("024").data.getUserIds() + ",1");
        // 分管所领导
        paraMap.put("fgsldId", sysClient.findFlowRoleByCode("001").data.getUserIds() + ",1");

        paraMap.put("baseUrl", "sixsys/flowInspJcqkzghf");
        for (String id :
                uuids.split(",")) {
            FlowInspJcqkzghf flowInspJcqkzghf = getMapper().selectById(id);
            //大队编码
            String str = "001,3601011100,3601011200,3601011300,3601011400,3601011500,3601011600";
            String[]strArr = str.split(",");
            boolean flag = false;
            for (String temp : strArr) {
               if(flowInspJcqkzghf.getSqrbmbm().equals(temp)){
                   paraMap.put("flag",true);
                   // 大队提交,设置网关条件
                   paraMap.put("exclusivegateway1","${flag}");
                   flag = true;
                   break;
               }
            }
            if(!flag){
                paraMap.put("flag",false);
                // 非大队提交,设置网关条件
                paraMap.put("exclusivegateway1","${!flag}");
            }

            paraMap.put("tableName","flow_insp_jcqkzghf");
            paraMap.put("sysType","insp");
            this.startProcess(id, "flowInspJcqkzghf", paraMap);

            //将第一个节点的任务办理完
            String pid = getMapper().selectById(id).getPid();
            String taskId = actClient.findStartTaskId(UserUtils.getCurrentUser().getId(), pid).msg;
            Map<String, Object> para = new HashMap<>(12);
            para.put("taskId", taskId);
            para.put("pId", pid);
            para.put("pKey", "flowInspJcqkzghf");
            para.put("msg", "提交");
            para.put("comment", "默认提交");
            actClient.handleStartTask(para);
        }
        return ReturnBean.ok("流程启动成功");
    }


    /**
     * 获取用户的ID
     *
     * @param jdrybm
     * @param code
     * @return
     */
    private String getUserIds(String jdrybm, String code) {
        ReturnBean<BaseAddicts> byJdrybm = sysClient.findByJdrybm(jdrybm);
        String bmbm = byJdrybm.data.getBmbm();
        ReturnBean<FlowRole> byCode = sysClient.findFlowRoleByCode(code);
        String userIds = "," + byCode.data.getUserIds() + ",";
        ReturnBean<BaseDepartment> byBmbm = sysClient.findByBmbm(bmbm);
        List<SysUser> sysUsers = sysClient.findFlowRoleByUserIdsAndBmbm(userIds, byBmbm.data.getSjbmbm());
        return UserIdUtils.idSplit(UserIdUtils.getUserIdList(sysUsers));
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
    }


    /**
     * 流程结束回调
     *
     * @param actRollBackEntity
     */
    @Override
    public void doFinishBusiness(ActRollBackEntity actRollBackEntity) {
        String msg = actRollBackEntity.getMsg();
        FlowInspJcqkzghf flowInspJcqkzghf = super.findByPid(actRollBackEntity.getPid());
        //更新数据状态为已审核
        if ("监察室意见".equals(actRollBackEntity.getTaskName()) && "已阅".equals(msg)) {
            flowInspJcqkzghf.setFlowState(FlowState.PASSED.getState()).setDocState(SysConstant.docState.finish.getDocState());
            super.update(flowInspJcqkzghf);
        }

    }
}
