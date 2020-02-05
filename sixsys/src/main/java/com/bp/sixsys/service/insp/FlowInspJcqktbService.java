package com.bp.sixsys.service.insp;

import javax.annotation.Resource;

import com.bp.common.anno.FileAnnotation;
import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.constants.FlowState;
import com.bp.common.constants.SysConstant;
import com.bp.common.dto.ActRollBackEntity;
import com.bp.common.dto.BaseAddicts;
import com.bp.common.entity.SysUser;
import com.bp.common.utils.Common;
import com.bp.common.utils.UserIdUtils;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.client.ActClient;
import com.bp.sixsys.client.SysClient;
import com.bp.sixsys.mapper.insp.FlowInspJcqkzghfMapper;
import com.bp.sixsys.po.BaseDepartment;
import com.bp.sixsys.po.FlowRole;
import com.bp.sixsys.po.insp.FlowInspJcqkzghf;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bp.sixsys.po.insp.FlowInspJcqktb;
import com.bp.sixsys.mapper.insp.FlowInspJcqktbMapper;
import com.bp.common.base.BaseMapperUUID;
import com.bp.common.base.BaseServiceImplUUID;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pengwanli
 * @version 1.0
 * @Description: 规范权力运行检查情况通报服务层
 * @date 2019年12月02日
*/
@Service
public class FlowInspJcqktbService extends BaseServiceImplByAct<FlowInspJcqktbMapper, FlowInspJcqktb> {

	@Resource
	private FlowInspJcqktbMapper flowInspJcqktbMapper;
	@Autowired
    private FlowInspJcqkzghfService flowInspJcqkzghfService;
	@Autowired
    private FlowInspJcqkzghfMapper flowInspJcqkzghfMapper;
	@Autowired
    private SysClient sysClient;


    @Autowired
    private ActClient actClient;

	@Override
	public BaseMapperUUID<FlowInspJcqktb> getMapper() {
        request.setAttribute("sysType","insp");
        request.setAttribute("tableName","flow_insp_jcqktb");
		return flowInspJcqktbMapper;
	}

    @Override
    @Transactional(rollbackFor = Exception.class)
    @FileAnnotation(paramName = "xgwj")
    @LcnTransaction
    public String insert(FlowInspJcqktb entity) {
        entity.setFlowState(0);
        entity.setFlowSn(sysClient.getNumberFlow("ACTIVITI_SN").msg);
        entity.setDocState("新建");
        return super.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @FileAnnotation(paramName = "xgwj")
    @LcnTransaction
    public void update(FlowInspJcqktb entity) {

        super.update(entity);
    }

    /**
     * 将通报数据插入到回复信息表
     * @param actTitle
     * @param jsfzr
     * @param jsfzrName
     */
    public void insertBackInfo(String actTitle,String jsfzr,String jsfzrName){
        String[] jsfzrs = jsfzr.split(",");
        String[] jsfzrNames = jsfzrName.split(",");
        if(null!=jsfzrs){
            //遍历数据插入到规范权力运行回复表
            for (int i=0;i<jsfzrs.length;i++){
                String jcbm = jsfzrs[i];
                String bmbm = jsfzrNames[i].substring(jsfzrNames[i].indexOf("|")+1,jsfzrNames[i].lastIndexOf("|"));
                String bmmc = jsfzrNames[i].substring(jsfzrNames[i].lastIndexOf("|")+1);
                String sqr = jsfzrNames[i].substring(0,jsfzrNames[i].indexOf("|"));
                FlowInspJcqkzghf flowInspJcqkzghf=new FlowInspJcqkzghf();
                flowInspJcqkzghf.setActTitle(actTitle);
                flowInspJcqkzghf.setJcbm(jcbm);
                flowInspJcqkzghf.setSqrbmbm(bmbm);
                flowInspJcqkzghf.setSqrbm(bmmc);
                flowInspJcqkzghf.setSqr(sqr);
                flowInspJcqkzghf.setFlowState(0);
                flowInspJcqkzghf.setFlowSn(sysClient.getNumberFlow("ACTIVITI_SN").msg);
                flowInspJcqkzghf.setDocState("新建");
                Map userMap = sysClient.findUserByUserName(jcbm).data;
                if(null!=userMap){
                    flowInspJcqkzghf.setInserter(Integer.valueOf(Common.getObjStr(userMap.get("id"))));
                }
                //因为是工作流回调，没有登录用户信息所有调用mapper层直接插入
                flowInspJcqkzghfMapper.insert(flowInspJcqkzghf);
            }
        }
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
        //监察室主任意见
        paraMap.put("jcszrId", sysClient.findFlowRoleByCode("044").data.getUserIds() + ",1");
        //分管所领导
        paraMap.put("fgsldId", sysClient.findFlowRoleByCode("001").data.getUserIds() + ",1");

        paraMap.put("baseUrl", "sixsys/flowInspJcqktb");
        for (String id :
                uuids.split(",")) {
            FlowInspJcqktb flowInspJcqktb = getMapper().selectById(id);
            List userList = Arrays.asList(new String[]{"1","410","411"});
            paraMap.put("userList",userList);
            paraMap.put("baseUrl", "sixsys/flowInspJmthtz");
            paraMap.put("tableName","flow_insp_jcqktb");
            paraMap.put("sysType","insp");
            this.startProcess(id, "flowInspJcqktb", paraMap);

            //将第一个节点的任务办理完
            String pid = getMapper().selectById(id).getPid();
            String taskId = actClient.findStartTaskId(UserUtils.getCurrentUser().getId(), pid).msg;
            Map<String, Object> para = new HashMap<>(12);
            para.put("taskId", taskId);
            para.put("pId", pid);
            para.put("pKey", "flowInspJcqktb");
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
        FlowInspJcqktb flowInspJcqktb = super.findByPid(actRollBackEntity.getPid());
        //通报工作流审核完毕，下达通知到具体人员
        this.insertBackInfo(flowInspJcqktb.getActTitle(),flowInspJcqktb.getJsfzr(),flowInspJcqktb.getJsfzrName());
        //更新数据状态为已审核
        if ("分管所领导意见".equals(actRollBackEntity.getTaskName()) && "已阅".equals(msg)) {
            flowInspJcqktb.setFlowState(FlowState.PASSED.getState()).setDocState(SysConstant.docState.finish.getDocState());
            super.update(flowInspJcqktb);
        }
    }
}
