package com.bp.common.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 
 * @version 1.0
 * @Description: DataTable数据返回
 * @date 2016年8月1日
 */
public class DataTable implements Serializable {

    /**
     * 每页显示数据
     */
    private Integer length = 10;
    /**
     * 页数
     */
    private Integer pageNum = 1;
    /**
     * 总数
     */
    private Long recordsFiltered;
    /**
     * 数据
     */
    private List data;
    /**
     * 总数
     */
    private Long count = getRecordsFiltered();
    /**
     * 请求编码
     */
    private String code = "0";
    /**
     * 信息
     */
    private String msg = "";

    public Long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(Long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer start) {
        Integer page = start / this.getLength();
        this.pageNum = page + 1;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
