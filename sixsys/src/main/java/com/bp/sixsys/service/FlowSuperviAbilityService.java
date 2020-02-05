package com.bp.sixsys.service;

import com.bp.common.base.BaseMapperUUID;
import com.bp.common.base.BaseServiceImplUUID;
import com.bp.common.bean.ReturnBean;
import com.bp.common.constants.SysConstant;
import com.bp.common.utils.Common;
import com.bp.sixsys.client.ActClient;
import com.bp.sixsys.client.SysClient;
import com.bp.sixsys.mapper.FlowSuperviAbilityMapper;
import com.bp.sixsys.po.FlowSuperviAbility;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author zhongxinkai
 * @version 1.0
 * @Description: 监督问责服务层
 * @date 2019年11月21日
*/
@Service
public class FlowSuperviAbilityService extends BaseServiceImplUUID<FlowSuperviAbilityMapper,FlowSuperviAbility> {

	@Resource
	private FlowSuperviAbilityMapper flowSuperviAbilityMapper;

	@Autowired
	private ActClient actClient;

	@Autowired
	private SysClient sysClient;
	@Override
	public BaseMapperUUID<FlowSuperviAbility> getMapper() {
		return flowSuperviAbilityMapper;
	}



	/**
	 * 启动监督问责流程
	 * @param actTitle
	 * @return
	 */
	public String insertSuperviAbility(String actTitle) {

		FlowSuperviAbility flowSuperviAbility = new FlowSuperviAbility();
		flowSuperviAbility.setActTitle(actTitle+"监督问责");
		super.insert(flowSuperviAbility);
		return flowSuperviAbility.getUuid();
	}

	@Override
	public ReturnBean<List<FlowSuperviAbility>> selectList(Map para) {
		int page = Integer.parseInt(para.get(SysConstant.PAGE).toString());
		int limit = Integer.parseInt(para.get(SysConstant.LIMIT).toString());
		PageHelper.startPage(page, limit);
		List<Map<String, Object>> list = getMapper().selectList(para);
		for (Map<String, Object> map :
				list) {
			String pid = Common.getObjStr(map.get("pid"));
			Map insStartInfoByPidMap = actClient.getProInsStartInfoByPid(pid);
			int id =  1;
			try {
				id = Integer.parseInt(Common.getObjStr(insStartInfoByPidMap.get("startUserId")));
			} catch (Exception e) {
				e.printStackTrace();
			}
//			ReturnBean<Map> returnBean = sysClient.findUserById(id);
//			Map data = returnBean.data;
			map.put("startName",id);
            map.put("startTime",insStartInfoByPidMap==null?"":insStartInfoByPidMap.get("startTime"));
            if (!StringUtils.isEmpty(map.get("remark"))){
            	map.put("abilityState","4");
			}
		}
		PageInfo<Map> pageInfo = new PageInfo(list);
		return ReturnBean.list(list, pageInfo.getTotal());
	}
}
