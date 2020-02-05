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
import com.bp.sixsys.mapper.hr.FlowHrNdkhgzMapper;
import com.bp.sixsys.po.hr.FlowHrNdkhgz;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pengwanli
 * @version 1.0
 * @Description: 年度考核工作服务层
 * @date 2019年10月10日
 */
@Service
public class FlowHrNdkhgzService extends BaseServiceImplByAct<FlowHrNdkhgzMapper, FlowHrNdkhgz> {

    @Resource
    private FlowHrNdkhgzMapper flowHrNdkhgzMapper;

    @Resource
    private SysClient sysClient;

    @Autowired
    private ActClient actClient;

    @Override
    public BaseMapperUUID<FlowHrNdkhgz> getMapper() {
        request.setAttribute("sysType","sixsys");
        request.setAttribute("tableName","flow_hr_ndkhgz");
        return flowHrNdkhgzMapper;
    }

    @Override
    public ReturnBean<List<FlowHrNdkhgz>> selectList(Map para) {
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
    public String insert(FlowHrNdkhgz entity) {
        entity.setFlowState(0);
        entity.setFlowSn(sysClient.getNumberFlow("ACTIVITI_SN").msg);
        entity.setDocState("新建");
        return super.insert(entity);
    }

    @Override
    @FileAnnotation(paramName = "xgwj")
    public void update(FlowHrNdkhgz entity) {
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
        paraMap.put("baseUrl", "sixsys/flowHrNdkhgz");
        for (String id :
                uuids.split(",")) {
            FlowHrNdkhgz FlowHrNdkhgz = getMapper().selectById(id);
            this.startProcess(id, "flowHrNdkhgz", paraMap);

            //将第一个节点的任务办理完
            String pid = getMapper().selectById(id).getPid();
            String taskId = actClient.findStartTaskId(UserUtils.getCurrentUser().getId(), pid).msg;
            Map<String, Object> para = new HashMap<>(12);
            para.put("taskId", taskId);
            para.put("pId", pid);
            para.put("pKey", "flowHrNdkhgz");
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
        // 流程节点名称
        String taskName = actRollBackEntity.getTaskName();
        FlowHrNdkhgz flowHrNdkhgz = super.findByPid(actRollBackEntity.getPid());
        if (null != flowHrNdkhgz) {
            if("民警".equals(taskName)){
                // 事前
                super.beforehand(flowHrNdkhgz);
            }
            if ("政治处制定年度考核工作方案".equals(taskName)){
                // 事中
                super.inFact(flowHrNdkhgz);
            }
            if ("材料归档".equals(taskName)){
                // 事后
                super.afterwards(flowHrNdkhgz);
            }
            Map param = actRollBackEntity.getMap();
            // 存储 政治处会议时间 and 政治处会议决定内容
            if ("政治处意见".equals(taskName)) {
                if (!StringUtils.isBlank(param.get("zzchysj").toString())) {
                    flowHrNdkhgz.setZzchysj(DateUtil.stringToDate("yyyy-MM-dd HH:mm:ss", param.get("zzchysj").toString()));
                }
                flowHrNdkhgz.setZzchyjdnr(param.getOrDefault("zzchyjdnr", "").toString());
                super.update(flowHrNdkhgz);
            }
            // 存储 领导小组会议时间 and 领导小组会议决定内容
            if ("上传领导小组会议结果".equals(taskName)) {
                if (!StringUtils.isBlank(param.get("ldxzhysj").toString())) {
                    flowHrNdkhgz.setLdxzhysj(DateUtil.stringToDate("yyyy-MM-dd HH:mm:ss", param.get("ldxzhysj").toString()));
                }
                flowHrNdkhgz.setLdxzhyjdnr(param.getOrDefault("ldxzhyjdnr","").toString());
                super.update(flowHrNdkhgz);
            }
            // 存储 抄告单
            if ("办公室主任填写党委会抄告单".equals(taskName)) {
                flowHrNdkhgz.setCgdnr(param.getOrDefault("cgdnr","").toString());
                super.update(flowHrNdkhgz);
            }
            // 存储 确定材料正确且完整
            if ("材料归档".equals(taskName)) {
                flowHrNdkhgz.setQdclzqqwz(param.getOrDefault("qdclzqqwz","1").toString());
                super.update(flowHrNdkhgz);
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
        FlowHrNdkhgz flowHrNdkhgz = super.findByPid(actRollBackEntity.getPid());
        String msg = actRollBackEntity.getMsg();
        if ("材料归档".equals(actRollBackEntity.getTaskName()) && "已归档".equals(msg)) {
            flowHrNdkhgz.setFlowState(2).setDocState(SysConstant.docState.finish.getDocState());
            super.update(flowHrNdkhgz);
        }
    }

}
