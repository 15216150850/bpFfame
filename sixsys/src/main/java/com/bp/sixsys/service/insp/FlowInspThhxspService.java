package com.bp.sixsys.service.insp;

import javax.annotation.Resource;

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
import com.bp.sixsys.mapper.insp.FlowInspThhxspMapper;
import com.bp.sixsys.po.insp.FlowInspJmthsp;
import com.bp.sixsys.po.insp.FlowInspThhxsp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author guoxiangxu
 * @version 1.0
 * @Description: 谈话函询审批服务层
 * @date 2019年12月03日
*/
@Service
public class FlowInspThhxspService extends BaseServiceImplByAct<FlowInspThhxspMapper, FlowInspThhxsp> {

	@Resource
	private FlowInspThhxspMapper flowInspThhxspMapper;

	@Autowired
	private FileStoreClient fileStoreClient;

	@Autowired
	private SysClient sysClient;

	@Autowired
	private ActClient actClient;

	@Override
	public BaseMapperUUID<FlowInspThhxsp> getMapper() {
		request.setAttribute("sysType","insp");
		request.setAttribute("tableName","flow_insp_thhxsp");
		return flowInspThhxspMapper;
	}


	/**
	 * 重写新增方法
	 *
	 * @param flowInspThhxsp
	 */

	@Override
	public String insert(FlowInspThhxsp flowInspThhxsp) {

		flowInspThhxsp.setFlowState(0);
		flowInspThhxsp.setFlowSn(sysClient.getNumberFlow("ACTIVITI_SN").msg);
		flowInspThhxsp.setDocState("新建");

		flowInspThhxsp.setUuid(IdUtils.getUuid());
		flowInspThhxsp.setInserter(null == flowInspThhxsp.getInserter() ? UserUtils.getCurrentUser() == null ? 0 : (UserUtils.getCurrentUser().getId()) : flowInspThhxsp.getInserter());
		flowInspThhxsp.setInsertTime(new Date());

		if (!"".equals(flowInspThhxsp.getFj()) && flowInspThhxsp.getFj() != null) {
			fileStoreClient.updateFileStatus(flowInspThhxsp.getFj());
		}
		return super.insert(flowInspThhxsp);

	}

	/**
	 * 重写修改方法
	 *
	 * @param flowInspThhxsp
	 */
	@Override
	public void update(FlowInspThhxsp flowInspThhxsp) {
		if (flowInspThhxsp.getFj() != null && !"".equals(flowInspThhxsp.getFj())) {
			fileStoreClient.updateFileStatus(flowInspThhxsp.getFj());
		}
		//!!!!!!
		flowInspThhxsp.setUpdater(UserUtils.getCurrentUser() == null ? 0 : (UserUtils.getCurrentUser().getId()));
		flowInspThhxsp.setUpdateTime(new Date());
		super.update(flowInspThhxsp);

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
		paraMap.put("jwId", sysClient.findFlowRoleByCode("045").data.getUserIds() + ",1");
		paraMap.put("dwId", sysClient.findFlowRoleByCode("035").data.getUserIds() + ",1");
		paraMap.put("baseUrl", "sixsys/flowInspThhxsp");
		for (String id : ids.split(SysConstant.COMMA)) {
			// 监督问责参数
			paraMap.put("tableName","flow_insp_thhxsp");
			paraMap.put("sysType","insp");
			this.startProcess(id, "flowInspThhxsp", paraMap);
			//将第一个节点的任务办理完
			String pid = getMapper().selectById(id).getPid();
			String taskId = actClient.findStartTaskId(UserUtils.getCurrentUser().getId(), pid).msg;
			Map<String, Object> para = new HashMap<>(12);
			para.put("taskId", taskId);
			para.put("pId", pid);
			para.put("pKey", "flowInspThhxsp");
			para.put("msg", "提交");
			para.put("comment", "默认提交");
			System.out.println(para);
			actClient.handleStartTask(para);
		}
		return ReturnBean.ok("流程启动成功");

	}


	@Override
	public void doFinishBusiness(ActRollBackEntity actRollBackEntity) {

		FlowInspThhxsp flowInspThhxsp = super.findByPid(actRollBackEntity.getPid());
		String msg = actRollBackEntity.getMsg();
		if ("党委书记意见".equals(actRollBackEntity.getTaskName()) && "同意".equals(msg)) {
			flowInspThhxsp.setFlowState(FlowState.PASSED.getState()).setDocState(SysConstant.docState.finish.getDocState());
			super.update(flowInspThhxsp);
		}
	}

	@Override
	public void taskComplete(ActRollBackEntity actRollBackEntity) {

	}
}
