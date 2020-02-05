package com.bp.sixsys.mapper.hr;
import com.bp.common.base.BaseMapperUUID;
import com.bp.sixsys.po.hr.FlowHrGbtp;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author guoxiangxu
 * @version 1.0
 * @Description: 干部调配映射层
 * @date 2019年10月17日
 */
@Mapper
public interface FlowHrGbtpMapper extends BaseMapperUUID<FlowHrGbtp> {

     FlowHrGbtp selectById(String uuid);
     FlowHrGbtp findByPid(String pid);
}
