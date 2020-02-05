package com.bp.sixsys.mapper.eco;
import com.bp.common.base.BaseMapperUUID;
import com.bp.sixsys.po.eco.FlowEcoKfldtxsp;
import com.bp.sixsys.po.insp.FlowInspBmtbppsp;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author yanjisheng
 * @version 1.0
 * @Description: 康复劳动调休审批映射层
 * @date 2019年10月12日
 */
@Mapper
public interface GovKfldtxspMapper extends BaseMapperUUID<FlowEcoKfldtxsp> {

    FlowEcoKfldtxsp selectEntityById(String uuid);

    FlowEcoKfldtxsp findByPid(String pid);


}
