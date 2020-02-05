package com.bp.sixsys.mapper.insp;

import com.bp.common.base.BaseMapperUUID;
import com.bp.sixsys.po.insp.FlowInspBmtbppsp;
import com.bp.sixsys.po.insp.FlowInspJcjytzs;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author guoxiangxu
 * @version 1.0
 * @Description: 通报批评审批（部门）映射层
 * @date 2019年12月03日
 */
@Mapper
public interface FlowInspBmtbppspMapper extends BaseMapperUUID<FlowInspBmtbppsp> {

    FlowInspBmtbppsp selectEntityById(String uuid);

    FlowInspBmtbppsp findByPid(String pid);
}
