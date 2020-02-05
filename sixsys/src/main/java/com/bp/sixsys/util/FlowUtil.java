package com.bp.sixsys.util;

import com.bp.common.entity.LoginUser;
import com.bp.common.utils.UserUtils;
import com.bp.sixsys.SixsysApplication;
import com.bp.sixsys.client.SysClient;
import com.bp.sixsys.po.BaseDepartment;
import com.bp.sixsys.po.FlowRole;

import java.util.List;
import java.util.Map;

/**
 * 工作流工具类
 *
 * @author yangjisheng
 */
public class FlowUtil {

    private static SysClient sysClient = SixsysApplication.getApplicationContext().getBean(SysClient.class);

    /**
     * 获取当前登录用户所在单位的负责人id
     */
    public static String getChargeablePerson() {
        LoginUser user = UserUtils.getCurrentUser();
        String dwfzrId = "0";
        if (user != null) {
            String bmbm = user.getOrganizationCode();
            BaseDepartment bm = sysClient.findByBmbm(bmbm).data;
            if (bm != null) {
                String dwfzrName = bm.getFzr();
                dwfzrId = getUserIdByName(dwfzrName);
            }
        }
        return dwfzrId;
    }

    /**
     * 根据姓名获取用户id<br>
     * 注意：禁止service层直接调用本方法，替代方法是，建立相应的流程角色，
     * 然后调用getUserIdByFlowRole方法
     */
    public static String getUserIdByName(String name) {
        String userId = "0";
        List<Map<String, Object>> user = (List<Map<String, Object>>) sysClient.findUserByName(name).data;
        if (user != null && user.size() == 1) {
            userId = ((Integer) user.get(0).get("id")).toString();
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < user.size(); i++) {
                sb.append(((Integer) user.get(i).get("id")).toString());
                if (i < user.size() - 1) {
                    sb.append(",");
                }
            }
            userId = sb.toString();
        }
        return userId;
    }

    /**
     * 根据流程角色code获取用户id
     */
    public static String getUserIdByFlowRole(String flowRoleCode) {
        String userId = "0";
        FlowRole flowRole = sysClient.findFlowRoleByCode(flowRoleCode).data;
        if (flowRole != null) {
            userId = flowRole.getUserIds();
        }
        return userId;
    }

}
