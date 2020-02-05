package com.bp.sys.mapper;

import com.bp.common.base.BpBaseMapper;
import com.bp.sys.po.Res;
import com.bp.sys.po.dto.ResDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @version 1.0
 * @Description: 资源管理映射层
 * @date 2016年7月16日
 */
@Mapper
public interface ResMapper extends BpBaseMapper<Res> {

    /**
     * 获取角色的资源
     *
     * @param roleCode 角色id
     * @return
     */
    List<Map> selectListByRoleCode(@Param("roleCode") String roleCode);

    /**
     * 新版多功能授权后登录获取账号资源
     *
     * @param para 角色id
     * @return
     */
    List<Map> selectListByUser(Map para);


    /**
     * 获取用户的资源
     *
     * @param userId 用户id
     * @return
     */
    List<Map> selectListByUserId(@Param("userId") Integer userId);


    /**
     * 获取资源树
     *
     * @return
     */
    List<Map> selectAllTree();


    /**
     * 根据父id查询子集资源
     *
     * @param pid 父id
     * @return
     */
    List<Map> selectListByPid(@Param("pid") Integer pid);

    /**
     * 查询临时授权根据temp_res表ID
     *
     * @param pid
     * @return
     */
    List<Map> selectListByTempResId(@Param("pid") Integer pid);

    /**
     * 获取用户的资源
     *
     * @param userId 用户id
     * @return
     */
    List<Map> selectRoleResByUserId(@Param("userId") Integer userId);

    List<Map> selectRoleUrlResByUserId(@Param("code") String roleCode, @Param("userId") Integer userId, @Param("pid") Integer pid);

    List<Map> selectRoleResByUser(Integer userId);

    List<Map> selectRoleUrlResByUserIdSjpt(@Param("code") String roleCode, @Param("userId") Integer userId, @Param("pid") Integer pid);

    List<Res> selectAll();

    /**
     * 根据pid查询子级资源树列表
     * @param pid pid
     * @return
     */
    List<ResDto> selectTableList(@Param("pid") String pid);
}