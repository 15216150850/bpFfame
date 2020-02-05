package com.bp.sys.mapper;


import com.bp.common.base.BpBaseMapper;
import com.bp.sys.po.Region;
import com.bp.common.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Description：区域映射层
 * @author：
 * @date：2016-07-26
 */
@Mapper
public interface RegionMapper extends BpBaseMapper<Region> {

    /**
     * 检测区域编码是否重复
     *
     * @param id   区域id
     * @param code 区域编码
     * @return
     */
    Integer checkCode(@Param("id") Integer id, @Param("code") String code);

    /**
     * 根据id查询子集区域id
     *
     * @param id 区域id
     * @return
     */
    List<Map> selectChildList(@Param("id") Integer id);

    /**
     * 根据父id查询子集区域
     *
     * @param pid 父id
     * @return
     */
    List selectListByPid(@Param("pid") Integer pid);
}
