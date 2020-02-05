package com.bp.sys.mapper;

import com.bp.common.base.BaseMapperUUID;
import com.bp.sys.po.Wxxsjfx;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


/**
 * @author zhangyu
 * @version 1.0
 * @Description: 危险性数据分析映射层
 * @date 2019年10月25日
 */
@Mapper
public interface WxxsjfxMapper extends BaseMapperUUID<Wxxsjfx> {

    Wxxsjfx selectByJdrybm(String jdrybm);

    List<Map<String, Object>> selectPersonList(Map param);

    Long selectPersonListCount(Map param);

    Float selectAvg();

}
