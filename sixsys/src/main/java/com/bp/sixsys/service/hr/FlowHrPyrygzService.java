package com.bp.sixsys.service.hr;

import com.bp.common.anno.FileAnnotation;
import com.bp.common.base.BaseMapperUUID;
import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.constants.SysConstant;
import com.bp.common.dto.ActRollBackEntity;
import com.bp.common.entity.SysUser;
import com.bp.common.utils.Common;
import com.bp.common.utils.DateUtil;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.client.ActClient;
import com.bp.sixsys.client.SysClient;
import com.bp.sixsys.mapper.hr.FlowHrPyrygzMapper;
import com.bp.sixsys.po.BaseDepartment;
import com.bp.sixsys.po.hr.FlowHrPyrygz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pengwanli
 * @version 1.0
 * @Description: 聘用人员工作服务层
 * @date 2019年10月11日
 */
@Service
public class FlowHrPyrygzService extends BaseServiceImplByAct<FlowHrPyrygzMapper, FlowHrPyrygz> {

    @Resource
    private FlowHrPyrygzMapper FlowHrPyrygzMapper;

    @Resource
    private SysClient sysClient;

    @Autowired
    private ActClient actClient;

    @Override
    public BaseMapperUUID<FlowHrPyrygz> getMapper() {
        request.setAttribute("sysType","hr");
        request.setAttribute("tableName","flow_hr_pyrygz");
        return FlowHrPyrygzMapper;
    }

    @Override
    public ReturnBean<List<FlowHrPyrygz>> selectList(Map para) {
        SysUser sysUser = UserUtils.getCurrentUser();
        BaseDepartment department = sysClient.findByBmbm(sysUser.getOrganizationCode()).data;
        if (SysConstant.bmlx.three.getType().equals(Common.getObjStr(department == null ? "" : department.getBmlx())) || SysConstant.bmlx.four.getType().equals(Common.getObjStr(department == null ? "" : department.getBmlx()))) {
            //如果登录用户是大队/中队,则查自己部门下用户戒毒人员
            para.put("bmbmChange", department.getBmbm());
        }
        return super.selectList(para);
    }

    @Override
    @FileAnnotation(paramName = "xgwj")
    public String insert(FlowHrPyrygz entity) {
        entity.setFlowState(0);
        entity.setFlowSn(sysClient.getNumberFlow("ACTIVITI_SN").msg);
        entity.setDocState("新建");
        return super.insert(entity);
    }

    @Override
    @FileAnnotation(paramName = "xgwj")
    public void update(FlowHrPyrygz entity) {
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
        paraMap.put("mjId", UserUtils.getCurrentUser().getId() + ",1");
        paraMap.put("zzcfzrId", sysClient.findFlowRoleByCode("022").data.getUserIds() + ",1");
        paraMap.put("sldId", sysClient.findFlowRoleByCode("001").data.getUserIds() + ",1");
        paraMap.put("bgsfzrId", sysClient.findFlowRoleByCode("023").data.getUserIds() + ",1");
        paraMap.put("ddId", sysClient.findFlowRoleByCode("007").data.getUserIds() + ",1");
        paraMap.put("baseUrl", "sixsys/flowHrPyrygz");
        for (String id :
                uuids.split(",")) {
            FlowHrPyrygz FlowHrPyrygz = getMapper().selectById(id);
            paraMap.put("tableName", "flow_hr_pyrygz");
            paraMap.put("sysType", "hr");
            this.startProcess(id, "flowHrPyrygz", paraMap);

            //将第一个节点的任务办理完
            String pid = getMapper().selectById(id).getPid();
            String taskId = actClient.findStartTaskId(UserUtils.getCurrentUser().getId(), pid).msg;
            Map<String, Object> para = new HashMap<>(12);
            para.put("taskId", taskId);
            para.put("pId", pid);
            para.put("pKey", "flowHrPyrygz");
            para.put("msg", "提交");
            para.put("comment", "默认提交");
            actClient.handleStartTask(para);
        }
        return ReturnBean.ok("流程启动成功");
    }

    /**
     * 流程节点回调
     *
     * @param actRollBackEntity
     */
    @Override
    public void taskComplete(ActRollBackEntity actRollBackEntity) {
        String taskName = actRollBackEntity.getTaskName();
        FlowHrPyrygz flowHrPyrygz = super.findByPid(actRollBackEntity.getPid());
        if (null != flowHrPyrygz) {
            Map param = actRollBackEntity.getMap();
            if ("民警".equals(taskName)) {
                // 事前
                super.beforehand(flowHrPyrygz);
            }
            if ("政治处意见".equals(taskName)){
                // 事中
                super.inFact(flowHrPyrygz);
            }
            if ("材料归档".equals(taskName)){
                // 事后
                super.afterwards(flowHrPyrygz);
            }
            if ("发布招聘公告".equals(taskName)) {
                flowHrPyrygz.setBmrys(param.get("bmrys").toString());
                super.update(flowHrPyrygz);
            }
            if ("政治处意见".equals(taskName)) {
                flowHrPyrygz.setZzchysj(DateUtil.stringToDate("yyyy-MM-dd",param.get("zzchysj").toString()));
                flowHrPyrygz.setZzchyjdnr(param.get("zzchyjdnr").toString());
                super.update(flowHrPyrygz);
            }
            if ("上传领导小组会议结果".equals(taskName)) {
                flowHrPyrygz.setLdxzhysj(DateUtil.stringToDate("yyyy-MM-dd",param.get("ldxzhysj").toString()));
                flowHrPyrygz.setLdxzhyjdnr(param.get("ldxzhyjdnr").toString());
                flowHrPyrygz.setZpryjqgzdy(param.get("zpryjqgzdy").toString());
                super.update(flowHrPyrygz);
            }
            if ("材料归档".equals(taskName)) {
                flowHrPyrygz.setQdclzqqwz(param.get("qdclzqqwz").toString());
                super.update(flowHrPyrygz);
            }
        }
    }


    /**
     * 流程结束回调
     *
     * @param actRollBackEntity
     */
    @Override
    public void doFinishBusiness(ActRollBackEntity actRollBackEntity) {
        FlowHrPyrygz FlowHrPyrygz = super.findByPid(actRollBackEntity.getPid());
        String msg = actRollBackEntity.getMsg();
        if ("上传任职文件".equals(actRollBackEntity.getTaskName()) && "已上传".equals(msg)) {
            FlowHrPyrygz.setFlowState(2).setDocState(SysConstant.docState.finish.getDocState());
            super.update(FlowHrPyrygz);
        }
    }

}
