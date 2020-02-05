package com.bp.sixsys.service.hr;
import com.bp.common.base.BaseMapperUUID;
import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.constants.FlowState;
import com.bp.common.constants.SysConstant;
import com.bp.common.dto.ActRollBackEntity;
import com.bp.common.utils.DateUtil;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.client.ActClient;
import com.bp.sixsys.client.FileStoreClient;
import com.bp.sixsys.client.SysClient;
import com.bp.sixsys.mapper.hr.FlowHrGbtpMapper;
import com.bp.sixsys.po.hr.FlowHrGbtp;
import com.bp.sixsys.po.hr.BasePolice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author guoxiangxu
 * @version 1.0
 * @Description: 干部调配服务层
 * @date 2019年10月17日
 */
@Service
public class FlowHrGbtpService extends BaseServiceImplByAct<FlowHrGbtpMapper, FlowHrGbtp> {

    @Resource
    private FlowHrGbtpMapper flowHrGbtpMapper;

    @Autowired
    private FileStoreClient fileStoreClient;

    @Autowired
    private SysClient sysClient;

    @Autowired
    private ActClient actClient;


    @Override
    public BaseMapperUUID<FlowHrGbtp> getMapper() {
        request.setAttribute("sysType","hr");
        request.setAttribute("tableName","flow_hr_gbtp");
        return flowHrGbtpMapper;
    }


    /**
     * 重写新增方法
     *
     * @param flowHrGbtp
     */

    @Override
    public String insert(FlowHrGbtp flowHrGbtp) {

        flowHrGbtp.setFlowState(0);
        flowHrGbtp.setFlowSn(sysClient.getNumberFlow("ACTIVITI_SN").msg);
        flowHrGbtp.setDocState("新建");
        flowHrGbtp.setInserter(null == flowHrGbtp.getInserter() ? UserUtils.getCurrentUser() == null ? 0 : (UserUtils.getCurrentUser().getId()) : flowHrGbtp.getInserter());
        flowHrGbtp.setInsertTime(new Date());

        if (!"".equals(flowHrGbtp.getFj()) && flowHrGbtp.getFj() != null) {
            fileStoreClient.updateFileStatus(flowHrGbtp.getFj());
        }
        return super.insert(flowHrGbtp);

    }


    /**
     * 重写修改方法
     *
     * @param flowHrGbtp
     */
    @Override
    public void update(FlowHrGbtp flowHrGbtp) {
        if (flowHrGbtp.getFj() != null && !"".equals(flowHrGbtp.getFj())) {
            fileStoreClient.updateFileStatus(flowHrGbtp.getFj());
        }
        flowHrGbtp.setUpdater(UserUtils.getCurrentUser() == null ? 0 : (UserUtils.getCurrentUser().getId()));
        flowHrGbtp.setUpdateTime(new Date());
        super.update(flowHrGbtp);

    }


    /**
     * 提交审核
     *
     * @param ids
     * @return
     */
    public ReturnBean submit(String ids) {
        Map<String, Object> paraMap = new HashMap<>(12);
        // 中队
        paraMap.put("zdId", UserUtils.getCurrentUser().getId() + ",1");
        paraMap.put("dwbmId", sysClient.findFlowRoleByCode("007").data.getUserIds() + ",1");
        paraMap.put("zzcId", sysClient.findFlowRoleByCode("022").data.getUserIds() + ",1");
        paraMap.put("sdwId", sysClient.findFlowRoleByCode("035").data.getUserIds() + ",1");
        paraMap.put("bgsId", sysClient.findFlowRoleByCode("036").data.getUserIds() + ",1");
        paraMap.put("sldId", sysClient.findFlowRoleByCode("001").data.getUserIds() + ",1");
        paraMap.put("baseUrl", "sixsys/flowHrGbtp");
        for (String id : ids.split(SysConstant.COMMA)) {
//            paraMap.put("ddId", sysClient.findFlowRoleByCode("007").data.getUserIds() + ",1");
            // 监督问责参数
            paraMap.put("tableName","flow_hr_gbtp");
            paraMap.put("sysType","hr");
            this.startProcess(id, "flowHrGbtp", paraMap);
            //将第一个节点的任务办理完
            String pid = getMapper().selectById(id).getPid();
            String taskId = actClient.findStartTaskId(UserUtils.getCurrentUser().getId(), pid).msg;
            Map<String, Object> para = new HashMap<>(12);
            para.put("taskId", taskId);
            para.put("pId", pid);
            para.put("pKey", "flowHrGbtp");
            para.put("msg", "提交");
            para.put("comment", "默认提交");
            System.out.println(para);
            actClient.handleStartTask(para);
        }
        return ReturnBean.ok("流程启动成功");

    }

    @Override
    public void doFinishBusiness(ActRollBackEntity actRollBackEntity) {
        Map param = actRollBackEntity.getMap();
        FlowHrGbtp flowHrGbtp = super.findByPid(actRollBackEntity.getPid());
        String msg = actRollBackEntity.getMsg();
        if ("材料归档".equals(actRollBackEntity.getTaskName()) && "已归档".equals(msg)) {
            flowHrGbtp.setFlowState(FlowState.PASSED.getState()).setDocState(SysConstant.docState.finish.getDocState());
            super.update(flowHrGbtp);
        }
        //修改警察信息表
        String drhzw = param.get("drhzw").toString();
        String jcbm = param.get("xm").toString();
        String drdw = param.get("drbmdwjzw").toString();
        ReturnBean<BasePolice> policebyJcby = sysClient.findPolicebyJcby(jcbm);
        BasePolice data = policebyJcby.data;
        // 设置警察信息
        data.setBmbm(drdw);
        data.setXzzw(drhzw);
        sysClient.updatePolice(data);
    }

    @Override
    public FlowHrGbtp selectEntityById(String uuid) {
        return flowHrGbtpMapper.selectById(uuid);
    }


    @Override
    public FlowHrGbtp findByPid(String pid) {
        return flowHrGbtpMapper.findByPid(pid);
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
        // 实体Bean
        FlowHrGbtp flowHrGbtp = super.findByPid(actRollBackEntity.getPid());
        if (null != flowHrGbtp) {


            // 政治处意见
            if ("政治处意见".equals(taskName)) {
                String zzchynr = actRollBackEntity.getMap().get("zzchynr").toString();
                flowHrGbtp.setZzchynr(zzchynr);
                Date zzchysj = DateUtil.stringToDate("yyyy-MM-dd HH:mm:ss", actRollBackEntity.getMap().get("zzchysj").toString());
                flowHrGbtp.setZzchysj(zzchysj);
                super.update(flowHrGbtp);
            }
            else if ("办公室主任填写党委会抄告单".equals(taskName)) {
                String cgd = actRollBackEntity.getMap().get("cgd").toString();
                flowHrGbtp.setCgd(cgd);
                super.update(flowHrGbtp);
            }
              //该节点如有需求可放开 todo
//            // 办公室主任填写党委会抄告单
//            else if ("办公室主任填写党委会抄告单".equals(taskName)) {
//                String isAllno = actRollBackEntity.getMap().get("is_allno").toString();
//                flowHrGbtp.setIsAllno(isAllno);
//                super.update(flowHrGbtp);｝
        else if ("材料归档".equals(taskName)) {
                String hccl = actRollBackEntity.getMap().get("hccl").toString();
                flowHrGbtp.setHccl(hccl);
                super.update(flowHrGbtp);
            }
        }

    }


}
