package com.bp.sixsys.service.hr;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONArray;
import com.bp.common.anno.FileAnnotation;
import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.constants.FlowState;
import com.bp.common.constants.SysConstant;
import com.bp.common.dto.ActRollBackEntity;
import com.bp.common.utils.DateUtil;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.client.ActClient;
import com.bp.sixsys.client.SysClient;
import com.bp.sixsys.mapper.hr.FlowHrKjhbgbxbMapper;
import com.bp.sixsys.po.hr.BasePolice;
import com.bp.sixsys.po.hr.FlowHrKjhbgbxb;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bp.common.base.BaseMapperUUID;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangyu
 * @version 1.0
 * @Description: 科级后备干部选拔工作服务层
 * @date 2019年11月22日
*/
@Service
public class FlowHrKjhbgbxbService extends BaseServiceImplByAct<FlowHrKjhbgbxbMapper, FlowHrKjhbgbxb> {

	@Resource
	private FlowHrKjhbgbxbMapper flowHrKjhbgbxbMapper;

	@Override
	public BaseMapperUUID<FlowHrKjhbgbxb> getMapper() {
        request.setAttribute("tableName","flow_hr_kjhbgbxb");
        request.setAttribute("sysType","hr");
	    return flowHrKjhbgbxbMapper;
	}

    @Autowired
    private SysClient sysClient;

    @Autowired
    private ActClient actClient;
    
    @Override
    @FileAnnotation(paramName = "xgwj")
    public String insert(FlowHrKjhbgbxb entity) {
        entity.setFlowState(0);
        entity.setFlowSn(sysClient.getNumberFlow("ACTIVITI_SN").msg);
        entity.setDocState("新建");
        return super.insert(entity);
    }

    @Override
    @FileAnnotation(paramName = "xgwj")
    public void update(FlowHrKjhbgbxb entity) {
        entity.setFlowState(0);
        entity.setFlowSn(sysClient.getNumberFlow("ACTIVITI_SN").msg);
        entity.setDocState("新建");
        super.update(entity);
    }


    /**
     * 提交审核
     *
     * @param ids
     * @return
     */
    public ReturnBean submit(String ids) {
        //设置流程参数
        Map<String, Object> paraMap = new HashMap<>(12);
        // 系统用户
        paraMap.put("userId", UserUtils.getCurrentUser().getId() + ",1");
        // 政治处负责人
        paraMap.put("zzcfzr", sysClient.findFlowRoleByCode("022").data.getUserIds() + ",1");
        // 办公室负责人
        paraMap.put("bgsfzr", sysClient.findFlowRoleByCode("023").data.getUserIds() + ",1");
        // 监察室
        paraMap.put("jcsId", sysClient.findFlowRoleByCode("024").data.getUserIds() + ",1");
        // 所领导
        paraMap.put("sldId", sysClient.findFlowRoleByCode("001").data.getUserIds() + ",1");
        // 办公室主任
        paraMap.put("bgszr", sysClient.findFlowRoleByCode("036").data.getUserIds() + ",1");

        paraMap.put("baseUrl", "sixsys/flowHrKjhbgbxb");
        // 启动流程
        for (String id : ids.split(SysConstant.COMMA)) {
            // 监督问责参数
            paraMap.put("tableName","flow_hr_kjhbgbxb");
            paraMap.put("sysType","hr");
            this.startProcess(id, "flowHrKjhbgbxb", paraMap);
            String pid = getMapper().selectById(id).getPid();
            String taskId = actClient.findStartTaskId(UserUtils.getCurrentUser().getId(), pid).msg;
            Map<String, Object> para = new HashMap<>(12);
            para.put("taskId", taskId);
            para.put("pId", pid);
            para.put("pKey", "flowHrKjhbgbxb");
            para.put("msg", "提交");
            para.put("comment", "默认提交");
            System.out.println(para);
            actClient.handleStartTask(para);
        }
        return ReturnBean.ok("流程启动成功！");
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void doFinishBusiness(ActRollBackEntity actRollBackEntity) {
        FlowHrKjhbgbxb flowHrKjhbgbxb = super.findByPid(actRollBackEntity.getPid());
        if ("正科级后备干部报局政治部备案".equals(actRollBackEntity.getTaskName()) && "已备案".equals(actRollBackEntity.getMsg())) {
            flowHrKjhbgbxb.setFlowState(FlowState.PASSED.getState()).setDocState(SysConstant.docState.finish.getDocState());
            super.update(flowHrKjhbgbxb);
            // 修改领导职务
            String policeInfo = flowHrKjhbgbxb.getXbry();
            if (StringUtils.isNotBlank(policeInfo)) {
                JSONArray jsonArray = JSONArray.parseArray(policeInfo);
                if (jsonArray.size() > 0) {
                    ReturnBean returnBean = null;
                    BasePolice basePolice = null;
                    for (int i = 0; i < jsonArray.size(); i++) {
                        Map data = (Map)jsonArray.get(i);
                        returnBean = sysClient.findPolicebyJcby(data.get("jcbm").toString());
                        if (null != returnBean){
                            basePolice = (BasePolice)returnBean.data;
                            String tbzw = data.get("tbzw").toString();
                            if (null != basePolice && StringUtils.isNumeric(tbzw)) {
                                basePolice.setXzzw(tbzw);
                                sysClient.updatePolice(basePolice);
                            }
                        }
                    }
                }
            }
        }
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
        FlowHrKjhbgbxb flowHrKjhbgbxb = super.findByPid(actRollBackEntity.getPid());
        if (null != flowHrKjhbgbxb) {
            if("系统用户".equals(taskName)){
                // 事前
                super.beforehand(flowHrKjhbgbxb);
            }
            if ("政治处选拔任用工作方案".equals(taskName) || "民主推荐情况".equals(taskName)
                    || "综治办意见".equals(taskName) || "监察室意见".equals(taskName)
                    || "公示".equals(taskName)){
                // 事中
                super.inFact(flowHrKjhbgbxb);
            }
            if ("所党委意见".equals(taskName)) {
                // 事后
                super.afterwards(flowHrKjhbgbxb);
            }

            Map param = actRollBackEntity.getMap();
            // 存储 政治会议时间 和 会议内容
            if ("政治处选拔任用工作方案".equals(taskName)) {
                if (StringUtils.isNotBlank(param.get("zzchysj").toString())) {
                    flowHrKjhbgbxb.setZzchysj(DateUtil.stringToDate("yyyy-MM-dd", param.get("zzchysj").toString()));
                }
                flowHrKjhbgbxb.setZzchynr(param.getOrDefault("zzchynr","").toString());
                super.update(flowHrKjhbgbxb);
            }
            // 存储 领导小组会议时间 和 会议内容
            if ("上传领导小组会议结果".equals(taskName)) {
                if (StringUtils.isNotBlank(param.get("ldxzhysj").toString())) {
                    flowHrKjhbgbxb.setLdxzhysj(DateUtil.stringToDate("yyyy-MM-dd", param.get("ldxzhysj").toString()));
                }
                flowHrKjhbgbxb.setLdxzhynr(param.getOrDefault("ldxzhynr","").toString());
                super.update(flowHrKjhbgbxb);
            }
            // 存储 抄告单
            else if ("办公室主任填写党委会议抄告单".equals(taskName)) {
                flowHrKjhbgbxb.setCgd(param.getOrDefault("cgd", "").toString());
                super.update(flowHrKjhbgbxb);
            }
            // 退回，清空流程业务字段
            if ("退回".equals(status)) {
                flowHrKjhbgbxb.setZzchysj(null).setZzchynr("")
                        .setLdxzhysj(null).setLdxzhynr("")
                        .setCgd("");
                super.update(flowHrKjhbgbxb);
            }
        }
    }
}
