package com.bp.act.mapper;


import com.bp.act.entity.ActKeyUrl;
import com.bp.act.entity.process.ActUserTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 启动流程实例持久层
 *
 * @author 钟欣凯
 * @date: 2019/1/21 10:38
 */

@Mapper
public interface ProcessMapper {

    /**
     * @param actKeyUrl
     */
    void insertActKeyUrl(ActKeyUrl actKeyUrl);


    ActKeyUrl selectKeyUrlByKey(String key);


    void insertIsDefined(Map<String, Object> map);

    /**
     * 查询自定义流程
     *
     * @param para 查询参数
     * @return 查询结果
     */
    List<Map> selectListDefinedProdefList(Map para);

    /**
     * 根据key修改是否自定义审批人员表
     *
     * @param paraMap 参数
     */
    void updateIsDefinedByKey(Map<String, Object> paraMap);

    void deleteIsDefinedByKey(String key);

    Long selectListProefCount(Map para);

    Long selectListDefinedProdefListCount(Map para);

    List<ActUserTask> findProCessByKey(String key);


    void updateActKeyUrl(ActKeyUrl actKeyUrl);
}
