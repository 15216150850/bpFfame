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
import com.bp.sixsys.mapper.insp.FlowInspJcjytzsMapper;
import com.bp.sixsys.po.hr.FlowHrLdgbjqsxb;
import com.bp.sixsys.po.insp.FlowInspJcjytzs;
import com.bp.sixsys.po.insp.FlowInspJcjytzs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @author guoxiangxu
 * @version 1.0
 * @Description: 监查建议通知书服务层
 * @date 2019年12月02日
*/
@Service
public class FlowInspJcjytzsService extends BaseServiceImplByAct<FlowInspJcjytzsMapper, FlowInspJcjytzs> {

	@Resource
	private FlowInspJcjytzsMapper flowInspJcjytzsMapper;



	@Autowired
	private SysClient sysClient;

	@Autowired
	private ActClient actClient;
	
	@Override
	public BaseMapperUUID<FlowInspJcjytzs> getMapper() {
		request.setAttribute("sysType","insp");
		request.setAttribute("tableName","flow_insp_jcjytzs");
		return flowInspJcjytzsMapper;
	}


	/**
	 * 重写新增方法
	 *
	 * @param flowInspJcjytzs
	 */

	@Override
	public String insert(FlowInspJcjytzs flowInspJcjytzs) {

		flowInspJcjytzs.setFlowState(0);
		flowInspJcjytzs.setFlowSn(sysClient.getNumberFlow("ACTIVITI_SN").msg);
		flowInspJcjytzs.setDocState("新建");
		flowInspJcjytzs.setUuid(IdUtils.getUuid());
		flowInspJcjytzs.setInserter(null == flowInspJcjytzs.getInserter() ? UserUtils.getCurrentUser() == null ? 0 : (UserUtils.getCurrentUser().getId()) : flowInspJcjytzs.getInserter());
		flowInspJcjytzs.setInsertTime(new Date());
		return super.insert(flowInspJcjytzs);

	}

	/**
	 * 重写修改方法
	 *
	 * @param flowInspJcjytzs
	 */
	@Override
	public void update(FlowInspJcjytzs flowInspJcjytzs) {
		//!!!!!!
		flowInspJcjytzs.setUpdater(UserUtils.getCurrentUser() == null ? 0 : (UserUtils.getCurrentUser().getId()));
		flowInspJcjytzs.setUpdateTime(new Date());
		super.update(flowInspJcjytzs);

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
		paraMap.put("jcsId", sysClient.findFlowRoleByCode("024").data.getUserIds() + ",1");
		paraMap.put("dwfzrId", sysClient.findFlowRoleByCode("007").data.getUserIds() + ",1");
		paraMap.put("baseUrl", "sixsys/flowInspJcjytzs");
		for (String id : ids.split(SysConstant.COMMA)) {
			// 监督问责参数
			paraMap.put("tableName","flow_insp_jcjytzs");
			paraMap.put("sysType","insp");
			this.startProcess(id, "flowInspJcjytzs", paraMap);
			//将第一个节点的任务办理完
			String pid = getMapper().selectById(id).getPid();
			String taskId = actClient.findStartTaskId(UserUtils.getCurrentUser().getId(), pid).msg;
			Map<String, Object> para = new HashMap<>(12);
			para.put("taskId", taskId);
			para.put("pId", pid);
			para.put("pKey", "flowInspJcjytzs");
			para.put("msg", "提交");
			para.put("comment", "默认提交");
			System.out.println(para);
			actClient.handleStartTask(para);
		}
		return ReturnBean.ok("流程启动成功");

	}





	@Override
	public void doFinishBusiness(ActRollBackEntity actRollBackEntity) {

		FlowInspJcjytzs flowInspJcjytzs = super.findByPid(actRollBackEntity.getPid());
		String msg = actRollBackEntity.getMsg();
		if ("回复情况审核".equals(actRollBackEntity.getTaskName()) && "已审核".equals(msg)) {
			flowInspJcjytzs.setFlowState(FlowState.PASSED.getState()).setDocState(SysConstant.docState.finish.getDocState());
			super.update(flowInspJcjytzs);
		}
		
	}

	@Override
	public void taskComplete(ActRollBackEntity actRollBackEntity) {

		// 流程节点名称
		String taskName = actRollBackEntity.getTaskName();
		String status = actRollBackEntity.getMsg();
		// 实体Bean
		FlowInspJcjytzs flowInspJcjytzs = findByPid(actRollBackEntity.getPid());
		if (null != flowInspJcjytzs) {
			// 办理进入手续
			if ("回复情况".equals(taskName)) {
				String drjc = actRollBackEntity.getMap().get("jcjyhf").toString();
				flowInspJcjytzs.setJcjyhf(drjc);
				super.update(flowInspJcjytzs);
			}
			if ("回复情况审核".equals(taskName)) {
				String wenhao = actRollBackEntity.getMap().get("wenhao").toString();
				flowInspJcjytzs.setWenhao(wenhao);
				super.update(flowInspJcjytzs);

			}
		}
	}

	@Override
	public FlowInspJcjytzs selectEntityById(String uuid){
		return flowInspJcjytzsMapper.selectEntityById(uuid);
	}

	@Override
	public FlowInspJcjytzs findByPid(String pid) {
		return flowInspJcjytzsMapper.findByPid(pid);
	}

}
