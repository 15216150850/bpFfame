package com.bp.sixsys.service.insp;

import javax.annotation.Resource;

import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.dto.ActRollBackEntity;
import com.bp.common.dto.BaseAddicts;
import com.bp.common.entity.SysUser;
import com.bp.common.utils.UserIdUtils;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.client.ActClient;
import com.bp.sixsys.client.SysClient;
import com.bp.sixsys.po.BaseDepartment;
import com.bp.sixsys.po.FlowRole;
import com.bp.sixsys.po.insp.FlowInspJcqkzghf;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bp.sixsys.po.insp.FlowInspJmthtz;
import com.bp.sixsys.mapper.insp.FlowInspJmthtzMapper;
import com.bp.common.base.BaseMapperUUID;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pengwanli
 * @version 1.0
 * @Description: 诫勉谈话通知服务层
 * @date 2019年12月02日
*/
@Service
public class FlowInspJmthtzService extends BaseServiceImplByAct<FlowInspJmthtzMapper, FlowInspJmthtz> {

	@Resource
	private FlowInspJmthtzMapper flowInspJmthtzMapper;

    @Resource
    private SysClient sysClient;

    @Autowired
    private ActClient actClient;

	@Override
	public BaseMapperUUID<FlowInspJmthtz> getMapper() {
        request.setAttribute("sysType","insp");
        request.setAttribute("tableName","flow_insp_jmthtz");
		return flowInspJmthtzMapper;
	}

    @Override
    public String insert(FlowInspJmthtz entity) {
        entity.setFlowState(0);
        entity.setFlowSn(sysClient.getNumberFlow("ACTIVITI_SN").msg);
        entity.setDocState("新建");
        return super.insert(entity);
    }

    @Override
    public void update(FlowInspJmthtz entity) {
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
        //系统用户
        paraMap.put("sysId", UserUtils.getCurrentUser().getId() + ",1");

        //政工或纪检部门意见
        paraMap.put("zghjjbmId", sysClient.findFlowRoleByCode("045").data.getUserIds() + ",1");
        paraMap.put("baseUrl", "sixsys/flowInspJmthtz");
        for (String id :
                uuids.split(",")) {
            FlowInspJmthtz flowInspJmthtz = getMapper().selectById(id);
            //被谈话人签收
            paraMap.put("bthrId", sysClient.findUserByUserName(flowInspJmthtz.getThdxbm()).data.get("id")+ ",1");
            paraMap.put("tableName","flow_insp_jmthtz");
            paraMap.put("sysType","insp");
            this.startProcess(id, "flowInspJmthtz", paraMap);

            //将第一个节点的任务办理完
            String pid = getMapper().selectById(id).getPid();
            String taskId = actClient.findStartTaskId(UserUtils.getCurrentUser().getId(), pid).msg;
            Map<String, Object> para = new HashMap<>(12);
            para.put("taskId", taskId);
            para.put("pId", pid);
            para.put("pKey", "flowInspJmthtz");
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
    }


    /**
     * 流程结束回调
     *
     * @param actRollBackEntity
     */
    @Override
    public void doFinishBusiness(ActRollBackEntity actRollBackEntity) {

    }
}
