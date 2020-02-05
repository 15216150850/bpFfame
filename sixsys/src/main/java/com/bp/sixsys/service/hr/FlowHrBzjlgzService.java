package com.bp.sixsys.service.hr;

import com.bp.common.anno.FileAnnotation;
import com.bp.common.base.BaseMapperUUID;
import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.dto.ActRollBackEntity;
import com.bp.common.utils.DateUtil;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.client.ActClient;
import com.bp.sixsys.client.SysClient;
import com.bp.sixsys.mapper.hr.FlowHrBzjlgzMapper;
import com.bp.sixsys.po.hr.FlowHrBzjlgz;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pengwanli
 * @version 1.0
 * @Description: 表彰奖励工作服务层
 * @date 2019年10月11日
 */
@Service
public class FlowHrBzjlgzService extends BaseServiceImplByAct<FlowHrBzjlgzMapper, FlowHrBzjlgz> {

    @Resource
    private FlowHrBzjlgzMapper flowHrBzjlgzMapper;

    @Resource
    private SysClient sysClient;

    @Autowired
    private ActClient actClient;

    @Override
    public BaseMapperUUID<FlowHrBzjlgz> getMapper() {
        request.setAttribute("sysType","sixsys");
        request.setAttribute("tableName","flow_hr_bzjlgz");
        return flowHrBzjlgzMapper;
    }

    @Override
    @FileAnnotation(paramName = "xgwj")
    public String insert(FlowHrBzjlgz entity) {
        entity.setFlowState(0);
        entity.setFlowSn(sysClient.getNumberFlow("ACTIVITI_SN").msg);
        entity.setDocState("新建");
        return super.insert(entity);
    }

    @Override
    @FileAnnotation(paramName = "xgwj")
    public void update(FlowHrBzjlgz entity) {
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
        paraMap.put("gsId", sysClient.findFlowRoleByCode("046").data.getUserIds() + ",1");
        paraMap.put("clgdId", sysClient.findFlowRoleByCode("022").data.getUserIds() + ",1");
        paraMap.put("sldId", sysClient.findFlowRoleByCode("001").data.getUserIds() + ",1");
        paraMap.put("bgsfzrId", sysClient.findFlowRoleByCode("023").data.getUserIds() + ",1");
        paraMap.put("jcsId", sysClient.findFlowRoleByCode("025").data.getUserIds() + ",1");
        paraMap.put("baseUrl", "sixsys/flowHrBzjlgz");
        for (String id : uuids.split(",")) {
            FlowHrBzjlgz flowHrBzjlgz = getMapper().selectById(id);
            this.startProcess(id, "flowHrBzjlgz", paraMap);

            // 将第一个节点的任务办理完
            String pid = getMapper().selectById(id).getPid();
            String taskId = actClient.findStartTaskId(UserUtils.getCurrentUser().getId(), pid).msg;
            Map<String, Object> para = new HashMap<>(12);
            para.put("taskId", taskId);
            para.put("pId", pid);
            para.put("pKey", "flowHrBzjlgz");
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
        FlowHrBzjlgz flowHrBzjlgz = super.findByPid(actRollBackEntity.getPid());
        if (null != flowHrBzjlgz) {
            if("政治处拟定表彰奖励方案".equals(taskName)){
                // 事前
                super.beforehand(flowHrBzjlgz);
            }
            if ("政治处意见".equals(taskName)){
                // 事中
                super.inFact(flowHrBzjlgz);
            }
            if ("材料归档".equals(taskName)){
                // 事后
                super.afterwards(flowHrBzjlgz);
            }
            Map param = actRollBackEntity.getMap();
            // 存储 政治处会议时间 and 政治处会议决定内容
            if ("政治处意见".equals(taskName)) {
                if (!StringUtils.isBlank(param.get("zzchysj").toString())) {
                    flowHrBzjlgz.setZzchysj(DateUtil.stringToDate("yyyy-MM-dd HH:mm:ss", param.get("zzchysj").toString()));
                }
                flowHrBzjlgz.setZzchyjdnr(param.getOrDefault("zzchyjdnr","").toString());
                super.update(flowHrBzjlgz);
            }
            // 存储 领导小组会议时间 and 领导小组会议决定内容
            if ("上传领导小组会议结果".equals(taskName)) {
                if (!StringUtils.isBlank(param.get("ldxzhysj").toString())) {
                    flowHrBzjlgz.setLdxzhysj(DateUtil.stringToDate("yyyy-MM-dd HH:mm:ss", param.get("ldxzhysj").toString()));
                }
                flowHrBzjlgz.setLdxzhyjdnr(param.getOrDefault("ldxzhyjdnr","").toString());
                super.update(flowHrBzjlgz);
            }
            // 存储 抄告单
            if ("办公室主任填写党委会抄告单".equals(taskName)) {
                flowHrBzjlgz.setCgdnr(param.getOrDefault("cgdnr","").toString());
                super.update(flowHrBzjlgz);
            }
            // 存储 确定材料正确且完整
            if ("材料归档".equals(taskName)) {
                flowHrBzjlgz.setQdclzqqwz(param.getOrDefault("qdclzqqwz","1").toString());
                super.update(flowHrBzjlgz);
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

    }


}
