package com.bp.sixsys.service.insp;

import javax.annotation.Resource;

import com.bp.common.base.BaseMapperUUID;
import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.constants.FlowState;
import com.bp.common.constants.SysConstant;
import com.bp.common.dto.ActRollBackEntity;
import com.bp.common.utils.DateUtil;
import com.bp.common.utils.IdUtils;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.client.ActClient;
import com.bp.sixsys.client.FileStoreClient;
import com.bp.sixsys.client.SysClient;
import com.bp.sixsys.mapper.insp.FlowInspHxtzsMapper;
import com.bp.sixsys.po.insp.FlowInspDflzjsgzyttzs;
import com.bp.sixsys.po.insp.FlowInspHxtzs;
import com.bp.sixsys.po.insp.FlowInspHxtzs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author guoxiangxu
 * @version 1.0
 * @Description: 函询通知书服务层
 * @date 2019年12月03日
*/
@Service
public class FlowInspHxtzsService extends BaseServiceImplByAct<FlowInspHxtzsMapper, FlowInspHxtzs> {

	@Resource
	private FlowInspHxtzsMapper flowInspHxtzsMapper;

	@Autowired
	private FileStoreClient fileStoreClient;

	@Autowired
	private SysClient sysClient;

	@Autowired
	private ActClient actClient;

	/**
	 * 重写新增方法
	 *
	 * @param flowInspHxtzs
	 */

	@Override
	public String insert(FlowInspHxtzs flowInspHxtzs) {

		flowInspHxtzs.setFlowState(0);
		flowInspHxtzs.setFlowSn(sysClient.getNumberFlow("ACTIVITI_SN").msg);
		flowInspHxtzs.setDocState("新建");

		flowInspHxtzs.setUuid(IdUtils.getUuid());
		flowInspHxtzs.setInserter(null == flowInspHxtzs.getInserter() ? UserUtils.getCurrentUser() == null ? 0 : (UserUtils.getCurrentUser().getId()) : flowInspHxtzs.getInserter());
		flowInspHxtzs.setInsertTime(new Date());

		if (!"".equals(flowInspHxtzs.getFj()) && flowInspHxtzs.getFj() != null) {
			fileStoreClient.updateFileStatus(flowInspHxtzs.getFj());
		}
		return super.insert(flowInspHxtzs);

	}

	/**
	 * 重写修改方法
	 *
	 * @param flowInspHxtzs
	 */
	@Override
	public void update(FlowInspHxtzs flowInspHxtzs) {
		if (flowInspHxtzs.getFj() != null && !"".equals(flowInspHxtzs.getFj())) {
			fileStoreClient.updateFileStatus(flowInspHxtzs.getFj());
		}
		//!!!!!!
		flowInspHxtzs.setUpdater(UserUtils.getCurrentUser() == null ? 0 : (UserUtils.getCurrentUser().getId()));
		flowInspHxtzs.setUpdateTime(new Date());
		super.update(flowInspHxtzs);

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
		paraMap.put("baseUrl", "sixsys/flowInspHxtzs");
		for (String id : ids.split(SysConstant.COMMA)) {
			// 监督问责参数
			paraMap.put("tableName", "flow_insp_hxtzs");
			paraMap.put("sysType", "insp");
			this.startProcess(id, "flowInspHxtzs", paraMap);
			//将第一个节点的任务办理完
			String pid = getMapper().selectById(id).getPid();
			String taskId = actClient.findStartTaskId(UserUtils.getCurrentUser().getId(), pid).msg;
			Map<String, Object> para = new HashMap<>(12);
			para.put("taskId", taskId);
			para.put("pId", pid);
			para.put("pKey", "flowInspHxtzs");
			para.put("msg", "提交");
			para.put("comment", "默认提交");
			System.out.println(para);
			actClient.handleStartTask(para);
		}
		return ReturnBean.ok("流程启动成功");

	}


	@Override
	public BaseMapperUUID<FlowInspHxtzs> getMapper() {
		request.setAttribute("sysType", "insp");
		request.setAttribute("tableName", "flow_insp_hxtzs");
		return flowInspHxtzsMapper;
	}

	@Override
	public void doFinishBusiness(ActRollBackEntity actRollBackEntity) {

		FlowInspHxtzs flowInspHxtzs = super.findByPid(actRollBackEntity.getPid());
		String msg = actRollBackEntity.getMsg();
		if ("被函询人签收".equals(actRollBackEntity.getTaskName()) && "已签收".equals(msg)) {
			flowInspHxtzs.setFlowState(FlowState.PASSED.getState()).setDocState(SysConstant.docState.finish.getDocState());
			super.update(flowInspHxtzs);
		}
	}

	@Override
	public void taskComplete(ActRollBackEntity actRollBackEntity) {

		// 流程节点名称
		String taskName = actRollBackEntity.getTaskName();
		String status = actRollBackEntity.getMsg();
		// 实体Bean
		FlowInspHxtzs flowInspHxtzs = findByPid(actRollBackEntity.getPid());
		if (null != flowInspHxtzs) {
			// 办理进入手续
			if ("下发谈话函询通知书".equals(taskName)) {
				Date jzsj = DateUtil.stringToDate("yyyy-MM-dd HH:mm:ss", actRollBackEntity.getMap().get("jzsj").toString());
				flowInspHxtzs.setJzsj(jzsj);
				super.update(flowInspHxtzs);
			}
		}
	}
}
