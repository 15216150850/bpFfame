package com.bp.sixsys.mapper;

import com.bp.common.base.BaseMapperUUID;
import com.bp.common.entity.FlowSixSys;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * @author zhongxinkai
 * @version 1.0
 * @Description: 六大体系流程汇总表映射层
 * @date 2019年11月20日
 */
@Mapper
public interface FlowSixSysMapper extends BaseMapperUUID<FlowSixSys>{

    Map<String, Object> findBymyApplications(@Param("userId") int userId,@Param("flowState") String[] flowState
            ,@Param("tableName") String tableName,@Param("uuid")String uuid);

    List<FlowSixSys> selectAll(Map map);


    void deleteBusi(@Param("tableName") String tableName,@Param("uuid") String uuid);

    Map<String, Object> selectBusi(@Param("tableName") String tableName, @Param("uuid") String uuid);

}
