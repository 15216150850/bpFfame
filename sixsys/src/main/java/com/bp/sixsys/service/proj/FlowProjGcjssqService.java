package com.bp.sixsys.service.proj;

import javax.annotation.Resource;

import com.bp.common.anno.FileAnnotation;
import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.constants.FlowState;
import com.bp.common.constants.SysConstant;
import com.bp.common.dto.ActRollBackEntity;
import com.bp.common.entity.SysUser;
import com.bp.common.exception.BpException;
import com.bp.common.utils.UserIdUtils;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.client.ActClient;
import com.bp.sixsys.client.SysClient;
import com.bp.sixsys.mapper.proj.FlowProjGcjssqMapper;
import com.bp.sixsys.po.BaseDepartment;
import com.bp.sixsys.po.FlowRole;
import com.bp.sixsys.po.proj.FlowProjGcjssq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bp.common.base.BaseMapperUUID;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangyu
 * @version 1.0
 * @Description: 工程建设申请服务层
 * @date 2019年11月25日
*/
@Service
public class FlowProjGcjssqService extends BaseServiceImplByAct<FlowProjGcjssqMapper, FlowProjGcjssq> {

	@Resource
	private FlowProjGcjssqMapper flowProjGcjssqMapper;

	@Override
	public BaseMapperUUID<FlowProjGcjssq> getMapper() {
        request.setAttribute("tableName","flow_proj_gcjssq");
        request.setAttribute("sysType","proj");
	    return flowProjGcjssqMapper;
	}

    @Autowired
    private SysClient sysClient;

    @Autowired
    private ActClient actClient;

    @Override
    @FileAnnotation(paramName = "xgwj")
    public String insert(FlowProjGcjssq entity) {
        entity.setFlowState(0);
        entity.setFlowSn(sysClient.getNumberFlow("ACTIVITI_SN").msg);
        entity.setDocState("新建");
        return super.insert(entity);
    }

    @Override
    @FileAnnotation(paramName = "xgwj")
    public void update(FlowProjGcjssq entity) {
        entity.setFlowState(0);
        entity.setFlowSn(sysClient.getNumberFlow("ACTIVITI_SN").msg);
        entity.setDocState("新建");
        super.update(entity);
    }

    /**
     * 提交审核
     *
     * @param ids
     * @return
     */
//    @Transactional(rollbackFor = Exception.class)
//    @LcnTransaction
    public ReturnBean submit(String ids) {
        try{
            //设置流程参数
            Map<String, Object> paraMap = new HashMap<>(12);
            // 系统用户
            paraMap.put("userId", UserUtils.getCurrentUser().getId() + ",1");
            // 所领导
            paraMap.put("sldId", sysClient.findFlowRoleByCode("001").data.getUserIds() + ",1");
            // 办公室负责人
            paraMap.put("bgsfzr", sysClient.findFlowRoleByCode("023").data.getUserIds() + ",1");
            /********************************** 新加的流程角色 start *********************************/
            // 基建经办人
            paraMap.put("jjjbr", sysClient.findFlowRoleByCode("025").data.getUserIds() + ",1");
            // 基建办公室负责人
            paraMap.put("jjbgs", sysClient.findFlowRoleByCode("026").data.getUserIds() + ",1");
            // 财务部门审核人
            paraMap.put("cwbmshr", sysClient.findFlowRoleByCode("027").data.getUserIds() + ",1");
            // 财务部门负责人
            paraMap.put("cwbmfzr", sysClient.findFlowRoleByCode("028").data.getUserIds() + ",1");
            // 财务分管所领导
            paraMap.put("cwfgsld", sysClient.findFlowRoleByCode("029").data.getUserIds() + ",1");
            // 基建办分管所领导
            paraMap.put("jjbfgsld", sysClient.findFlowRoleByCode("030").data.getUserIds() + ",1");
            /********************************** 新加的流程角色 end *********************************/
            paraMap.put("baseUrl", "sixsys/flowProjGcjssq");

            // 启动流程
            for (String id : ids.split(SysConstant.COMMA)) {
                FlowProjGcjssq flowProjGcjssq = getMapper().selectById(id);
                Map userMap = sysClient.findUserById(flowProjGcjssq.getInserter()).data;
                // 金额
                paraMap.put("yjje", flowProjGcjssq.getYjje());
                // 设置网关条件
                paraMap = setGatewayParam(flowProjGcjssq, paraMap);
                // 流程提交用户所在大队的负责人（大队领导）
                paraMap.put("ddId", this.getUserIds(userMap.get("organizationCode").toString()) + ",1");
                // 监督问责参数
                paraMap.put("tableName","flow_proj_gcjssq");
                paraMap.put("sysType","proj");
                this.startProcess(id, "flowProjGcjssq", paraMap);
                String pid = getMapper().selectById(id).getPid();
                String taskId = actClient.findStartTaskId(UserUtils.getCurrentUser().getId(), pid).msg;
                Map<String, Object> para = new HashMap<>(12);
                para.put("taskId", taskId);
                para.put("pId", pid);
                para.put("pKey", "flowProjGcjssq");
                para.put("msg", "提交");
                para.put("comment", "默认提交");
                actClient.handleStartTask(para);
            }
            return ReturnBean.ok("流程启动成功！");
        }catch (Exception e){
            e.printStackTrace();
            throw new BpException("工程建设申请流程启动出错");
        }
    }

    @Override
    public void doFinishBusiness(ActRollBackEntity actRollBackEntity) {
        FlowProjGcjssq flowProjGcjssq = super.findByPid(actRollBackEntity.getPid());
        if ("基建工程材料归档".equals(actRollBackEntity.getTaskName()) && "材料完整".equals(actRollBackEntity.getMsg())) {
            flowProjGcjssq.setFlowState(FlowState.PASSED.getState()).setDocState(SysConstant.docState.finish.getDocState());
            super.update(flowProjGcjssq);
        }
    }

    @Override
    public void taskComplete(ActRollBackEntity actRollBackEntity) {
        // 流程节点名称
        String taskName = actRollBackEntity.getTaskName();
        FlowProjGcjssq flowProjGcjssq = super.findByPid(actRollBackEntity.getPid());
        if (null != flowProjGcjssq) {
            if("系统用户".equals(taskName)){
                // 事前
                super.beforehand(flowProjGcjssq);
            }
            else if ("基建工程材料归档".equals(taskName)) {
                // 事后
                super.afterwards(flowProjGcjssq);
            }
            else{
                // 事中
                super.inFact(flowProjGcjssq);
            }

            Map param = actRollBackEntity.getMap();
            // 存储 基建办公室负责人意见
            if ("基建办公室负责人意见".equals(taskName)) {
                flowProjGcjssq.setJjbgsyj(param.get("jjbgsyj").toString());
                super.update(flowProjGcjssq);
            }
            // 存储 该项目资金来源 和 预算情况
            if ("财务部门预算审核人意见".equals(taskName)) {
                flowProjGcjssq.setXmzjly(param.get("xmzjly").toString())
                        .setYsqk(param.get("ysqk").toString());
                super.update(flowProjGcjssq);
            }
            // 存储 采购实施机构 和 采购方式
            else if ("基建办公室经办人填写".equals(taskName)) {
                flowProjGcjssq.setCgssjg(param.get("cgssjg").toString())
                        .setCgfs(param.get("cgfs").toString());
                super.update(flowProjGcjssq);
            }
            // 存储 抄告单
            else if ("办公室主任上传材料".equals(taskName)) {
                flowProjGcjssq.setBgszrcgd(param.get("cgd").toString());
                super.update(flowProjGcjssq);
            }
            // 存储 抄告单
            else if ("办公室主任填写党委会抄告单".equals(taskName)) {
                flowProjGcjssq.setDwhcgd(param.get("cgd").toString());
                super.update(flowProjGcjssq);
            }
            // 存储 省戒毒局批复意见
            else if ("省戒毒局批复意见".equals(taskName)) {
                flowProjGcjssq.setSjdjpfyj(param.get("sjdjpfyj").toString());
                super.update(flowProjGcjssq);
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

    /**
     * 手动设置网关条件
     * @param flowProjGcjssq
     * @param param
     * @return
     */
    private Map<String, Object>  setGatewayParam(FlowProjGcjssq flowProjGcjssq,Map<String, Object> param){
        BigDecimal yjje = flowProjGcjssq.getYjje();
        // 预计金额<5000
        if (yjje.compareTo(new BigDecimal(5000)) < 0){
            param.put("exclusivegateway1", "${yjje<5000}");
        }
        // 预计金额>=5000
        if (yjje.compareTo(new BigDecimal(5000)) >= 0){
            param.put("exclusivegateway1", "${yjje>=5000}");
        }
        // 预计金额<5万
        if (yjje.compareTo(new BigDecimal(50000)) < 0){
            param.put("exclusivegateway4", "${yjje<50000}");
        }
        // 预计金额>=5万
        if (yjje.compareTo(new BigDecimal(50000)) >= 0){
            param.put("exclusivegateway4", "${yjje>=50000}");
        }
        // 预计金额<400万
        if (yjje.compareTo(new BigDecimal(4000000)) < 0){
            param.put("exclusivegateway6", "${yjje<4000000}");
        }
        // 预计金额>=400万
        if (yjje.compareTo(new BigDecimal(4000000)) >= 0){
            param.put("exclusivegateway6", "${yjje>=4000000}");
        }
        // 预计金额<1万
        if (yjje.compareTo(new BigDecimal(10000)) < 0){
            param.put("exclusivegateway2", "${yjje<10000}");
        }
        // 预计金额>=1万
        if (yjje.compareTo(new BigDecimal(10000)) >= 0){
            param.put("exclusivegateway2", "${yjje>=10000}");
        }
        // 预计金额<2万
        if (yjje.compareTo(new BigDecimal(20000)) < 0){
            param.put("exclusivegateway3", "${yjje<20000}");
        }
        // 预计金额>=2万
        if (yjje.compareTo(new BigDecimal(20000)) >= 0){
            param.put("exclusivegateway3", "${yjje>=20000}");
        }
        // 预计金额<50万
        if (yjje.compareTo(new BigDecimal(500000)) < 0){
            param.put("exclusivegateway5", "${yjje<500000}");
        }
        // 预计金额>=50万
        if (yjje.compareTo(new BigDecimal(500000)) >= 0){
            param.put("exclusivegateway5", "${yjje>=500000}");
        }
        return param;
    }
}
