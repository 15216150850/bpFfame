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
import com.bp.sixsys.mapper.insp.FlowInspDflzjsgzyttzsMapper;
import com.bp.sixsys.po.insp.FlowInspDflzjsgzyttzs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author guoxiangxu
 * @version 1.0
 * @Description: 党风廉政建设工作约谈通知书服务层
 * @date 2019年12月03日
*/
@Service
public class FlowInspDflzjsgzyttzsService extends BaseServiceImplByAct<FlowInspDflzjsgzyttzsMapper, FlowInspDflzjsgzyttzs> {

	@Resource
	private FlowInspDflzjsgzyttzsMapper flowInspDflzjsgzyttzsMapper;
	
	@Autowired
	private SysClient sysClient;

	@Autowired
	private ActClient actClient;
	

	@Override
	public BaseMapperUUID<FlowInspDflzjsgzyttzs> getMapper() {
		request.setAttribute("sysType","insp");
		request.setAttribute("tableName","flow_insp_dflzjsgzyttzs");
		return flowInspDflzjsgzyttzsMapper;
	}

	/**
	 * 重写新增方法
	 *
	 * @param flowInspDflzjsgzyttzs
	 */

	@Override
	public String insert(FlowInspDflzjsgzyttzs flowInspDflzjsgzyttzs) {

		flowInspDflzjsgzyttzs.setFlowState(0);
		flowInspDflzjsgzyttzs.setFlowSn(sysClient.getNumberFlow("ACTIVITI_SN").msg);
		flowInspDflzjsgzyttzs.setDocState("新建");
		flowInspDflzjsgzyttzs.setUuid(IdUtils.getUuid());
		flowInspDflzjsgzyttzs.setInserter(null == flowInspDflzjsgzyttzs.getInserter() ? UserUtils.getCurrentUser() == null ? 0 : (UserUtils.getCurrentUser().getId()) : flowInspDflzjsgzyttzs.getInserter());
		flowInspDflzjsgzyttzs.setInsertTime(new Date());
		return super.insert(flowInspDflzjsgzyttzs);

	}

	/**
	 * 重写修改方法
	 *
	 * @param flowInspDflzjsgzyttzs
	 */
	@Override
	public void update(FlowInspDflzjsgzyttzs flowInspDflzjsgzyttzs) {
		flowInspDflzjsgzyttzs.setUpdater(UserUtils.getCurrentUser() == null ? 0 : (UserUtils.getCurrentUser().getId()));
		flowInspDflzjsgzyttzs.setUpdateTime(new Date());
		super.update(flowInspDflzjsgzyttzs);

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
		paraMap.put("baseUrl", "sixsys/flowInspDflzjsgzyttzs");
		for (String id : ids.split(SysConstant.COMMA)) {
			// 监督问责参数
			paraMap.put("tableName","flow_insp_dflzjsgzyttzs");
			paraMap.put("sysType","insp");
			this.startProcess(id, "flowInspDflzjsgzyttzs", paraMap);
			//将第一个节点的任务办理完
			String pid = getMapper().selectById(id).getPid();
			String taskId = actClient.findStartTaskId(UserUtils.getCurrentUser().getId(), pid).msg;
			Map<String, Object> para = new HashMap<>(12);
			para.put("taskId", taskId);
			para.put("pId", pid);
			para.put("pKey", "flowInspDflzjsgzyttzs");
			para.put("msg", "提交");
			para.put("comment", "默认提交");
			System.out.println(para);
			actClient.handleStartTask(para);
		}
		return ReturnBean.ok("流程启动成功");

	}

	@Override
	public void doFinishBusiness(ActRollBackEntity actRollBackEntity) {
		FlowInspDflzjsgzyttzs flowInspDflzjsgzyttzs = super.findByPid(actRollBackEntity.getPid());
		String msg = actRollBackEntity.getMsg();
		if ("被约谈人签收".equals(actRollBackEntity.getTaskName()) && "已签收".equals(msg)) {
			flowInspDflzjsgzyttzs.setFlowState(FlowState.PASSED.getState()).setDocState(SysConstant.docState.finish.getDocState());
			super.update(flowInspDflzjsgzyttzs);
		}
	}

	@Override
	public void taskComplete(ActRollBackEntity actRollBackEntity) {

	}



}
