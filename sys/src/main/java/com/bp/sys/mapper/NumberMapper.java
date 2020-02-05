package com.bp.sys.mapper;

import com.bp.common.base.BpBaseMapper;
import com.bp.model.sys.Number;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;


/**
 * @author
 * @version 1.0
 * @Description: 流水号映射层
 * @date 2017年05月17日
 */
@Mapper
public interface NumberMapper extends BpBaseMapper<Number> {



    /**
     * 获取流水号
     *
     * @param para
     * @return
     */
    String selectNum(Map para);


}
