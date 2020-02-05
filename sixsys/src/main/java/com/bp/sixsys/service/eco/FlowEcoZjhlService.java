package com.bp.sixsys.service.eco;
import com.bp.common.base.BaseMapperUUID;
import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.constants.FlowState;
import com.bp.common.constants.SysConstant;
import com.bp.common.dto.ActRollBackEntity;
import com.bp.common.utils.DateUtil;
import com.bp.common.utils.IdUtils;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.client.ActClient;
import com.bp.sixsys.client.FileStoreClient;
import com.bp.sixsys.client.SysClient;
import com.bp.sixsys.mapper.eco.GovZjhlMapper;
import com.bp.sixsys.po.eco.FlowEcoZjhl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yanjisheng
 * @version 1.0
 * @Description: 资金回笼服务层
 * @date 2019年10月15日
 */
@Service
public class FlowEcoZjhlService extends BaseServiceImplByAct<GovZjhlMapper, FlowEcoZjhl> {

    @Resource
    private GovZjhlMapper flowEcoZjhlMapper;

    @Autowired
    private FileStoreClient fileStoreClient;

    @Autowired
    private SysClient sysClient;

    @Autowired
    private ActClient actClient;


    @Override
    public BaseMapperUUID<FlowEcoZjhl> getMapper() {
        request.setAttribute("sysType","eco");
        request.setAttribute("tableName","flow_eco_zjhl");
        return flowEcoZjhlMapper;
    }


    @Override
    public String insert(FlowEcoZjhl govZjhl) {

        govZjhl.setFlowState(0);
        govZjhl.setFlowSn(sysClient.getNumberFlow("ACTIVITI_SN").msg);
        govZjhl.setDocState("新建");

        govZjhl.setUuid(IdUtils.getUuid());

        govZjhl.setInserter(null == govZjhl.getInserter() ? UserUtils.getCurrentUser() == null ? 0 : (UserUtils.getCurrentUser().getId()) : govZjhl.getInserter());
        govZjhl.setInsertTime(new Date());

        if (!"".equals(govZjhl.getFj()) && govZjhl.getFj() != null) {
            fileStoreClient.updateFileStatus(govZjhl.getFj());
        }
        return super.insert(govZjhl);

    }

    /**
     * 重写修改方法
     *
     * @param govZjhl
     */
    @Override
    public void update(FlowEcoZjhl govZjhl) {
        if (govZjhl.getFj() != null && !"".equals(govZjhl.getFj())) {
            fileStoreClient.updateFileStatus(govZjhl.getFj());
        }
        govZjhl.setUpdater(UserUtils.getCurrentUser() == null ? 0 : (UserUtils.getCurrentUser().getId()));
        govZjhl.setUpdateTime(new Date());

        super.update(govZjhl);

    }


    /**
     * 提交审核
     *
     * @param ids
     * @return
     */
    //@Transactional(rollbackFor = Exception.class)
    public ReturnBean submit(String ids) {
        Map<String, Object> paraMap = new HashMap<>(12);
        // 中队
        paraMap.put("zdId", UserUtils.getCurrentUser().getId() + ",1");
        paraMap.put("wlzxId", sysClient.findFlowRoleByCode("wlzx").data.getUserIds() + ",1");
        paraMap.put("shxykId", sysClient.findFlowRoleByCode("033").data.getUserIds() + ",1");
        paraMap.put("sldId", sysClient.findFlowRoleByCode("001").data.getUserIds() + ",1");
        paraMap.put("sckId", sysClient.findFlowRoleByCode("029").data.getUserIds() + ",1");
        paraMap.put("cwkId", sysClient.findFlowRoleByCode("034").data.getUserIds() + ",1");
        paraMap.put("bgsId", sysClient.findFlowRoleByCode("023").data.getUserIds() + ",1");
        paraMap.put("baseUrl", "sixsys/flowEcoZjhl");
        for (String id : ids.split(SysConstant.COMMA)) {
            FlowEcoZjhl govZjhl = getMapper().selectById(id);
            // 从业务表获取指定的人员
            Map userMap = sysClient.findUserByUserName(govZjhl.getScddqr()).data;
            paraMap.put("scddId", "1," + userMap.get("id").toString());
            // 监督问责参数
            paraMap.put("tableName","flow_eco_zjhl");
            paraMap.put("sysType","eco");
            this.startProcess(id, "flowEcoZjhl", paraMap);
            //将第一个节点的任务办理完
            String pid = getMapper().selectById(id).getPid();
            String taskId = actClient.findStartTaskId(UserUtils.getCurrentUser().getId(), pid).msg;
            Map<String, Object> para = new HashMap<>(12);
            para.put("taskId", taskId);
            para.put("pId", pid);
            para.put("pKey", "flowEcoZjhl");
            para.put("msg", "提交");
            para.put("comment", "默认提交");
            System.out.println(para);
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
        String status = actRollBackEntity.getMsg();
        // 实体Bean
        FlowEcoZjhl govZjhl = super.findByPid(actRollBackEntity.getPid());
        if (null != govZjhl) {
            Map param = actRollBackEntity.getMap();
            if (taskName.equals("发票开出时间")) {
                if (StringUtils.isNotBlank(param.get("fpkcsj").toString())) {
                    govZjhl.setFpkcsj(DateUtil.stringToDate("yyyy-MM-dd HH:mm:ss", param.get("fpkcsj").toString()));
                }
                govZjhl.setFpsm(param.getOrDefault("fpsm", "").toString());
                super.update(govZjhl);
            }
            if (taskName.equals("货款回笼时间")) {
                govZjhl.setHksfyhl(param.getOrDefault("hksfyhl", "是").toString());
                super.update(govZjhl);
            }
            if (taskName.equals("领导小组意见")) {
                govZjhl.setSftjdwh(param.getOrDefault("sftjdwh", "是").toString());
                super.update(govZjhl);
            }
            if (taskName.equals("货款回笼时间确认")) {
                if (StringUtils.isNotBlank(param.get("hkhlsj").toString())) {
                    govZjhl.setHkhlsj(DateUtil.stringToDate("yyyy-MM-dd HH:mm:ss", param.get("hkhlsj").toString()));
                }
                super.update(govZjhl);
            }
        }
    }


    @Override
    public void doFinishBusiness(ActRollBackEntity actRollBackEntity) {

        FlowEcoZjhl govZjhl = super.findByPid(actRollBackEntity.getPid());
        String msg = actRollBackEntity.getMsg();
        if ("货款回笼时间确认".equals(actRollBackEntity.getTaskName()) && "同意".equals(msg)) {
            govZjhl.setFlowState(FlowState.PASSED.getState()).setDocState(SysConstant.docState.finish.getDocState());
            super.update(govZjhl);
        }
    }


}
