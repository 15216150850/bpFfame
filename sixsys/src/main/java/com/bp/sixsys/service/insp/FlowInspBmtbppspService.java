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
import com.bp.sixsys.client.SysClient;
import com.bp.sixsys.mapper.insp.FlowInspBmtbppspMapper;
import com.bp.sixsys.po.insp.FlowInspBmtbppsp;
import com.bp.sixsys.po.insp.FlowInspBmtbppsp;
import com.bp.sixsys.po.insp.FlowInspJcjytzs;
import com.bp.sixsys.po.insp.FlowInspTbppsp;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author guoxiangxu
 * @version 1.0
 * @Description: 通报批评审批（部门）服务层
 * @date 2019年12月03日
*/
@Service
public class FlowInspBmtbppspService extends BaseServiceImplByAct<FlowInspBmtbppspMapper, FlowInspBmtbppsp> {

	@Resource
	private FlowInspBmtbppspMapper flowInspBmtbppspMapper;

	@Autowired
	private SysClient sysClient;

	@Autowired
	private ActClient actClient;

	@Override
	public BaseMapperUUID<FlowInspBmtbppsp> getMapper() {
		request.setAttribute("sysType","insp");
		request.setAttribute("tableName","flow_insp_bmtbppsp");
		return flowInspBmtbppspMapper;
	}

	/**
	 * 重写新增方法
	 *
	 * @param flowInspBmtbppsp
	 */

	@Override
	public String insert(FlowInspBmtbppsp flowInspBmtbppsp) {
		flowInspBmtbppsp.setFlowState(0);
		flowInspBmtbppsp.setFlowSn(sysClient.getNumberFlow("ACTIVITI_SN").msg);
		flowInspBmtbppsp.setDocState("新建");
		flowInspBmtbppsp.setUuid(IdUtils.getUuid());
		flowInspBmtbppsp.setInserter(null == flowInspBmtbppsp.getInserter() ? UserUtils.getCurrentUser() == null ? 0 : (UserUtils.getCurrentUser().getId()) : flowInspBmtbppsp.getInserter());
		flowInspBmtbppsp.setInsertTime(new Date());
		return super.insert(flowInspBmtbppsp);

	}

	/**
	 * 重写修改方法
	 *
	 * @param flowInspBmtbppsp
	 */
	@Override
	public void update(FlowInspBmtbppsp flowInspBmtbppsp) {
		//!!!!!!
		flowInspBmtbppsp.setUpdater(UserUtils.getCurrentUser() == null ? 0 : (UserUtils.getCurrentUser().getId()));
		flowInspBmtbppsp.setUpdateTime(new Date());
		super.update(flowInspBmtbppsp);

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
		paraMap.put("jjId", sysClient.findFlowRoleByCode("045").data.getUserIds() + ",1");
		paraMap.put("sldId", sysClient.findFlowRoleByCode("001").data.getUserIds() + ",1");
		paraMap.put("dwId", sysClient.findFlowRoleByCode("035").data.getUserIds() + ",1");
		paraMap.put("bmfzrId", sysClient.findFlowRoleByCode("007").data.getUserIds() + ",1");
		paraMap.put("baseUrl", "sixsys/flowInspBmtbppsp");
		for (String id : ids.split(SysConstant.COMMA)) {
			// 监督问责参数
			paraMap.put("tableName","flow_insp_bmtbppsp");
			paraMap.put("sysType","insp");
			this.startProcess(id, "flowInspBmtbppsp", paraMap);
			//将第一个节点的任务办理完
			String pid = getMapper().selectById(id).getPid();
			String taskId = actClient.findStartTaskId(UserUtils.getCurrentUser().getId(), pid).msg;
			Map<String, Object> para = new HashMap<>(12);
			para.put("taskId", taskId);
			para.put("pId", pid);
			para.put("pKey", "flowInspBmtbppsp");
			para.put("msg", "提交");
			para.put("comment", "默认提交");
			System.out.println(para);
			actClient.handleStartTask(para);
		}
		return ReturnBean.ok("流程启动成功");

	}


	@Override
	public void doFinishBusiness(ActRollBackEntity actRollBackEntity) {

		FlowInspBmtbppsp flowInspBmtbppsp = super.findByPid(actRollBackEntity.getPid());
		String msg = actRollBackEntity.getMsg();
		if ("被通报部门负责人签收".equals(actRollBackEntity.getTaskName()) && "已签收".equals(msg)) {
			flowInspBmtbppsp.setFlowState(FlowState.PASSED.getState()).setDocState(SysConstant.docState.finish.getDocState());
			super.update(flowInspBmtbppsp);
		}

	}

	@Override
	public void taskComplete(ActRollBackEntity actRollBackEntity) {

	}

	@Override
	public FlowInspBmtbppsp findByPid(String pid) {
		return flowInspBmtbppspMapper.findByPid(pid);
	}

	@Override
	public FlowInspBmtbppsp selectEntityById(String uuid){
		return flowInspBmtbppspMapper.selectEntityById(uuid);
	}

}
