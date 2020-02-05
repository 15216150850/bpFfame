package com.bp.sixsys.service.hr;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bp.common.anno.FileAnnotation;
import com.bp.common.base.BaseMapperUUID;
import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.constants.FlowState;
import com.bp.common.constants.MsgConstant;
import com.bp.common.constants.SysConstant;
import com.bp.common.dto.ActRollBackEntity;
import com.bp.common.dto.MsgDto;
import com.bp.common.exception.BpException;
import com.bp.common.utils.DateUtil;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.client.ActClient;
import com.bp.sixsys.client.Msgclient;
import com.bp.sixsys.client.SysClient;
import com.bp.sixsys.mapper.hr.FlowHrLdgbjqsxbMapper;
import com.bp.sixsys.po.hr.BasePolice;
import com.bp.sixsys.po.hr.FlowHrLdgbjqsxb;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangyu
 * @version 1.0
 * @Description: 领导干部近亲属选拔任用工作服务层
 * @date 2019年11月21日
*/
@Service
public class FlowHrLdgbjqsxbService extends BaseServiceImplByAct<FlowHrLdgbjqsxbMapper, FlowHrLdgbjqsxb> {

	@Resource
	private FlowHrLdgbjqsxbMapper flowHrLdgbjqsxbMapper;

	@Override
	public BaseMapperUUID<FlowHrLdgbjqsxb> getMapper() {
        request.setAttribute("tableName","flow_hr_ldgbjqsxb");
        request.setAttribute("sysType","hr");
	    return flowHrLdgbjqsxbMapper;
	}

    @Autowired
    private SysClient sysClient;

    @Autowired
    private ActClient actClient;

    @Autowired
    private Msgclient msgclient;


    @Override
    @FileAnnotation(paramName = "xgwj")
    public String insert(FlowHrLdgbjqsxb entity) {
        entity.setFlowState(0);
        entity.setFlowSn(sysClient.getNumberFlow("ACTIVITI_SN").msg);
        entity.setDocState("新建");
        return super.insert(entity);
    }

    @Override
    @FileAnnotation(paramName = "xgwj")
    public void update(FlowHrLdgbjqsxb entity) {
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
//    @Transactional(rollbackFor = Exception.class)
//    @LcnTransaction
    public ReturnBean submit(String ids) {
        try{
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
            paraMap.put("baseUrl", "sixsys/flowHrLdgbjqsxb");
            // 启动流程
            for (String id : ids.split(SysConstant.COMMA)) {
                // 监督问责参数
                paraMap.put("tableName","flow_hr_ldgbjqsxb");
                paraMap.put("sysType","hr");
                this.startProcess(id, "flowHrLdgbjqsxb", paraMap);
                String pid = getMapper().selectById(id).getPid();
                String taskId = actClient.findStartTaskId(UserUtils.getCurrentUser().getId(), pid).msg;
                Map<String, Object> para = new HashMap<>(12);
                para.put("taskId", taskId);
                para.put("pId", pid);
                para.put("pKey", "flowHrLdgbjqsxb");
                para.put("msg", "提交");
                para.put("comment", "默认提交");
                actClient.handleStartTask(para);
            }
            return ReturnBean.ok("流程启动成功！");
        }catch (Exception e){
            e.printStackTrace();
            throw new BpException("领导干部近亲属选拔任用工作流程启动出错");
        }
    }

    @Override
    public void doFinishBusiness(ActRollBackEntity actRollBackEntity) {
        FlowHrLdgbjqsxb flowHrLdgbjqsxb = super.findByPid(actRollBackEntity.getPid());
        String msg = actRollBackEntity.getMsg();
        if ("上传任职文件".equals(actRollBackEntity.getTaskName()) && "已上传".equals(msg)) {
            flowHrLdgbjqsxb.setFlowState(FlowState.PASSED.getState()).setDocState(SysConstant.docState.finish.getDocState());
            super.update(flowHrLdgbjqsxb);
        }
    }

    @Override
    public void taskComplete(ActRollBackEntity actRollBackEntity) {
        // 流程节点名称
        String taskName = actRollBackEntity.getTaskName();
        String status = actRollBackEntity.getMsg();
        // 实体Bean
        FlowHrLdgbjqsxb flowHrLdgbjqsxb = super.findByPid(actRollBackEntity.getPid());
        if (null != flowHrLdgbjqsxb) {
            if("系统用户".equals(taskName)){
                // 事前
                super.beforehand(flowHrLdgbjqsxb);
            }
            if ("政治处选拔任用人员动议".equals(taskName) || "民主推荐情况".equals(taskName) || "综治办意见".equals(taskName)
                    || "职数审核".equals(taskName) || "公示".equals(taskName)){
                // 事中
                super.inFact(flowHrLdgbjqsxb);
            }
            if ("上传任职文件".equals(taskName)) {
                // 事后
                super.afterwards(flowHrLdgbjqsxb);
            }

            Map param = actRollBackEntity.getMap();
            // 存储 政治处会议时间 和 政治处会议内容
            if ("政治处选拔任用人员动议".equals(taskName)) {
                if (StringUtils.isNotBlank(param.get("zzchysj").toString())) {
                    flowHrLdgbjqsxb.setZzchysj(DateUtil.stringToDate("yyyy-MM-dd", param.get("zzchysj").toString()));
                }
                flowHrLdgbjqsxb.setZzchynr(param.getOrDefault("zzchynr","").toString());
                super.update(flowHrLdgbjqsxb);
            }
            // 存储 民主测评人数 和 民主测评优秀票数
            else if ("考察情况".equals(taskName)) {
                flowHrLdgbjqsxb.setMzcprs(Integer.parseInt(param.getOrDefault("mzcprs", 0).toString()))
                        .setMzyxps(Integer.parseInt(param.getOrDefault("mzyxps", 0).toString()));
                super.update(flowHrLdgbjqsxb);
            }
            // 存储 领导小组会议时间 和 会议内容
            else if ("上传领导小组会议结果".equals(taskName)) {
                if (StringUtils.isNotBlank(param.get("ldxzhysj").toString())) {
                    flowHrLdgbjqsxb.setLdxzhysj(DateUtil.stringToDate("yyyy-MM-dd", param.get("ldxzhysj").toString()));
                }
                flowHrLdgbjqsxb.setLdxzhynr(param.getOrDefault("ldxzhynr","").toString());
                super.update(flowHrLdgbjqsxb);
            }
            // 存储 应到人数、实到人数、人员名单、抄告单
            else if ("办公室主任填写党委会抄告单".equals(taskName)) {
                flowHrLdgbjqsxb.setYdrs(Integer.parseInt(param.getOrDefault("ydrs", "0").toString()))
                        .setSdrs(Integer.parseInt(param.getOrDefault("sdrs", "0").toString()))
                        .setRymd(param.getOrDefault("rymd", "").toString())
                        .setCgd(param.getOrDefault("cgd", "").toString());
                super.update(flowHrLdgbjqsxb);
            }
            // 职数审核节点，审核通过后生成一条公告(所有人可见)
            else if ("职数审核".equals(taskName) && "已审核".equals(status)) {
                String jcxxStr = formatQsxx(flowHrLdgbjqsxb.getJcbm(), flowHrLdgbjqsxb.getJcqsxx());
                String title = "关于“" + flowHrLdgbjqsxb.getActTitle() + "”流程审批通过,特此通报公示";
                Date msgStartDate = new Date();
                Date msgEndDate = DateUtil.dateAdd("day", 5, msgStartDate);
                String content = "经内部会议慎重讨论决定,“" + flowHrLdgbjqsxb.getActTitle() + "”流程提议被采纳。" +
                        "亲属选拔信息如下：" + jcxxStr + "。此公告将公示五个工作日" +
                        "(" + DateUtil.dateToString("yyyy-MM-dd", msgStartDate) + " 至 " + DateUtil.dateToString("yyyy-MM-dd", msgEndDate) + "),特此通告。";
                ReturnBean returnBean = msgclient.sendMsg(new MsgDto(title, "", "", MsgConstant.RECEIVE_TYPE_ALL, content, MsgConstant.MSG_SIGN_XXTZ, "1", "", ""));
                if (null != returnBean.data) {
                    Map data = (Map)returnBean.data;
                    String msgUuid = data.get("uuid").toString();
                    flowHrLdgbjqsxb.setMsgUuid(msgUuid).setMsgStartDate(msgStartDate).setMsgEndDate(msgEndDate);
                    super.update(flowHrLdgbjqsxb);
                }
            }
            // 存储 确定已公示5个工作日(0:否,1:是)
            else if ("公示".equals(taskName)) {
                flowHrLdgbjqsxb.setGs(Integer.parseInt(param.getOrDefault("gs", "1").toString()));
                super.update(flowHrLdgbjqsxb);
            }
            // 存储 确定材料正确且完整(0:否,1:是)
            else if ("上传任职文件".equals(taskName)) {
                flowHrLdgbjqsxb.setHccl(Integer.parseInt(param.getOrDefault("hccl", "1").toString()));
                super.update(flowHrLdgbjqsxb);
            }
            // 退回，清空流程业务字段
            if ("退回".equals(status)) {
                flowHrLdgbjqsxb.setZzchysj(null).setZzchynr("")
                        .setMzcprs(0).setMzyxps(0)
                        .setLdxzhysj(null).setLdxzhynr("")
                        .setYdrs(0).setSdrs(0).setRymd("").setCgd("")
                        .setGs(0).setHccl(0);
                super.update(flowHrLdgbjqsxb);
            }
        }
    }

    /**
     * 解析警察亲属信息
     * @param jcbm
     * @param qsxx
     * @return
     */
    private String formatQsxx(String jcbm, String qsxx){
        BasePolice basePolice = (BasePolice)sysClient.findPolicebyJcby(jcbm).data;
        String jcxm = basePolice.getXm();
        JSONArray jsonArray = JSON.parseArray(qsxx);
        StringBuffer sb = new StringBuffer("");
        if (jsonArray.size() > 0) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = (JSONObject)jsonArray.get(i);
                String info = "和" + jcxm + "为" + jsonObject.get("qsgx") + "关系的" + jsonObject.get("qsxm") + "被选拔到" + jsonObject.get("bmmc") + "部门，担任" + jsonObject.get("qszw") + "职务";
                sb.append(info);
                if (i != (jsonArray.size()-1)) {
                    sb.append(";");
                }
            }
        }
        return sb.toString();
    }
}
