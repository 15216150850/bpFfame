package com.bp.filecenter.controller.server;

import com.bp.common.base.BaseController;
import com.bp.common.bean.ReturnBean;
import com.bp.filecenter.entity.FileStore;
import com.bp.filecenter.service.FileStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FileStoreServer extends BaseController {

    @Autowired
    private FileStoreService fileStoreService;

    /**
     * 有关文件上传的新增修改,插入数据后,同步修改文件状态为有效(有文件操作的必须调用的方法)
     * @param uuid 文件uuid
     * @return 返回操作成功
     */
    @GetMapping("api/fileStore/updateFileStatus/{uuid}")
    public ReturnBean updateFileStatus(@PathVariable String uuid){
        fileStoreService.updateFileStatus(uuid);
        return ReturnBean.ok();
    }


    /**
     * 根据uuid获得文件
     * @param uuid 文件uuid
     * @return 返回操作成功
     */
    @GetMapping("api/fileStore/getFileByUuid/{uuid}")
    public ReturnBean getFileByUuid(@PathVariable String uuid){
        List<FileStore> fileStoreList = fileStoreService.selectFileList(uuid, "1");
        return ReturnBean.ok(fileStoreList);
    }

    /**
     * 插入文件数据
     * @param fileStore
     * @return
     */
    @PostMapping("api/fileStore/insertFileStore")
    public ReturnBean insertFileStore(@RequestBody FileStore fileStore){
        fileStoreService.insertFileStore(fileStore.getAttachName(), fileStore.getFileName(), fileStore.getUrl(), fileStore.getAbsoluteUrl(), fileStore.getModule(), fileStore.getModuleFunction(), fileStore.getFileType(), fileStore.getUuid());
        return ReturnBean.ok();
    }

}
