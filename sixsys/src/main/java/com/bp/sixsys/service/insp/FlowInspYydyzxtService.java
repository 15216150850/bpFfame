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
import com.bp.sixsys.po.insp.FlowInspMjcbhsxqsy;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bp.sixsys.po.insp.FlowInspYydyzxt;
import com.bp.sixsys.mapper.insp.FlowInspYydyzxtMapper;
import com.bp.common.base.BaseMapperUUID;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pengwanli
 * @version 1.0
 * @Description: 运用第一种形态通知单服务层
 * @date 2019年12月02日
*/
@Service
public class FlowInspYydyzxtService extends BaseServiceImplByAct<FlowInspYydyzxtMapper,FlowInspYydyzxt> {

	@Resource
	private FlowInspYydyzxtMapper flowInspYydyzxtMapper;

    @Resource
    private SysClient sysClient;

    @Autowired
    private ActClient actClient;

	@Override
	public BaseMapperUUID<FlowInspYydyzxt> getMapper() {
        request.setAttribute("sysType","insp");
        request.setAttribute("tableName","flow_insp_yydyzxt");
		return flowInspYydyzxtMapper;
	}

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LcnTransaction
    public String insert(FlowInspYydyzxt entity) {
        entity.setFlowState(0);
        entity.setFlowSn(sysClient.getNumberFlow("ACTIVITI_SN").msg);
        entity.setDocState("新建");
        return super.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LcnTransaction
    public void update(FlowInspYydyzxt entity) {
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

        paraMap.put("baseUrl", "sixsys/flowInspYydyzxt");
        for (String id :
                uuids.split(",")) {
            FlowInspYydyzxt flowInspYydyzxt = getMapper().selectById(id);
            // 设置网关条件
            paraMap = setGatewayParam(flowInspYydyzxt, paraMap);
            //运用对象确认
            paraMap.put("yydxId", sysClient.findUserByUserName(flowInspYydyzxt.getYydx()).data.get("id")  + ",1");
            if(flowInspYydyzxt.getZbsj() == null){
                //支部书记确认
                paraMap.put("isZbsj",true);
            }else {
                //支部书记确认
                paraMap.put("zbsjId", sysClient.findUserByUserName(flowInspYydyzxt.getZbsj()).data.get("id") + ",1");
                //支部书记确认
                paraMap.put("isZbsj",false);
            }

            paraMap.put("tableName","flow_insp_yydyzxt");
            paraMap.put("sysType","insp");
            this.startProcess(id, "flowInspYydyzxt", paraMap);

            //将第一个节点的任务办理完
            String pid = getMapper().selectById(id).getPid();
            String taskId = actClient.findStartTaskId(UserUtils.getCurrentUser().getId(), pid).msg;
            Map<String, Object> para = new HashMap<>(12);
            para.put("taskId", taskId);
            para.put("pId", pid);
            para.put("pKey", "flowInspYydyzxt");
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

    }

    /**
     * 手动设置网关条件
     * @param flowInspYydyzxt
     * @param param
     * @return
     */
    private Map<String, Object>  setGatewayParam(FlowInspYydyzxt flowInspYydyzxt,Map<String, Object> param){
        String zbsj = flowInspYydyzxt.getZbsj();
        // 支部书记不为空
        if (zbsj != null){
            param.put("exclusivegateway1", "${!isZbsj}");
        }else {
            param.put("exclusivegateway1", "${isZbsj}");
        }

        return param;
    }
}
