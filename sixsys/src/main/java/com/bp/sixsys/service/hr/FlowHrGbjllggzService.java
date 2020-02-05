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
import com.bp.sixsys.po.BaseDepartment;
import com.bp.sixsys.mapper.hr.FlowHrGbjllggzMapper;
import com.bp.sixsys.po.hr.FlowHrGbjllggz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pengwanli
 * @version 1.0
 * @Description: 干部交流轮岗工作服务层
 * @date 2019年10月10日
 */
@Service
public class FlowHrGbjllggzService extends BaseServiceImplByAct<FlowHrGbjllggzMapper, FlowHrGbjllggz> {

    @Resource
    private FlowHrGbjllggzMapper flowHrGbjllggzMapper;

    @Resource
    private SysClient sysClient;

    @Autowired
    private ActClient actClient;

    @Override
    public BaseMapperUUID<FlowHrGbjllggz> getMapper() {
        request.setAttribute("sysType","sixsys");
        request.setAttribute("tableName","flow_hr_gbjllggz");
        return flowHrGbjllggzMapper;
    }

    @Override
    public ReturnBean<List<FlowHrGbjllggz>> selectList(Map para) {
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
    public String insert(FlowHrGbjllggz entity) {
        entity.setFlowState(0);
        entity.setFlowSn(sysClient.getNumberFlow("ACTIVITI_SN").msg);
        entity.setDocState("新建");
        return super.insert(entity);
    }

    @Override
    @FileAnnotation(paramName = "xgwj")
    public void update(FlowHrGbjllggz entity) {
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
        paraMap.put("baseUrl", "sixsys/flowHrGbjllggz");
        for (String id :
                uuids.split(",")) {
            FlowHrGbjllggz FlowHrGbjllggz = getMapper().selectById(id);
            this.startProcess(id, "flowHrGbjllggz", paraMap);

            //将第一个节点的任务办理完
            String pid = getMapper().selectById(id).getPid();
            String taskId = actClient.findStartTaskId(UserUtils.getCurrentUser().getId(), pid).msg;
            Map<String, Object> para = new HashMap<>(12);
            para.put("taskId", taskId);
            para.put("pId", pid);
            para.put("pKey", "flowHrGbjllggz");
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
        FlowHrGbjllggz flowHrGbjllggz = super.findByPid(actRollBackEntity.getPid());
        if (null != flowHrGbjllggz) {
            Map param = actRollBackEntity.getMap();
            if ("民警".equals(taskName)) {
                // 事前
                super.beforehand(flowHrGbjllggz);
            }
            if ("政治处意见".equals(taskName)){
                // 事中
                super.inFact(flowHrGbjllggz);
            }
            if ("办理调动手续".equals(taskName)){
                // 事后
                super.afterwards(flowHrGbjllggz);
            }
            if ("政治处意见".equals(taskName)) {
                flowHrGbjllggz.setZzchysj(DateUtil.stringToDate("yyyy-MM-dd",param.get("zzchysj").toString()));
                flowHrGbjllggz.setZzchyjdnr(param.get("zzchyjdnr").toString());
                super.update(flowHrGbjllggz);
            }
            if ("办公室主任填写党委会抄告单".equals(taskName)) {
                flowHrGbjllggz.setCgdnr(param.get("cgdnr").toString());
                super.update(flowHrGbjllggz);
            }
            if ("上传领导小组会议结果".equals(taskName)) {
                flowHrGbjllggz.setLdxzhysj(DateUtil.stringToDate("yyyy-MM-dd",param.get("ldxzhysj").toString()));
                flowHrGbjllggz.setLdxzhyjdnr(param.get("ldxzhyjdnr").toString());
                super.update(flowHrGbjllggz);
            }
            // 存储 确定材料正确且完整
            if ("办理调动手续".equals(taskName)) {
                flowHrGbjllggz.setQdclzqqwz(param.get("qdclzqqwz").toString());
                super.update(flowHrGbjllggz);
            }
        }
    }

    @Override
    public void doFinishBusiness(ActRollBackEntity actRollBackEntity) {
        String msg = actRollBackEntity.getMsg();
        FlowHrGbjllggz FlowHrGbjllggz = super.findByPid(actRollBackEntity.getPid());
        if ("办理调动手续".equals(actRollBackEntity.getTaskName()) && "已办理".equals(msg)) {
            FlowHrGbjllggz.setFlowState(2).setDocState(SysConstant.docState.finish.getDocState());;
            super.update(FlowHrGbjllggz);
        }
    }


}
