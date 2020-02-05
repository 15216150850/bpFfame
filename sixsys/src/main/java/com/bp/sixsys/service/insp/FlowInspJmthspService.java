package com.bp.sixsys.service.insp;

import javax.annotation.Resource;

import com.bp.common.base.BaseMapperUUID;
import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.base.BaseServiceImplUUID;
import com.bp.common.bean.ReturnBean;
import com.bp.common.constants.FlowState;
import com.bp.common.constants.SysConstant;
import com.bp.common.dto.ActRollBackEntity;
import com.bp.common.utils.IdUtils;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.client.ActClient;
import com.bp.sixsys.client.FileStoreClient;
import com.bp.sixsys.client.SysClient;
import com.bp.sixsys.mapper.insp.FlowInspJmthspMapper;
import com.bp.sixsys.po.insp.FlowInspJcjytzs;
import com.bp.sixsys.po.insp.FlowInspJmthsp;
import com.bp.sixsys.po.insp.FlowInspJmthsp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @author guoxiangxu
 * @version 1.0
 * @Description: 诫勉谈话审批表服务层
 * @date 2019年12月03日
*/
@Service
public class FlowInspJmthspService extends BaseServiceImplByAct<FlowInspJmthspMapper, FlowInspJmthsp> {

	@Resource
	private FlowInspJmthspMapper flowInspJmthspMapper;
	

	@Autowired
	private SysClient sysClient;

	@Autowired
	private ActClient actClient;

	@Override
	public BaseMapperUUID<FlowInspJmthsp> getMapper() {
		request.setAttribute("sysType","insp");
		request.setAttribute("tableName","flow_insp_jmthsp");
		return flowInspJmthspMapper;
	}


	/**
	 * 重写新增方法
	 *
	 * @param flowInspJmthsp
	 */

	@Override
	public String insert(FlowInspJmthsp flowInspJmthsp) {

		flowInspJmthsp.setFlowState(0);
		flowInspJmthsp.setFlowSn(sysClient.getNumberFlow("ACTIVITI_SN").msg);
		flowInspJmthsp.setDocState("新建");
		flowInspJmthsp.setUuid(IdUtils.getUuid());
		flowInspJmthsp.setInserter(null == flowInspJmthsp.getInserter() ? UserUtils.getCurrentUser() == null ? 0 : (UserUtils.getCurrentUser().getId()) : flowInspJmthsp.getInserter());
		flowInspJmthsp.setInsertTime(new Date());
		return super.insert(flowInspJmthsp);

	}

	/**
	 * 重写修改方法
	 *
	 * @param flowInspJmthsp
	 */
	@Override
	public void update(FlowInspJmthsp flowInspJmthsp) {
		//!!!!!!
		flowInspJmthsp.setUpdater(UserUtils.getCurrentUser() == null ? 0 : (UserUtils.getCurrentUser().getId()));
		flowInspJmthsp.setUpdateTime(new Date());
		super.update(flowInspJmthsp);

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
		paraMap.put("zgjjId", sysClient.findFlowRoleByCode("045").data.getUserIds() + ",1");
		paraMap.put("fgsldId", sysClient.findFlowRoleByCode("001").data.getUserIds() + ",1");
		paraMap.put("dwId", sysClient.findFlowRoleByCode("035").data.getUserIds() + ",1");
		paraMap.put("baseUrl", "sixsys/flowInspJmthsp");
		for (String id : ids.split(SysConstant.COMMA)) {
			// 监督问责参数
			paraMap.put("tableName","flow_insp_jmthsp");
			paraMap.put("sysType","insp");
			this.startProcess(id, "flowInspJmthsp", paraMap);
			//将第一个节点的任务办理完
			String pid = getMapper().selectById(id).getPid();
			String taskId = actClient.findStartTaskId(UserUtils.getCurrentUser().getId(), pid).msg;
			Map<String, Object> para = new HashMap<>(12);
			para.put("taskId", taskId);
			para.put("pId", pid);
			para.put("pKey", "flowInspJmthsp");
			para.put("msg", "提交");
			para.put("comment", "默认提交");
			System.out.println(para);
			actClient.handleStartTask(para);
		}
		return ReturnBean.ok("流程启动成功");

	}



	@Override
	public void doFinishBusiness(ActRollBackEntity actRollBackEntity) {

		FlowInspJmthsp flowInspJmthsp = super.findByPid(actRollBackEntity.getPid());
		String msg = actRollBackEntity.getMsg();
		if ("党委主要负责人意见".equals(actRollBackEntity.getTaskName()) && "同意".equals(msg)) {
			flowInspJmthsp.setFlowState(FlowState.PASSED.getState()).setDocState(SysConstant.docState.finish.getDocState());
			super.update(flowInspJmthsp);
		}

	}

	@Override
	public void taskComplete(ActRollBackEntity actRollBackEntity) {

	}
}
