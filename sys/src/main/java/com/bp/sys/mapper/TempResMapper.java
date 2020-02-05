package com.bp.sys.mapper;

import com.bp.common.base.BaseMapper;
import com.bp.common.base.BpBaseMapper;
import com.bp.sys.po.TempRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author
 * @version 1.0
 * @Description: 临时授权映射层
 * @date 2018年10月30日
 */
@Mapper
public interface TempResMapper extends BpBaseMapper<TempRes> {
    /**
     * 保存临时权限子表
     *
     * @param list
     */
    void insertTempResInfo(List list);

    /**
     * 根据PID删除角色临时权限资源
     *
     * @param pid
     */
    void deleteTempResInfo(@Param("pid") Integer pid);
}
