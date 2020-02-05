package com.bp.filecenter.mapper;


import com.bp.common.base.BpBaseMapper;
import com.bp.filecenter.entity.FileStore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


/**
 * @author xcj
 * @version 1.0
 * @Description: 文件仓库表映射层
 * @date 2018年10月22日
 */
@Mapper
@Component
public interface FileStoreMapper extends BpBaseMapper<FileStore> {

    /**
     * 根据条件查询文件仓库文件记录
     * @param para
     * @return
     */
    List<FileStore> selectFileList(Map para);

    /**
     * 根据uuid修改文件状态为有效
     * @param uuid
     */
    void updateFileStatus(@Param("uuid") String uuid);
}
