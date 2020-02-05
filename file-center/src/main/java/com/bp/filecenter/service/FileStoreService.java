package com.bp.filecenter.service;


import com.bp.common.base.BaseServiceImpl;
import com.bp.common.base.BpBaseMapper;
import com.bp.common.constants.SysConstant;
import com.bp.common.utils.UserUtils;
import com.bp.filecenter.contants.TypeConstant;
import com.bp.filecenter.controller.FileStoreController;
import com.bp.filecenter.entity.FileStore;
import com.bp.filecenter.mapper.FileStoreMapper;
import com.bp.filecenter.utils.FtpUtil;
import com.bp.filecenter.utils.PropertiesUtil;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author xcj
 * @version 1.0
 * @Description: 文件仓库表服务层
 * @date 2018年10月22日
 */
@Service
public class FileStoreService extends BaseServiceImpl<FileStoreMapper,FileStore> {


    private static Logger logger = LoggerFactory.getLogger(FileStoreController.class);

    @Value("${file.local.path}")
    private String filePath;

    /**
     * 定义允许上传的文件扩展名
     */
    private HashMap<String, String> extMap = new HashMap<String, String>();

    //图片上传最大大小
    private static Long IMAGE_MAXSIZE = 2097152L;

    //文件上传最大大小
    private static Long FILE_MAXSIZE = 524288000L;

    public void init() {
        extMap.put("flash", "swf,flv,SWF,FLV");
        extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb,SWF,FLV,MP3,WAV,WMA,WMV,MID,AVI,MPG,ASF,RM,RMVB");
        extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2,pdf,DOC,DOCX,XLS,XLSX,PPT,HTM,HTML,TXT,ZIP,RAR,GZ,BZ2,PDF");
        //图片
        extMap.put("image", "gif,jpg,jpeg,png,bmp,GIF,JPG,JPEG,PNG,BMP");
        //流程部署文件
        extMap.put("bpmn", "bpmn");
        //视频
        extMap.put("video", "swf,flv,wmv,avi,mpg,asf,rm,rmvb,ogg,mp4,WebM,SWF,FLV,WMV,AVI,MPG,ASF,RM,RMVB,OGG,MP4");
        //音频
        extMap.put("audio", "mp3,wav,wma,mid,MP3,WAV,WMA,MID");
        //文档
        extMap.put("doc", "doc,docx,xls,xlsx,ppt,htm,html,txt,DOC,DOCX,XLS,XLSX,PPT,HTM,HTML,TXT");
        //其它
        extMap.put("other", "zip,rar,gz,bz2,ZIP,RAR,GZ,BZ2");
        //警察头像(头像类型上传及为有效)
        extMap.put("tx", "jpg,jpeg,png,JPG,JPEG,PNG");
        //戒毒人员头像
        extMap.put("jdrytx", "jpg,jpeg,png,JPG,JPEG,PNG");
        //文件和图片类型
        extMap.put("fileImage", "gif,jpg,jpeg,png,bmp,GIF,JPG,JPEG,PNG,BMP,doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,pdf,DOC,DOCX,XLS,XLSX,PPT,HTM,HTML,TXT,ZIP,RAR,PDF");
        //决定书
        extMap.put("jds", "gif,jpg,jpeg,png,GIF,JPG,JPEG,PNG");
        //手机app文件
        extMap.put("app","apk");
    }

    @Resource
    private FileStoreMapper fileStoreMapper;

    @Override
    public BpBaseMapper<FileStore> getMapper() {
        return fileStoreMapper;
    }

    /**
     * 文件上传方法,返回文件id,url,或报错信息等
     *
     * @param file 文件流
     * @param dir 文件种类(目录名)
     * @param request request
     * @return
     */
    public synchronized Map<String, Object> insertFileUpload(HttpServletRequest request, MultipartFile file, String dir) {
        try {
            init();
            if (dir == null || !extMap.containsKey(dir)) {
                return getError("文件类型不合法");
            }
            if (file == null || file.isEmpty()) {
                return getError("文件不能为空");
            }
            if (dir != null) {
                if (!Arrays.<String>asList(extMap.get(dir).split(SysConstant.COMMA)).contains(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(TypeConstant.SPOT) + 1))) {
                    return getError("上传文件扩展名只允许" + extMap.get(dir) + "格式");
                }
            }
            // 计算出文件后缀名
            String fileExt = FilenameUtils.getExtension(file.getOriginalFilename());
            String savePath = filePath + "/upload/";
            //本地上传文件 放到对应的项目 对应的文件夹下
            logger.debug("path:{}", savePath);
            String url1 = "http://" + request.getServerName();
            int serverPort = request.getServerPort();
            String contextPath = request.getContextPath();
            savePath = savePath.replace("/", System.getProperty("file.separator")) + dir + System.getProperty("file.separator");
            File f = new File(savePath);
            if (!f.exists()) {
                f.mkdirs();
            }
            SimpleDateFormat fileNameFormat = new SimpleDateFormat("yyyyMMddkkmmss_S");
            String fileName = fileNameFormat.format(new Date());
            //文件绝对路径
            String saveFilePath = savePath + fileName + "." + fileExt;
            logger.debug("文件绝对路径:saveFilePath:{}", saveFilePath);
            //将文件上传至本地服务器
            copy(file.getInputStream(), saveFilePath);
            //上传后文件名
            String fileNameUrl = fileName + "." + fileExt;
            //上传前的文件名
            String attacheName = file.getOriginalFilename();
            //上传url路径
            String url = FtpUtil.url + "/upload/" + dir + "/" + fileNameUrl;
            //将本地服务器文件上传至ftp服务器
            FtpUtil.upload("upload/" + dir + "/", fileNameUrl, saveFilePath);
            //如果不是bpmn格式的，则删除本地文件
//			if(!"bpmn".equals(dir)&&!"image".equals(dir)) {
//				deleteLocalFile(saveFilePath);
//			}
            Map<String, Object> msg = new HashMap<String, Object>(2);
            msg.put("error", 0);
            msg.put("code", 0);
            msg.put("url", url);
            msg.put("fileNameUrl", fileNameUrl);
            msg.put("saveFilePath", saveFilePath);
            msg.put("fileName", attacheName);
            return msg;
        } catch (Exception e) {
            logger.error("程序异常", e);
            return getError("程序异常");
        }
    }

    /**
     * 将上传文件插入文件仓库中
     *
     * @param attacheName    文件上传前名称
     * @param fileNameUrl    文件上传后名称
     * @param url            文件url地址: /bp_XXX/upload/image/XXX
     * @param saveFilePath   文件绝对路径: E:\idea\workspace\
     * @param module         模块名称
     * @param moduleFunction 模块方法名
     * @param dir            文件类型
     * @param uuid           uuid 同一条业务数据多张图片uuid相同
     * @return 文件信息
     */
    public Integer insertFileStore(String attacheName, String fileNameUrl, String url, String saveFilePath, String module, String moduleFunction, String dir, String uuid) {
        FileStore fileStore = new FileStore();
        fileStore.setAttachName(attacheName);
        fileStore.setFileName(fileNameUrl);
        fileStore.setAbsoluteUrl(saveFilePath.replace("\\\\", "/"));
        fileStore.setUrl(url);
        fileStore.setModule(module);
        fileStore.setModuleFunction(moduleFunction);
        fileStore.setInserter(UserUtils.getCurrentUser().getId());
        fileStore.setInsertTime(new Date());
        fileStore.setUuid(uuid);
        fileStore.setFileType(dir);
        //头像类型上传及为有效
        if ("tx".equals(dir)) {
            fileStore.setStatus("1");
        }
        super.insert(fileStore);
        return fileStore.getId();
    }

    /**
     * @param src 源文件
     * @param tar 目标路径
     * @category 拷贝文件
     */
    public void copy(InputStream src, String tar) throws Exception {
        // 判断源文件或目标路径是否为空
        if (null == src || null == tar || tar.isEmpty()) {
            return;
        }
        OutputStream tarOs = null;
        File tarFile = new File(tar);
        tarOs = new FileOutputStream(tarFile);
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = src.read(buffer))) {
            tarOs.write(buffer, 0, n);
        }
        if (null != src) {
            src.close();
        }
        if (null != tarOs) {
            tarOs.close();
        }

    }

    /**
     * 返回错误信息
     *
     * @param message
     * @return
     */
    public Map<String, Object> getError(String message) {
        Map<String, Object> msg = new HashMap<String, Object>(2);
        msg.put("error", 1);
        msg.put("message", message);
        return msg;
    }


    /**
     * 删除上传了的文件
     *
     * @param absoluteUrl 文件绝对路径
     */
    public void deleteLocalFile(String absoluteUrl) {
        try {
            if (null != absoluteUrl && absoluteUrl.length() > 0) {
                absoluteUrl = absoluteUrl.replaceAll("/", "\\\\");
            }
            if (null != absoluteUrl) {
                File file = new File(absoluteUrl);
                file.delete();
            }
        } catch (Exception e) {
            logger.error("删除文件失败", e);
        }
    }


    public void setFileDownloadHeader(HttpServletRequest request, HttpServletResponse response, String fileName) {
        try {
            // 中文文件名支持
            String encodedfileName = null;
            String agent = request.getHeader("USER-AGENT");
            if (null != agent && -1 != agent.indexOf("MSIE")) {// IE
                encodedfileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            } else if (null != agent && -1 != agent.indexOf("Mozilla")) {
                encodedfileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
            } else {
                encodedfileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            }
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + "\"");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据文件uuid和文件类型查询
     *
     * @param uuid
     * @param fileType
     * @return
     */
    public List<FileStore> selectFileList(String uuid, String fileType) {
        List<FileStore> fileStoreList = null;
        if (uuid != null && !"".equals(uuid)) {
            Map para = new HashMap(2);
            para.put("uuid", uuid);
//			para.put("fileType", fileType);
            para.put("status", TypeConstant.FILE_STATUS_1);
            fileStoreList = fileStoreMapper.selectFileList(para);
        }
        return fileStoreList;
    }


    /**
     * 批量删除文件包括(删除本地文件/ftp服务端内文件/数据库文件记录)
     *
     * @param fileStoreList
     */
    public void deleteFileList(List<FileStore> fileStoreList) {
        //循环删除
        for (FileStore fileStore : fileStoreList) {
            deleteFile(fileStore);
        }
    }

    /**
     * 单个删除文件包括(删除本地文件/ftp服务端内文件/数据库文件记录)
     *
     * @param fileStore
     */
    public void deleteFile(FileStore fileStore) {
        String url = fileStore.getUrl();
        if (!"".equals(url)) {
            //数据库记录删除
            super.delete(fileStore.getId());
            //本地服务器文件删除
            deleteLocalFile(fileStore.getAbsoluteUrl());
            //ftp文件删除 针对springcloudURL要经过处理,用于找到ftp文件夹目录
            FtpUtil.delete(url.substring(PropertiesUtil.getProperty("ftp.url").length(), fileStore.getUrl().lastIndexOf("/")), fileStore.getFileName());
        }
    }


    /**
     * 定时任务,自动清除文件仓库无效文件
     */
    public void autoClearFile() {
        Map para = new HashMap(1);
        para.put("status", TypeConstant.FILE_STATUS_2);
        //根据状态查出所有无效文件
        List<FileStore> fileList = fileStoreMapper.selectFileList(para);
        for (FileStore fileStore : fileList) {
            //删除无效文件
            deleteFile(fileStore);
        }
    }

    /**
     * 根据uuid修改文件状态为有效
     *
     * @param uuid 文件uuid
     */
    @Transactional(rollbackFor = Exception.class)
    @LcnTransaction
    public void updateFileStatus(String uuid) {
        try {
            fileStoreMapper.updateFileStatus(uuid);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
