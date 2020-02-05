package com.bp.act.client;



import com.bp.act.bean.ReturnBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 文件服务客户端
 *
 * @author 钟欣凯
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
    public ReturnBean updateFileStatus(@PathVariable("uuid") String uuid);
}
