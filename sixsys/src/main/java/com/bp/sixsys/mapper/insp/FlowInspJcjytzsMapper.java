package com.bp.sixsys.mapper.insp;

import com.bp.common.base.BaseMapperUUID;
import com.bp.sixsys.po.insp.FlowInspJcjytzs;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author guoxiangxu
 * @version 1.0
 * @Description: 监查建议通知书映射层
 * @date 2019年12月02日
 */
@Mapper
public interface FlowInspJcjytzsMapper extends BaseMapperUUID<FlowInspJcjytzs> {

   FlowInspJcjytzs selectEntityById(String uuid);

   FlowInspJcjytzs findByPid(String pid);

}