package com.bp.sixsys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bp.common.base.BaseMapperUUID;
import com.bp.common.base.BaseServiceImplUUID;
import com.bp.common.bean.ReturnBean;
import com.bp.common.constants.SysConstant;
import com.bp.common.entity.FlowSixSys;
import com.bp.common.utils.Common;
import com.bp.sixsys.mapper.FlowSixSysMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhongxinkai
 * @version 1.0
 * @Description: 六大体系流程汇总表服务层
 * @date 2019年11月20日
 */
@Service
public class FlowSixSysService extends BaseServiceImplUUID<FlowSixSysMapper, FlowSixSys> {

    @Resource
    private FlowSixSysMapper flowSixSysMapper;

    @Override
    public BaseMapperUUID<FlowSixSys> getMapper() {
        return flowSixSysMapper;
    }

    public void updateByPid(FlowSixSys flowSixSys) {
        QueryWrapper<FlowSixSys> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("busi_pid", flowSixSys.getBusiPid());
        getMapper().update(flowSixSys, queryWrapper);
    }

    public ReturnBean<FlowSixSys> findBysupseUuid(String uuid) {
        QueryWrapper<FlowSixSys> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("supse_uuid", uuid);
        FlowSixSys flowSixSys = getMapper().selectOne(queryWrapper);
        return ReturnBean.ok(flowSixSys);
    }

    /**
     * 我的申请
     *
     * @param map
     * @return
     */
    public ReturnBean myApplications(Map map) {
        int userId = Integer.parseInt(map.get("userId").toString());
        String flowState = Common.getObjStr(map.get("flowState"));
        List<FlowSixSys> flowSixSysLists = flowSixSysMapper.selectAll(map);
        List<Map<String, Object>> myApplicationsList = new ArrayList<>();
        for (FlowSixSys flowSixSys :
                flowSixSysLists) {
            if (StringUtils.isEmpty(flowSixSys.getTableName())) {
                continue;
            }
            Map<String, Object> stringObjectMap = flowSixSysMapper.findBymyApplications(userId, StringUtils.isEmpty(flowState) ? null : flowState.split(","),
                    flowSixSys.getTableName(), flowSixSys.getBusiUuid());
            if (stringObjectMap != null) {
                myApplicationsList.add(stringObjectMap);
            }
        }
        int page = Integer.parseInt(map.get(SysConstant.PAGE).toString());
        int limit = Integer.parseInt(map.get(SysConstant.LIMIT).toString());
        int start = (page-1)*limit;
        List<Map<String,Object>>resulyList = new ArrayList<>();
        for (int i = start;i<start+limit;i++){
        	if ( i==myApplicationsList.size() ){
        		break;
			}
        	resulyList.add(myApplicationsList.get(i));
		}
        return ReturnBean.list(resulyList, (long) myApplicationsList.size());
//		int page = Integer.parseInt(map.get(SysConstant.PAGE).toString());
//		int limit = Integer.parseInt(map.get(SysConstant.LIMIT).toString());
//		int userId = Integer.parseInt(map.get("userId").toString());
//		String flowState = Common.getObjStr(map.get("flowState"));
//		Map<String,Object> stringObjectMap =  flowSixSysMapper.findBymyApplications(userId,StringUtils.isEmpty(flowState)?null:flowState.split(","),
//					sixSys.getTableName(),sixSys.getBusiUuid());
//		int start = (page-1)*limit;
//		int end = start+limit;
//		List<Map<String,Object>>myApplicationsList = new ArrayList<>();
//		for (int i = start;i < end;i++){
//
//			FlowSixSys sixSys = null;
//			try {
//				sixSys = flowSixSysLists.get(i);
//			} catch (Exception e) {
//				break;
//			}
//			if (StringUtils.isEmpty(sixSys.getTableName())){
//				end = end+1;
//				continue;
//			}
//
//			Map<String,Object> stringObjectMap =  flowSixSysMapper.findBymyApplications(userId,StringUtils.isEmpty(flowState)?null:flowState.split(","),
//					sixSys.getTableName(),sixSys.getBusiUuid());
//		     if (stringObjectMap!=null){
//		     	if(StringUtils.isEmpty(stringObjectMap.get("actTitle"))){
//		     	Map<String,Object> busiMap = 	flowSixSysMapper.selectBusi(sixSys.getTableName(),sixSys.getBusiUuid());
//				stringObjectMap.put("actTitle",Common.getObjStr(busiMap.get("actTitle")));
//		     	}
//				 myApplicationsList.add(stringObjectMap);
//			 }
//
//		}
//		int page = Integer.parseInt(map.get(SysConstant.PAGE).toString());
//		int limit = Integer.parseInt(map.get(SysConstant.LIMIT).toString());
//		Page<FlowSixSys> sixSysPage = new Page<>();
//		QueryWrapper<FlowSixSys> queryWrapper = new QueryWrapper<>();
//		if (!StringUtils.isEmpty(Common.getObjStr(map.get("flowState")))){
//			queryWrapper.in("flow_state", Arrays.asList(Common.getObjStr(map.get("flowState")).split(",")));
//		}
//		 getMapper().selectMapsPage(sixSysPage, queryWrapper);
        //		PageHelper.startPage(page, limit);
//		List<Map<String, Object>> list = getMapper().selectList(map);
//		PageInfo<Map> pageInfo = new PageInfo(list);
//		return ReturnBean.list(list, pageInfo.getTotal());
//
//		return ReturnBean.list(myApplicationsList, (long) flowSixSysLists.size());

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String uuid) {
        super.delete(uuid);
        FlowSixSys flowSixSys = super.selectEntityById(uuid);
        if (!StringUtils.isEmpty(flowSixSys)) {
            flowSixSysMapper.deleteBusi(flowSixSys.getTableName(), flowSixSys.getBusiUuid());
        }

    }

    public void deleteFlowSixSysByBusiUuid(String uuid) {
        QueryWrapper<FlowSixSys> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("busi_uuid", uuid);
        getMapper().delete(queryWrapper);
    }

    public void updateFlowSixSysByBusiUuid(FlowSixSys flowSixSys) {
        QueryWrapper<FlowSixSys> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("busi_uuid", flowSixSys.getBusiUuid());
        getMapper().update(flowSixSys, queryWrapper);
    }
}
