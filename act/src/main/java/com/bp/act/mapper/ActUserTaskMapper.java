package com.bp.act.mapper;


import com.bp.act.entity.process.ActUserTask;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * @author 钟欣凯
 * @version 1.0
 * @Description: act_user_task映射层
 * @date 2018年11月19日
 */
@Mapper
public interface ActUserTaskMapper {
    /**
     * 根据流程定义的KEY查询列表
     *
     * @param para 参数
     * @return 查询结果
     */
    List<ActUserTask> selectAllEntityByKey(Map para);

    void updateUser(@Param("list") List<LinkedHashMap> maps);

    /**
     * 根据流程实例key和任务实例key查询
     *
     * @param pkey 流程定义的KEY
     * @param tkey 任务实例的KEY
     * @return 查询结果
     */
    ActUserTask findByPidAndTaskKey(@Param("pkey") String pkey, @Param("tkey") String tkey);

    void update(ActUserTask userTask);

    void insert(ActUserTask userTask);


}
