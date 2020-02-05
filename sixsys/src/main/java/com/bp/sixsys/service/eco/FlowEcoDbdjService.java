package com.bp.sixsys.service.eco;

import javax.annotation.Resource;

import com.bp.common.anno.FileAnnotation;
import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.constants.FlowState;
import com.bp.common.constants.SysConstant;
import com.bp.common.dto.ActRollBackEntity;
import com.bp.common.entity.SysUser;
import com.bp.common.exception.BpException;
import com.bp.common.utils.DateUtil;
import com.bp.common.utils.UserIdUtils;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.client.ActClient;
import com.bp.sixsys.client.SysClient;
import com.bp.sixsys.mapper.eco.FlowEcoDbdjMapper;
import com.bp.sixsys.po.BaseDepartment;
import com.bp.sixsys.po.FlowRole;
import com.bp.sixsys.po.eco.FlowEcoDbdj;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import org.apache.commons.lang.StringUtils;
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
 * @Description: 打版定价服务层
 * @date 2019年11月25日
*/
@Service
public class FlowEcoDbdjService extends BaseServiceImplByAct<FlowEcoDbdjMapper, FlowEcoDbdj> {

	@Resource
	private FlowEcoDbdjMapper flowEcoDbdjMapper;

	@Override
	public BaseMapperUUID<FlowEcoDbdj> getMapper() {
        request.setAttribute("tableName","flow_eco_dbdj");
        request.setAttribute("sysType","eco");
	    return flowEcoDbdjMapper;
	}

    @Autowired
    private SysClient sysClient;

    @Autowired
    private ActClient actClient;

    @Override
    @FileAnnotation(paramName = "xgwj")
    public String insert(FlowEcoDbdj entity) {
        entity.setFlowState(0);
        entity.setFlowSn(sysClient.getNumberFlow("ACTIVITI_SN").msg);
        entity.setDocState("新建");
        return super.insert(entity);
    }

    @Override
    @FileAnnotation(paramName = "xgwj")
    public void update(FlowEcoDbdj entity) {
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
            // 设置流程参数
            Map<String, Object> paraMap = new HashMap<>(12);
            // 系统用户
            paraMap.put("userId", UserUtils.getCurrentUser().getId() + ",1");
            // 生活习艺科
            paraMap.put("shxyk", sysClient.findFlowRoleByCode("047").data.getUserIds() + ",1");
            // 分管所领导
            paraMap.put("fgsldId", sysClient.findFlowRoleByCode("001").data.getUserIds() + ",1");
            // 经济运行领导小组
            paraMap.put("jjyxldxzId", sysClient.findFlowRoleByCode("048").data.getUserIds() + ",1");
            // 所党委
            paraMap.put("dwId", sysClient.findFlowRoleByCode("035").data.getUserIds() + ",1");
            paraMap.put("baseUrl", "sixsys/flowEcoDbdj");
            // 启动流程
            for (String id : ids.split(SysConstant.COMMA)) {
                FlowEcoDbdj flowEcoDbdj = super.getById(id);
                // 大队
                Map userMap = sysClient.findUserById(flowEcoDbdj.getInserter()).data;
                paraMap.put("ddId", this.getUserIds(userMap.get("organizationCode").toString()) + ",1");
                // 监督问责参数
                paraMap.put("tableName","flow_eco_dbdj");
                paraMap.put("sysType","eco");
                this.startProcess(id, "flowEcoDbdj", paraMap);
                String pid = getMapper().selectById(id).getPid();
                String taskId = actClient.findStartTaskId(UserUtils.getCurrentUser().getId(), pid).msg;
                Map<String, Object> para = new HashMap<>(12);
                para.put("taskId", taskId);
                para.put("pId", pid);
                para.put("pKey", "flowEcoDbdj");
                para.put("msg", "提交");
                para.put("comment", "默认提交");
                System.out.println(para);
                actClient.handleStartTask(para);
            }
            return ReturnBean.ok("流程启动成功！");
        }catch (Exception e){
            e.printStackTrace();
            throw new BpException("打版定价流程启动出错");
        }
    }

    @Override
    public void doFinishBusiness(ActRollBackEntity actRollBackEntity) {
        FlowEcoDbdj flowEcoDbdj = super.findByPid(actRollBackEntity.getPid());
        String taskName = actRollBackEntity.getTaskName();
        String msg = actRollBackEntity.getMsg();
        if (("经济运行领导小组意见".equals(taskName) && "同意".equals(msg))
            || ("所党委意见".equals(taskName) && "同意".equals(msg))
            || ("分管领导审批".equals(taskName) && "同意".equals(msg))) {
            flowEcoDbdj.setFlowState(FlowState.PASSED.getState()).setDocState(SysConstant.docState.finish.getDocState());
            super.update(flowEcoDbdj);
        }
    }

    @Override
    public void taskComplete(ActRollBackEntity actRollBackEntity) {
        // 流程节点名称
        String taskName = actRollBackEntity.getTaskName();
        FlowEcoDbdj flowEcoDbdj = super.findByPid(actRollBackEntity.getPid());
        if (null != flowEcoDbdj) {
            if("系统用户".equals(taskName)){
                // 事前
                super.beforehand(flowEcoDbdj);
            }
            if ("加工大队意见".equals(taskName) || "生活习艺科意见".equals(taskName) || "党委会材料上传".equals(taskName)){
                // 事中
                super.inFact(flowEcoDbdj);
            }
            if ("所党委意见".equals(taskName) || "经济运行领导小组意见".equals(taskName)) {
                // 事后
                super.afterwards(flowEcoDbdj);
            }

            Map param = actRollBackEntity.getMap();// 存储 加工大队意见价格（元）
            if ("加工大队意见".equals(taskName)) {
                flowEcoDbdj.setJgddyj(StringUtils.isBlank(param.get("jgddyj").toString()) ? null : new BigDecimal(param.get("jgddyj").toString()));
                super.update(flowEcoDbdj);
            }
            // 存储 生活习艺科意见价格（元）
            if ("生活习艺科意见".equals(taskName)) {
                flowEcoDbdj.setShxykyj(StringUtils.isBlank(param.get("shxykyj").toString()) ? null : new BigDecimal(param.get("shxykyj").toString()));
                super.update(flowEcoDbdj);
            }
            // 存储 商务谈判价格（元）
            else if ("商务谈判".equals(taskName)) {
                flowEcoDbdj.setSwtpjg(StringUtils.isBlank(param.get("swtpjg").toString()) ? null : new BigDecimal(param.get("swtpjg").toString()));
                super.update(flowEcoDbdj);
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
