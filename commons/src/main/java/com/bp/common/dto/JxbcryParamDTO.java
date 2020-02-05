package com.bp.common.dto;

import lombok.Data;

/**
 * @author zhangyu
 * @date 2017-07-17
 * 教学编班-查询戒毒人员-参数封装DTO
 */
@Data
public class JxbcryParamDTO {
    private int page;
    private int limit;
    /**
     * 戒毒人员姓名
     */
    private String xm;
    /**
     * 分期情况
     */
    private String fqqk;
    /**
     * 班次编码
     */
    private String bcbm;
    /**
     * 部门编码
     */
    private String bmbm;
    /**
     * 排序字段
     */
    private String field;
    /**
     * 排序值
     */
    private String order;
}
