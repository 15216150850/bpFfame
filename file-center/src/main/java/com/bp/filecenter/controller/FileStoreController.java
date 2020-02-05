package com.bp.filecenter.controller;

import com.alibaba.fastjson.JSON;
import com.bp.common.anno.LogAnnotation;
import com.bp.common.base.BaseController;
import com.bp.common.bean.ReturnBean;
import com.bp.common.utils.Common;
import com.bp.common.utils.IdUtils;
import com.bp.filecenter.entity.FileStore;
import com.bp.filecenter.service.FileStoreService;
import com.bp.filecenter.utils.FtpUtil;
import com.bp.filecenter.utils.PropertiesUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * @author xcj
 * @version 1.0
 * @Description: 文件仓库表控制层
 * @date 2018年10月22日
 */
@RestController
public class FileStoreController extends BaseController {

    @Resource
    private FileStoreService fileStoreService;


    /**
     * 添加文件仓库表
     *
     * @param file
     * @return
     */
    @LogAnnotation(module = "上传文件")
    @PostMapping("/fileStore/insert")
    public Map<String, Object> insert(@RequestParam("file") MultipartFile file, String dir, String module, String moduleFunction, String uuid) {
        Map msg = fileStoreService.insertFileUpload(request, file, dir);
        if (1 != Integer.valueOf(msg.get("error").toString())) {
            String attacheName = Common.getObjStr(msg.get("fileName"));
            String fileNameUrl = Common.getObjStr(msg.get("fileNameUrl"));
            String url = Common.getObjStr(msg.get("url"));
            String saveFilePath = Common.getObjStr(msg.get("saveFilePath"));
            Integer fileId = fileStoreService.insertFileStore(attacheName, fileNameUrl, url, saveFilePath, module, moduleFunction, dir, uuid);
            msg.put("fileId", fileId);
            msg.put("uuid", uuid);
        }
        return msg;
    }

    /**
     * 批量上传文件
     *
     * @param file
     * @return
     */
    @LogAnnotation(module = "批量上传文件")
    @PostMapping("/fileStore/insertFiles")
    public Map<String, Object> insertFiles(@RequestParam("file") MultipartFile file, String dir, String module, String moduleFunction) {
        String uuid = IdUtils.getUuid();
        Map msg = fileStoreService.insertFileUpload(request, file, dir);
        if (1 != Integer.valueOf(msg.get("error").toString())) {
            String attacheName = Common.getObjStr(msg.get("fileName"));
            String fileNameUrl = Common.getObjStr(msg.get("fileNameUrl"));
            String url = Common.getObjStr(msg.get("url"));
            String saveFilePath = Common.getObjStr(msg.get("saveFilePath"));
            Integer fileId = fileStoreService.insertFileStore(attacheName, fileNameUrl, url, saveFilePath, module, moduleFunction, dir, uuid);
            msg.put("fileId", fileId);
            msg.put("uuid", uuid);
        }
        return msg;
    }

    /**
     * 上传流程部署文件
     *
     * @param file
     * @return
     */
    @LogAnnotation(module = "上传流程部署文件")
    @PostMapping("/fileStore/insertBpmn")
    public Map<String, Object> insertBpmn(@RequestParam("file") MultipartFile file, String dir) {
        Map msg = fileStoreService.insertFileUpload(request, file, dir);
        return msg;
    }

    /**
     * 修改文件仓库表
     *
     * @param fileStore 文件仓库表
     * @return
     */
    @LogAnnotation(module = "修改文件")
    @PostMapping("/fileStore/fileStore/update")
    public ReturnBean update(FileStore fileStore) {
        fileStoreService.update(fileStore);
        return ReturnBean.ok();
    }

    /**
     * 删除文件仓库表
     *
     * @param id 文件仓库表 id
     * @return
     */
    @LogAnnotation(module = "删除文件")
    @DeleteMapping("/fileStore/delete/{id}")
    public ReturnBean delete(@PathVariable Integer id) {
        FileStore fileStore = fileStoreService.selectEntityById(id);
        fileStoreService.deleteFile(fileStore);
        return ReturnBean.ok();
    }

    /**
     * 批量删除文件仓库表
     *
     * @param ids 文件仓库表 ids
     * @return
     */
    @LogAnnotation(module = "批量删除文件")
    @PostMapping("/fileStore/deleteByIds")
    @ResponseBody
    public ReturnBean deleteByIds(String ids) {
        fileStoreService.deleteByIds(ids);
        return ReturnBean.ok();
    }

    /**
     * 根据uuid查询文件
     *
     * @throws IOException
     */
    @GetMapping("/fileStore/getByUuid/{uuid}")
    public Object getByUuid(@PathVariable("uuid") String uuid) throws IOException {
        List<FileStore> fileList = fileStoreService.selectFileList(uuid, "file");
        return fileList.size() == 0 ? "-1" : JSON.toJSON(fileList);
    }


    /**
     * 通过文件id文件下载
     *
     * @param id 文件id
     * @throws IOException
     */
    @LogAnnotation(module = "文件下载")
    @GetMapping("/fileStore/download/{id}")
    public void fileDownloadById(@PathVariable("id") Integer id) {
        // 获取文件id 得到该文件信息
        FileStore fileStore = fileStoreService.selectEntityById(id);
        if (fileStore.getUrl() != null && !"".equals(fileStore.getUrl())) {
            String url = fileStore.getUrl().substring(PropertiesUtil.getProperty("ftp.url").length(), fileStore.getUrl().lastIndexOf("/"));
            //response; ftp服务器文件路径; ftp服务器文件名称; 下载时文件名称
            FtpUtil.download(response, url, fileStore.getFileName(), fileStore.getAttachName());
        }
    }

    /**
     * 通过文件url下载文件
     *
     * @param url  文件url
     * @param name 下载时生成的文件名
     */
    @LogAnnotation(module = "文件下载")
    @GetMapping("/fileStore/downloadByUrl")
    public void fileDownloadByUrl(@RequestParam("url") String url, @RequestParam("name") String name) {
        if (url != null && !"".equals(url)) {
            String filePath = url.substring(PropertiesUtil.getProperty("ftp.url").length(), url.lastIndexOf("/"));
            //拼装带文件后缀的文件名 没有文件名则默认返回文件名
            if (name != null && !"".equals(name)) {
                name = name + url.substring(url.lastIndexOf("."));
            } else {
                name = url.substring(url.lastIndexOf("/"));
            }
            //response; ftp服务器文件路径; ftp服务器文件名称; 下载时文件名称
            FtpUtil.download(response, filePath, url.substring(url.lastIndexOf("/") + 1), name);
        }
    }

    @GetMapping("/fileStore/getByUuid/")
    public String get() {
        return "-1";
    }

}
