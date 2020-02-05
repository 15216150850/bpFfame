package com.bp.sixsys.service.hr;

import javax.annotation.Resource;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bp.common.anno.FileAnnotation;
import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.constants.FlowState;
import com.bp.common.constants.MsgConstant;
import com.bp.common.constants.SysConstant;
import com.bp.common.dto.ActRollBackEntity;
import com.bp.common.dto.MsgDto;
import com.bp.common.entity.SysUser;
import com.bp.common.utils.DateUtil;
import com.bp.common.utils.UserIdUtils;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.client.ActClient;
import com.bp.sixsys.client.Msgclient;
import com.bp.sixsys.client.SysClient;
import com.bp.sixsys.po.BaseDepartment;
import com.bp.sixsys.po.FlowRole;
import com.bp.sixsys.po.hr.BasePolice;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bp.sixsys.po.hr.FlowHrZdjldgbxb;
import com.bp.sixsys.mapper.hr.FlowHrZdjldgbxbMapper;
import com.bp.common.base.BaseMapperUUID;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangyu
 * @version 1.0
 * @Description: 中队级领导干部选拔任用工作服务层
 * @date 2019年11月21日
*/
@Service
public class FlowHrZdjldgbxbService extends BaseServiceImplByAct<FlowHrZdjldgbxbMapper,FlowHrZdjldgbxb> {

	@Resource
	private FlowHrZdjldgbxbMapper flowHrZdjldgbxbMapper;

	@Autowired
	private Msgclient msgclient;

	@Override
	public BaseMapperUUID<FlowHrZdjldgbxb> getMapper() {
        request.setAttribute("tableName","flow_hr_zdjldgbxb");
        request.setAttribute("sysType","hr");
	    return flowHrZdjldgbxbMapper;
	}

    @Autowired
    private SysClient sysClient;

    @Autowired
    private ActClient actClient;

    @Override
    @FileAnnotation(paramName = "xgwj")
    public String insert(FlowHrZdjldgbxb entity) {
        entity.setFlowState(0);
        entity.setFlowSn(sysClient.getNumberFlow("ACTIVITI_SN").msg);
        entity.setDocState("新建");
        return super.insert(entity);
    }

    @Override
    @FileAnnotation(paramName = "xgwj")
    public void update(FlowHrZdjldgbxb entity) {
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
        // 中队民警 todo 需要确认是否为中队提交
        paraMap.put("zdId", UserUtils.getCurrentUser().getId() + ",1");
        // 政治处负责人
        paraMap.put("zzcfzr", sysClient.findFlowRoleByCode("022").data.getUserIds() + ",1");
        // 办公室负责人
        paraMap.put("bgsfzr", sysClient.findFlowRoleByCode("023").data.getUserIds() + ",1");
        // 监察室
        paraMap.put("jcsId", sysClient.findFlowRoleByCode("024").data.getUserIds() + ",1");
        // 所领导
        paraMap.put("sldId", sysClient.findFlowRoleByCode("001").data.getUserIds() + ",1");
        // 法制科
        paraMap.put("fzkId", sysClient.findFlowRoleByCode("005").data.getUserIds() + ",1");
        paraMap.put("baseUrl", "sixsys/flowHrZdjldgbxb");
        // 启动流程
        for (String id : ids.split(SysConstant.COMMA)) {
            // 大队
            String bmbm = UserUtils.getCurrentUser().getOrganizationCode();
            ReturnBean<FlowRole> byCode = sysClient.findFlowRoleByCode("007");
            String userIds = "," + byCode.data.getUserIds() + ",";
            ReturnBean<BaseDepartment> byBmbm = sysClient.findByBmbm(bmbm);
            // 默认取中队的上级部门编码
            String currentBmbm = byBmbm.data.getSjbmbm();
            // 如果不是中队提交，则取当前部门编码
            if (byBmbm.data.getBmlx().equals("03")) {
                currentBmbm = bmbm;
            }
            List<SysUser> sysUsers = sysClient.findFlowRoleByUserIdsAndBmbm(userIds, currentBmbm);
            paraMap.put("ddId", UserIdUtils.idSplit(UserIdUtils.getUserIdList(sysUsers)) + ",1");

            // 监督问责参数
            paraMap.put("tableName","flow_hr_zdjldgbxb");
            paraMap.put("sysType","hr");
            this.startProcess(id, "flowHrZdjldgbxb", paraMap);
            String pid = getMapper().selectById(id).getPid();
            String taskId = actClient.findStartTaskId(UserUtils.getCurrentUser().getId(), pid).msg;
            Map<String, Object> para = new HashMap<>(12);
            para.put("taskId", taskId);
            para.put("pId", pid);
            para.put("pKey", "flowHrZdjldgbxb");
            para.put("msg", "提交");
            para.put("comment", "默认提交");
            actClient.handleStartTask(para);
        }
        return ReturnBean.ok("流程启动成功！");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void doFinishBusiness(ActRollBackEntity actRollBackEntity) {
        FlowHrZdjldgbxb flowHrZdjldgbxb = super.findByPid(actRollBackEntity.getPid());
        String msg = actRollBackEntity.getMsg();
        if ("上传任职文件".equals(actRollBackEntity.getTaskName()) && "已上传".equals(msg)) {
            flowHrZdjldgbxb.setFlowState(FlowState.PASSED.getState()).setDocState(SysConstant.docState.finish.getDocState());
            super.update(flowHrZdjldgbxb);
            // 修改领导职务
            String policeInfo = flowHrZdjldgbxb.getJcxx();
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
                            String nrzw = data.get("nrzw").toString();
                            if (null != basePolice && StringUtils.isNumeric(nrzw)) {
                                basePolice.setXzzw(nrzw);
                                sysClient.updatePolice(basePolice);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void taskComplete(ActRollBackEntity actRollBackEntity) {
        // 流程节点名称
        String taskName = actRollBackEntity.getTaskName();
        String status = actRollBackEntity.getMsg();
        // 实体Bean
        FlowHrZdjldgbxb flowHrZdjldgbxb = super.findByPid(actRollBackEntity.getPid());
        if (null != flowHrZdjldgbxb) {
            if("中队民警".equals(taskName)){
                // 事前
                super.beforehand(flowHrZdjldgbxb);
            }
            if ("大队支委会意见".equals(taskName) || "综治办意见".equals(taskName) || "监察室意见".equals(taskName) || "大队公示".equals(taskName)){
                // 事中
                super.inFact(flowHrZdjldgbxb);
            }
            if ("上传任职文件".equals(taskName)) {
                // 事后
                super.afterwards(flowHrZdjldgbxb);
            }

            Map param = actRollBackEntity.getMap();
            // 存储 支委会召开时间 和 支委会意见内容
            if ("大队支委会意见".equals(taskName)) {
                if (StringUtils.isNotBlank(param.get("zwhzksj").toString())) {
                    flowHrZdjldgbxb.setZwhzksj(DateUtil.stringToDate("yyyy-MM-dd", param.get("zwhzksj").toString()));
                }
                flowHrZdjldgbxb.setZwhyjnr(param.getOrDefault("zwhyjnr","").toString());
                super.update(flowHrZdjldgbxb);
            }
            // 存储 政治处会议时间 和 政治处会议内容
            else if ("政治处意见".equals(taskName)) {
                if (StringUtils.isNotBlank(param.get("zzchysj").toString())) {
                    flowHrZdjldgbxb.setZzchysj(DateUtil.stringToDate("yyyy-MM-dd", param.get("zzchysj").toString()));
                }
                flowHrZdjldgbxb.setZzchynr(param.getOrDefault("zzchynr","").toString());
                super.update(flowHrZdjldgbxb);
            }
            // 存储 领导小组会议时间 和 会议内容
            else if ("上传领导小组会议结果".equals(taskName)) {
                if(StringUtils.isNotBlank(param.get("ldxzhysj").toString())){
                    flowHrZdjldgbxb.setLdxzhysj(DateUtil.stringToDate("yyyy-MM-dd", param.get("ldxzhysj").toString()));
                }
                flowHrZdjldgbxb.setLdxzhynr(param.getOrDefault("ldxzhynr","").toString());
                super.update(flowHrZdjldgbxb);
            }
            // 领导小组意见节点，同意后生成一条公告(所有人可见)
            else if ("领导小组意见".equals(taskName) && "同意".equals(status)) {
                String jcxxStr = formatJcxx(flowHrZdjldgbxb.getJcxx());
                String title = "关于“" + flowHrZdjldgbxb.getActTitle() + "”流程审批通过,特此通报公示";
                Date msgStartDate = new Date();
                Date msgEndDate = DateUtil.dateAdd("day", 5, msgStartDate);
                String content = "经内部会议慎重讨论决定,“" + flowHrZdjldgbxb.getActTitle() + "”流程提议被采纳。以下警员职务变更如下：" + jcxxStr + "。此公告公示五天后(即" + DateUtil.dateToString("yyyy-MM-dd", msgEndDate) + "后)职务变更正式生效,特此通告。";
                ReturnBean returnBean = msgclient.sendMsg(new MsgDto(title, "", "", MsgConstant.RECEIVE_TYPE_ALL, content, MsgConstant.MSG_SIGN_XXTZ, "1", "", ""));
                if (null != returnBean.data) {
                    Map data = (Map)returnBean.data;
                    String msgUuid = data.get("uuid").toString();
                    flowHrZdjldgbxb.setMsgUuid(msgUuid).setMsgStartDate(msgStartDate).setMsgEndDate(msgEndDate);
                    super.update(flowHrZdjldgbxb);
                }
            }
            // 存储 确定已公示5个工作日(0:否,1:是)
            else if ("大队公示".equals(taskName)) {
                flowHrZdjldgbxb.setGs(Integer.parseInt(param.getOrDefault("gs", "1").toString()));
                super.update(flowHrZdjldgbxb);
            }
            // 存储 确定材料正确且完整(0:否,1:是)
            else if ("上传任职文件".equals(taskName)) {
                flowHrZdjldgbxb.setHccl(Integer.parseInt(param.getOrDefault("hccl", "1").toString()));
                super.update(flowHrZdjldgbxb);
            }
            // 退回，清空流程业务字段
            if ("退回".equals(status)) {
                flowHrZdjldgbxb.setZwhzksj(null).setZwhyjnr("")
                        .setZzchysj(null).setZzchynr("")
                        .setLdxzhysj(null).setLdxzhynr("")
                        .setGs(0).setHccl(0);
                super.update(flowHrZdjldgbxb);
            }
        }
    }

    /**
     * 解析警察信息
     * @param jcxx
     * @return
     */
    private String formatJcxx(String jcxx){
        JSONArray jsonArray = JSON.parseArray(jcxx);
        StringBuffer sb = new StringBuffer("");
        if (jsonArray.size() > 0) {
            ReturnBean policeData = null;
            ReturnBean xzzwData = null;
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = (JSONObject)jsonArray.get(i);
                policeData = sysClient.findPolicebyJcby(jsonObject.get("jcbm").toString());
                if (null != policeData.data) {
                    BasePolice police = (BasePolice)policeData.data;
                    sb.append(police.getXm());
                }
                xzzwData = sysClient.getNameByCodeAndType(jsonObject.get("nrzw").toString(), "xzzw");
                if (null != xzzwData.data) {
                    sb.append("职务变更为");
                    Map map = (Map)xzzwData.data;
                    sb.append(map.get("name"));
                }
                if (i != (jsonArray.size()-1)) {
                    sb.append(";");
                }
            }
        }
        return sb.toString();
    }
}
