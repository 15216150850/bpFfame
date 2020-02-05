package com.bp.act.mapper;

import com.bp.act.entity.ActBpmnRecord;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


/**
 * @author 钟欣凯
 * @version 1.0
 * @Description: 流程部署表映射层
 * @date 2019年06月13日
 */
@Mapper
public interface ActBpmnRecordMapper {
    /**
     * 根据流程定义的KEY查询
     *
     * @param key 流程定义的KEY
     * @return 查询结果
     */
    ActBpmnRecord findByKey(String key);


    void insert(ActBpmnRecord actBpmnRecord);

    List<Map<String, Object>> selectList(Map para);

    void update(ActBpmnRecord actBpmnRecord);

    ActBpmnRecord selectEntityById(Integer id);

    void deleteByKey(String key);
}
