package com.bp.common.exception;

/**
 * @auther: 钟欣凯
 * @date: 2018/12/25 15:40
 */

public class BpException extends RuntimeException{


    private String message;

    public BpException(String message) {
        super(message);
        this.message = message;
    }
    public BpException() {

    }

    public BpException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
