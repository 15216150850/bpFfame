package com.bp.sixsys.service.hr;

import javax.annotation.Resource;

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
import com.bp.sixsys.mapper.hr.FlowHrKjfldjsMapper;
import com.bp.sixsys.po.hr.FlowHrKjfldjs;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bp.common.base.BaseMapperUUID;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangyu
 * @version 1.0
 * @Description: 科级非领导职务晋升工作服务层
 * @date 2019年11月22日
*/
@Service
public class FlowHrKjfldjsService extends BaseServiceImplByAct<FlowHrKjfldjsMapper, FlowHrKjfldjs> {

	@Resource
	private FlowHrKjfldjsMapper flowHrKjfldjsMapper;

	@Override
	public BaseMapperUUID<FlowHrKjfldjs> getMapper() {
        request.setAttribute("tableName","flow_hr_kjfldjs");
        request.setAttribute("sysType","hr");
		return flowHrKjfldjsMapper;
	}

    @Autowired
    private SysClient sysClient;

    @Autowired
    private ActClient actClient;

    @Override
    @FileAnnotation(paramName = "xgwj")
    public String insert(FlowHrKjfldjs entity) {
        entity.setFlowState(0);
        entity.setFlowSn(sysClient.getNumberFlow("ACTIVITI_SN").msg);
        entity.setDocState("新建");
        return super.insert(entity);
    }

    @Override
    @FileAnnotation(paramName = "xgwj")
    public void update(FlowHrKjfldjs entity) {
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
        paraMap.put("baseUrl", "sixsys/flowHrKjfldjs");
        // 启动流程
        for (String id : ids.split(SysConstant.COMMA)) {
            // 监督问责参数
            paraMap.put("tableName","flow_hr_kjfldjs");
            paraMap.put("sysType","hr");
            this.startProcess(id, "flowHrKjfldjs", paraMap);
            String pid = getMapper().selectById(id).getPid();
            String taskId = actClient.findStartTaskId(UserUtils.getCurrentUser().getId(), pid).msg;
            Map<String, Object> para = new HashMap<>(12);
            para.put("taskId", taskId);
            para.put("pId", pid);
            para.put("pKey", "flowHrKjfldjs");
            para.put("msg", "提交");
            para.put("comment", "默认提交");
            actClient.handleStartTask(para);
        }
        return ReturnBean.ok("流程启动成功！");
    }


    @Override
    public void doFinishBusiness(ActRollBackEntity actRollBackEntity) {
        FlowHrKjfldjs flowHrKjfldjs = super.findByPid(actRollBackEntity.getPid());
        String msg = actRollBackEntity.getMsg();
        if ("上传任职文件".equals(actRollBackEntity.getTaskName()) && "已上传".equals(msg)) {
            flowHrKjfldjs.setFlowState(FlowState.PASSED.getState()).setDocState(SysConstant.docState.finish.getDocState());
            super.update(flowHrKjfldjs);
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
        // 实体Bean
        FlowHrKjfldjs flowHrKjfldjs = super.findByPid(actRollBackEntity.getPid());
        if (null != flowHrKjfldjs) {

            if("系统用户".equals(taskName)){
                // 事前
                super.beforehand(flowHrKjfldjs);
            }
            if ("政治处选拔任用人员动议".equals(taskName) || "民主推荐情况".equals(taskName)
                    || "综治办意见".equals(taskName) || "监察室意见".equals(taskName)
                    || "公示".equals(taskName)){
                // 事中
                super.inFact(flowHrKjfldjs);
            }

            Map param = actRollBackEntity.getMap();
            // 存储 领导小组会议时间 和 会议内容
            if ("上传领导小组会议结果".equals(taskName)) {
                if (StringUtils.isNotBlank(param.get("ldxzhysj").toString())) {
                    flowHrKjfldjs.setLdxzhysj(DateUtil.stringToDate("yyyy-MM-dd", param.get("ldxzhysj").toString()));
                }
                flowHrKjfldjs.setLdxzhynr(param.getOrDefault("ldxzhynr","").toString());
                super.update(flowHrKjfldjs);
            }
            // 存储 应到人数、实到人数、人员名单、抄告单
            else if ("办公室主任填写党委会议抄告单".equals(taskName)) {
                if (StringUtils.isNotBlank(param.get("ydrs").toString())) {
                    flowHrKjfldjs.setYdrs(Integer.parseInt(param.getOrDefault("ydrs", "0").toString()));
                }
                if (StringUtils.isNotBlank(param.get("sdrs").toString())) {
                    flowHrKjfldjs.setSdrs(Integer.parseInt(param.getOrDefault("sdrs", "0").toString()));
                }
                flowHrKjfldjs.setRymd(param.getOrDefault("rymd", "").toString());
                flowHrKjfldjs.setCgd(param.getOrDefault("cgd", "").toString());
                super.update(flowHrKjfldjs);
            }
            // 存储 确定已公示5个工作日(0:否,1:是)
            else if ("公示".equals(taskName)) {
                flowHrKjfldjs.setGs(Integer.parseInt(param.getOrDefault("gs", "1").toString()));
                super.update(flowHrKjfldjs);
            }
            // 存储 确定材料正确且完整(0:否,1:是)
            else if ("上传任职文件".equals(taskName)) {
                flowHrKjfldjs.setHccl(Integer.parseInt(param.getOrDefault("hccl", "1").toString()));
                super.update(flowHrKjfldjs);
                // 事后
                super.afterwards(flowHrKjfldjs);
            }
            // 退回，清空流程业务字段
            if ("退回".equals(status)) {
                flowHrKjfldjs.setZzchysj(null).setZzchynr("")
                        .setLdxzhysj(null).setLdxzhynr("")
                        .setYdrs(0).setSdrs(0).setRymd("").setCgd("")
                        .setGs(0).setHccl(0);
                super.update(flowHrKjfldjs);
            }
        }
    }
}
