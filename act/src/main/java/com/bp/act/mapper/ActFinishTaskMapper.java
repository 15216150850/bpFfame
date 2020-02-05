package com.bp.act.mapper;

import com.bp.act.entity.ActFinishTask;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * @author 钟欣凯
 * @version 1.0
 * @Description: 个人已办理的任务映射层
 * @date 2019年09月28日
 */
@Mapper
public interface ActFinishTaskMapper {
    /**
     * 根据流程实例ID查询
     *
     * @param pid 流程实例ID
     * @return 查询结果
     */
    List<ActFinishTask> getRecordByPid(String pid);

    /**
     * 查询所有的个人已办理任务
     *
     * @param para 参数集合
     * @return 查询结果
     */
    List<ActFinishTask> selectListAll(Map para);

    List<Map<String, Object>> selectList(Map para);

    void insert(ActFinishTask actFinishTask);

    void update(ActFinishTask actFinishTask);

    ActFinishTask selectEntityById(Integer id);

    void delete(Integer valueOf);

    void deleteByIds(String[] idArr);

    void updateCurrentStateByPid(@Param("pId") String pId, @Param("name") String name);

    /**
     * 获取流程的审批节点数据,取得每个节点最新的意见信息:用于回显流程各个部门的审批意见
     *
     * @param pid 流程pid
     * @return 返回该流程所有节点最新的审批意见
     */
    List<ActFinishTask> selectFinallyTaskByPid(@Param("pid") String pid);

    void updateByPid(ActFinishTask actFinishTask);

}
