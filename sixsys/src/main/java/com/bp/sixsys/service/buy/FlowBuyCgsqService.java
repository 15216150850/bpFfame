package com.bp.sixsys.service.buy;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.bp.common.anno.FileAnnotation;
import com.bp.common.base.BaseMapperUUID;
import com.bp.common.base.BaseServiceImplByAct;
import com.bp.common.bean.ReturnBean;
import com.bp.common.constants.FlowState;
import com.bp.common.constants.SysConstant;
import com.bp.common.dto.ActRollBackEntity;
import com.bp.common.utils.DateUtil;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.client.ActClient;
import com.bp.sixsys.client.SysClient;
import com.bp.sixsys.mapper.buy.FlowBuyCgsqMapper;
import com.bp.sixsys.po.buy.FlowBuyCgsq;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pengwanli
 * @version 1.0
 * @Description: 采购申请服务层
 * @date 2019年11月25日
 */
@Service
public class FlowBuyCgsqService extends BaseServiceImplByAct<FlowBuyCgsqMapper, FlowBuyCgsq> {

    @Resource
    private FlowBuyCgsqMapper flowBuyCgsqMapper;
    @Autowired
    private SysClient sysClient;
    @Autowired
    private ActClient actClient;
    @Resource
    private HttpServletResponse response;

    @Override
    public BaseMapperUUID<FlowBuyCgsq> getMapper() {
        request.setAttribute("sysType", "buy");
        request.setAttribute("tableName", "flow_buy_cgsq");
        return flowBuyCgsqMapper;
    }

    @Override
    @FileAnnotation(paramName = "xgwj")
    @Transactional(rollbackFor = Exception.class)
    @LcnTransaction
    public String insert(FlowBuyCgsq entity) {
        entity.setFlowState(0);
        entity.setFlowSn(sysClient.getNumberFlow("ACTIVITI_SN").msg);
        entity.setDocState("新建");
        return super.insert(entity);
    }

    @Override
    @FileAnnotation(paramName = "xgwj")
    @Transactional(rollbackFor = Exception.class)
    @LcnTransaction
    public void update(FlowBuyCgsq entity) {
        entity.setFlowState(0);
        entity.setFlowSn(sysClient.getNumberFlow("ACTIVITI_SN").msg);
        entity.setDocState("新建");
        super.update(entity);
    }

    /**
     * 提交单据
     *
     * @param uuids uuid
     * @return 提交结果
     */
    public ReturnBean submit(String uuids) {
        Map<String, Object> paraMap = new HashMap<>(12);
        // 系统用户
        paraMap.put("sysId", UserUtils.getCurrentUser().getId() + ",1");
        // 单位部门负责人意见
        paraMap.put("dwbmfzrId", sysClient.findFlowRoleByCode("007").data.getUserIds() + ",1");
        // 财务部门预算审核人意见
        paraMap.put("cwbmysshrId", sysClient.findFlowRoleByCode("031").data.getUserIds() + ",1");
        // 财务部门负责人意见
        paraMap.put("cwbmfzrId", sysClient.findFlowRoleByCode("028").data.getUserIds() + ",1");
        // 采购办公室意见
        paraMap.put("cgbgsId", sysClient.findFlowRoleByCode("033").data.getUserIds() + ",1");
        // 采购工作领导小组意见
        paraMap.put("cggzldxzId", sysClient.findFlowRoleByCode("034").data.getUserIds() + ",1");
        // 所党委会意见
        paraMap.put("jjdsglId", sysClient.findFlowRoleByCode("035").data.getUserIds() + ",1");
        // 分管财务所领导意见
        paraMap.put("fgcwsldId", sysClient.findFlowRoleByCode("029").data.getUserIds() + ",1");
        // 办公室主任填写抄告单
        paraMap.put("bgszrId", sysClient.findFlowRoleByCode("036").data.getUserIds() + ",1");
        // 所主要领导意见
        paraMap.put("szyldId", sysClient.findFlowRoleByCode("017").data.getUserIds() + ",1");
        // 省戒毒局意见
        paraMap.put("sjdjId", UserUtils.getCurrentUser().getId() + ",1");
        // 考察小组提交审批
        paraMap.put("kcxzId", sysClient.findFlowRoleByCode("038").data.getUserIds() + ",1");
        // 考察人员确认签字
        paraMap.put("kcryId", UserUtils.getCurrentUser().getId() + ",1");
        // 分管所领导意见
        paraMap.put("fgsldId", sysClient.findFlowRoleByCode("017").data.getUserIds() + ",1");
        // 采购领导小组意见
        paraMap.put("cgldxzId", sysClient.findFlowRoleByCode("039").data.getUserIds() + ",1");
        // 采购验收
        paraMap.put("cgysId", UserUtils.getCurrentUser().getId() + ",1");
        // 发起人上传材料
        paraMap.put("fqrId", UserUtils.getCurrentUser().getId() + ",1");
        // 采购资料归档
        paraMap.put("cgzlgdId", sysClient.findFlowRoleByCode("043").data.getUserIds() + ",1");
        // 所党委会意见
        paraMap.put("sdwhId", sysClient.findFlowRoleByCode("035").data.getUserIds() + ",1");

        paraMap.put("baseUrl", "sixsys/flowBuyCgsq");
        for (String id :
                uuids.split(",")) {
            FlowBuyCgsq flowBuyCgsq = getMapper().selectById(id);
            paraMap.put("tableName", "flow_buy_cgsq");
            paraMap.put("sysType", "buy");
            // 设置网关条件
            paraMap = setGatewayParam(flowBuyCgsq, paraMap);
            paraMap.put("sqrbm", flowBuyCgsq.getSqrbmbm());
            paraMap.put("cggkbm", flowBuyCgsq.getCggkbmbm());
            // 采购归口部门负责人意见
            paraMap.put("cggkbmfzrId",sysClient.findUserByUserName(flowBuyCgsq.getCggkbmfzrbm()).data.get("id") + ",1");
            paraMap.put("je", flowBuyCgsq.getYjje());
            this.startProcess(id, "flowBuyCgsq", paraMap);

            // 将第一个节点的任务办理完
            String pid = getMapper().selectById(id).getPid();
            String taskId = actClient.findStartTaskId(UserUtils.getCurrentUser().getId(), pid).msg;
            Map<String, Object> para = new HashMap<>(12);
            para.put("taskId", taskId);
            para.put("pId", pid);
            para.put("pKey", "flowBuyCgsq");
            para.put("msg", "提交");
            para.put("comment", "默认提交");
            actClient.handleStartTask(para);
        }

        return ReturnBean.ok("流程启动成功");
    }

    /**
     * 流程结束回调
     *
     * @param actRollBackEntity
     */
    @Override
    public void doFinishBusiness(ActRollBackEntity actRollBackEntity) {
        FlowBuyCgsq flowBuyCgsq = super.findByPid(actRollBackEntity.getPid());
        if ("采购资料归档".equals(actRollBackEntity.getTaskName()) && "材料完整".equals(actRollBackEntity.getMsg())) {
            flowBuyCgsq.setFlowState(FlowState.PASSED.getState()).setDocState(SysConstant.docState.finish.getDocState());
            super.update(flowBuyCgsq);
        }
    }

    @Override
    public void taskComplete(ActRollBackEntity actRollBackEntity) {
        // 流程节点名称
        String taskName = actRollBackEntity.getTaskName();
        Map map = actRollBackEntity.getMap();
        FlowBuyCgsq flowBuyCgsq = super.findByPid(actRollBackEntity.getPid());
        BigDecimal je = flowBuyCgsq.getYjje();
        if (null != flowBuyCgsq) {
            Map param = actRollBackEntity.getMap();
            if ("系统用户".equals(taskName)) {
                // 事前
                super.beforehand(flowBuyCgsq);
            } else if ("采购资料归档".equals(taskName)) {
                // 事后
                super.afterwards(flowBuyCgsq);
            } else {
                // 事中
                super.inFact(flowBuyCgsq);
            }

            // 存储 单位部门负责人意见 选定分管领导
            if ("单位部门负责人意见".equals(taskName)) {
                flowBuyCgsq.setXdfgld(param.getOrDefault("xdfgld","").toString());
                super.update(flowBuyCgsq);
            }
            // 存储 该项目资金来源 和 预算情况
            if ("财务部门预算审核人意见".equals(taskName)) {
                flowBuyCgsq.setGxmzjly(param.getOrDefault("gxmzjly","").toString());
                super.update(flowBuyCgsq);
            }

            // 存储 该项目资金来源 和 预算情况
            if ("财务部门负责人意见".equals(taskName)) {
                flowBuyCgsq.setSfzzfcgmln(param.getOrDefault("sfzzfcgmln","").toString());
                super.update(flowBuyCgsq);
            }

            // 存储 是否政府采购、采购组织形式、采购实施机构、 和 采购方式、会议采购单
            if ("usertask8".equals(map.get("taskKey"))) {
                flowBuyCgsq.setSfzfcg(param.getOrDefault("sfzfcg", "").toString())
                        .setCgzzxs(param.getOrDefault("cgzzxs","").toString())
                        .setCgssjg(param.getOrDefault("cgssjg","").toString())
                        .setCgfs(param.getOrDefault("cgfs","").toString());
                if(flowBuyCgsq.getYjje().compareTo(new BigDecimal(0))>0 && flowBuyCgsq.getYjje().compareTo(new BigDecimal(2000))<0){
                    flowBuyCgsq.setCgbgsyj(param.getOrDefault("cgbgsyj","").toString());
                }else {
                    flowBuyCgsq.setCgldxzhycgd(param.getOrDefault("cgldxzhycgd","").toString());
                }

                super.update(flowBuyCgsq);
            }
            // 存储 办公室主任填写抄告单
            if ("办公室主任填写抄告单".equals(taskName)) {
                flowBuyCgsq.setBgszrtxcgd(param.getOrDefault("bgszrtxcgd","").toString());
                super.update(flowBuyCgsq);
            }
            // 存储 考察小组提交审批 考察时间、考察地点、考察情况、考察小组意见、考察小组成员
            if ("考察小组提交审批".equals(taskName)) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = simpleDateFormat.parse(param.get("kcsj").toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                flowBuyCgsq.setKcxzcy(param.getOrDefault("kcxzcy","").toString())
                        .setKcsj(date)
                        .setKcdd(param.getOrDefault("kcdd","").toString())
                        .setKcqk(param.getOrDefault("kcqk","").toString())
                        .setKcxzyj(param.getOrDefault("kcxzyj","").toString());
                super.update(flowBuyCgsq);
            }
            // 存储 采购办公室意见 评定抄告单
            //条件（2）金额是否大于2万
            boolean check2 = flowBuyCgsq.getYjje().compareTo(new BigDecimal(20000))>=0;
            //条件（3）金额是否等于0
            boolean check3 = flowBuyCgsq.getYjje().compareTo(new BigDecimal(0))== 0;
            //条件（1）节点是否为采购办公室意见
            boolean check1 = "采购办公室意见".equals(taskName);
            //最终结果
            boolean result = check1 && ( check2|| check3);
            if (result) {
                flowBuyCgsq.setCgldxzhypdcgd(param.getOrDefault("cgldxzhypdcgd","").toString());
                super.update(flowBuyCgsq);
            }

            // 存储 采购验收 验收日期、验收单位部门、供应商名称、验收人员、验收物资明细及金额
            if ("采购验收".equals(taskName)) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = simpleDateFormat.parse(param.get("ysrq").toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                flowBuyCgsq.setYsdwbm(param.getOrDefault("ysdwbm","").toString())
                        .setGysmc(param.getOrDefault("gysmc","").toString())
                        .setYsry(param.getOrDefault("ysry","").toString())
                        .setYswzmxjje(param.getOrDefault("yswzmxjje","").toString())
                        .setYsrq(date)
                        .setCjje(new BigDecimal(param.getOrDefault("cjje","").toString()));
                System.out.println(param.getOrDefault("cjje",""));
                super.update(flowBuyCgsq);
            }
        }
    }

    /**
     * 导出师资人员信息
     */
    public void export(Map para) {
        String fileName;
        String cgssjg = para.get("sfzzfcgmln").toString();
        if ("1".equals(cgssjg)){
            // 导出
            fileName = "政府采购目录内招标方式-" + DateUtil.getDate("yyyy-MM-dd") + ".xls";
        }else {
            // 导出
            fileName = "政府采购目录外招标方式-" + DateUtil.getDate("yyyy-MM-dd") + ".xls";
        }
        List<FlowBuyCgsq> flowBuyCgsqList = flowBuyCgsqMapper.selectListExport(para);
        try {
            // 生成workbook
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("汇总表", ""), FlowBuyCgsq.class, flowBuyCgsqList);
            // 输出流
            response.setHeader("Content-Disposition", "attachment; filename=noname.xls");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Type", "application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
            OutputStream fos = response.getOutputStream();
            workbook.write(fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 手动设置网关条件
     * @param flowBuyCgsq
     * @param param
     * @return
     */
    private Map<String, Object>  setGatewayParam(FlowBuyCgsq flowBuyCgsq,Map<String, Object> param){
        BigDecimal yjje = flowBuyCgsq.getYjje();
        String sqrbmbm = flowBuyCgsq.getSqrbmbm();
        String cggkbmbm = flowBuyCgsq.getCggkbmbm();
        String cgssjg = flowBuyCgsq.getCgssjg();
        String cgfs = flowBuyCgsq.getCgfs();
        // 申请人部门等于采购归口部门
        if(sqrbmbm.equals(cggkbmbm)){
            param.put("exclusivegateway1","${sqrbm ==cggkbm}");
        }else {
            param.put("exclusivegateway1","${sqrbm != cggkbm}");
        }
        // 预计金额>0且<5000
        if (yjje.compareTo(new BigDecimal(5000)) < 0 && yjje.compareTo(new BigDecimal(0)) > 0){
            param.put("exclusivegateway3", "${je> 0 && je <5000}");
        }
        // 预计金额>=5000且<20000
        if(yjje.compareTo(new BigDecimal(5000)) >= 0 && yjje.compareTo(new BigDecimal(20000)) < 0){
            param.put("exclusivegateway3", "${je >=5000 && je <20000}");

        }
        // 预计金额>=20000或者=0
        if(yjje.compareTo(new BigDecimal(20000)) >= 0 || yjje.compareTo(new BigDecimal(0)) == 0){
            param.put("exclusivegateway3", "${ je >= 20000 ||  je == 0}");
        }
        // 预计金额>=20000且<200000
        if(yjje.compareTo(new BigDecimal(20000)) >= 0 && yjje.compareTo(new BigDecimal(200000)) < 0){
            param.put("exclusivegateway4", "${je <200000 ||  je == 0}");
        }
        // 预计金额>=200000
        if(yjje.compareTo(new BigDecimal(200000)) >= 0){
            param.put("exclusivegateway4", "${ je >= 200000}");
        }
        return param;
    }

    /**
     * 汇总表
     */

    public ReturnBean<List<FlowBuyCgsq>> totalList(Map para){
        if (para != null && para.get(SysConstant.PAGE) != null) {
            int page = Integer.parseInt(para.get(SysConstant.PAGE).toString());
            int limit = Integer.parseInt(para.get(SysConstant.LIMIT).toString());
            PageHelper.startPage(page, limit);
            List<Map<String, Object>> list = flowBuyCgsqMapper.totalList(para);
            PageInfo<Map> pageInfo = new PageInfo(list);
            return ReturnBean.list(list, pageInfo.getTotal());
        } else {
            List<Map<String, Object>> list = flowBuyCgsqMapper.totalList(para);
            return ReturnBean.list(list, (long) list.size());
        }
    }
}
