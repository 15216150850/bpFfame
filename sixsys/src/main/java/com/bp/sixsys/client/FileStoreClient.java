package com.bp.sixsys.client;

import com.bp.common.bean.ReturnBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 文集上传调用
 *
 * @author: 刘毓瑞
 * @date: 2019/6/20 08:35
 */
@FeignClient(name = "file")
public interface FileStoreClient {

    /**
     * 带文件上传必调方法
     *
     * @param uuid
     * @return
     */
    @GetMapping("api/fileStore/updateFileStatus/{uuid}")
    ReturnBean updateFileStatus(@PathVariable("uuid") String uuid);

    /**
     * 根据uuid获得文件对象
     *
     * @param uuid
     * @return
     */
    @GetMapping("api/fileStore/getFileByUuid/{uuid}")
    ReturnBean getFileByUuid(@PathVariable("uuid") String uuid);
}
