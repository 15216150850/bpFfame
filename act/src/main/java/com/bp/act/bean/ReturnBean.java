package com.bp.act.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author
 * @version 1.0
 * @Description: 返回bean
 * @date 2016年7月16日
 */
@ApiModel(value = "接口返回")
public class ReturnBean<T> implements Serializable {

    @ApiModelProperty(value = "响应码:http://xxxxxxxxxxxxxxxxxx")
    public int code;

    @ApiModelProperty(value = "信息提示")
    public String msg;

    @ApiModelProperty
    public T data;


    @ApiModelProperty(value = "列表总条数")
    public Long count;


    /**
     * 构造方法
     */
    public ReturnBean() {

    }

    public ReturnBean(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ReturnBean(int code, String msg, T data, Long count) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.count = count;
    }

    /**
     * 成功
     *
     * @return
     */
    public static ReturnBean ok() {
        return new ReturnBean(ReturnCode.OK.getCode(), ReturnCode.OK.getMsg(), null);
    }

    /**
     * 成功+自定义消息
     *
     * @return
     */
    public static ReturnBean ok(String msg) {
        return new ReturnBean(ReturnCode.OK.getCode(), msg, null);
    }

    /**
     * 返回数据列表
     *
     * @param data
     * @return
     */
    public static ReturnBean list(Object data, Long count) {
        return new ReturnBean(ReturnCode.OK.getCode(), ReturnCode.OK.getMsg(), data, count);
    }

    /**
     * 成功
     *
     * @param data
     * @return
     */
    public static ReturnBean ok(Object data) {
        return new ReturnBean(ReturnCode.OK.getCode(), ReturnCode.OK.getMsg(), data);
    }

    /**
     * 自定义返回内容
     *
     * @param data
     * @return
     */
    public static ReturnBean ok(Object data, Integer code, String msg) {
        return new ReturnBean(code, msg, data);
    }

    /**
     * 失败
     *
     * @return
     */
    public static ReturnBean error() {
        return new ReturnBean(ReturnCode.ERROR.getCode(), ReturnCode.ERROR.getMsg(), null);
    }

    /**
     * 自定义失败信息(自定义错误msg)
     *
     * @return
     */
    public static ReturnBean error(String msg) {
        return new ReturnBean(ReturnCode.ERROR.getCode(), msg, null);
    }

    /**
     * 自定义失败信息(使用全局的响应码和消息提示)
     *
     * @param rc
     * @return
     */
    public static ReturnBean error(ReturnCode rc) {
        return new ReturnBean(rc.getCode(), rc.getMsg(), null);
    }

    /**
     * 自定义失败信息
     *
     * @param rc
     * @return
     */
    public static ReturnBean error(ReturnCode rc, String msg) {
        return new ReturnBean(rc.getCode(), msg, null);
    }

}
