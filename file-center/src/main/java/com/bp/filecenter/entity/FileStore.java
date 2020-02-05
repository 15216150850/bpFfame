package com.bp.filecenter.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.bp.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author xcj
 * @version 1.0
 * @Description: 文件仓库表实体类
 * @date 2018年10月22日
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "文件仓库表")
@TableName(value = "file_store")
public class FileStore extends BaseEntity {

    /**
     * 文件唯一标识
     */
    private String uuid;

    /**
     * 附件名(原文件名)
     */
    private String attachName;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * URL路径
     */
    private String url;

    /**
     * 绝对路径
     */
    private String absoluteUrl;

    /**
     * 模块
     */
    private String module;

    /**
     * 模块-详情
     */
    private String moduleFunction;

	/**
	 * 文件状态
	 */
	private String status;

}