package com.bp.sixsys.mapper.buy;

import com.bp.common.base.BaseMapperUUID;

import com.bp.sixsys.po.buy.FlowBuyCgsq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


/**
 * @author pengwanli
 * @version 1.0
 * @Description: 采购申请映射层
 * @date 2019年11月25日
 */
@Mapper
public interface FlowBuyCgsqMapper extends BaseMapperUUID<FlowBuyCgsq>{

    /**
     * 带参数查询物资采购信息表导出
     *
     * @param param
     * @return
     */
    List<FlowBuyCgsq> selectListExport(Map param);

    /**
     * 汇总表
     */

    List<Map<String, Object>> totalList(Map para);
}
