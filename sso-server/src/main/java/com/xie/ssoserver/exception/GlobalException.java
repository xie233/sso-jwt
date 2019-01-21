package com.xie.ssoserver.exception;

public class GlobalException extends RuntimeException{

    private String msg;

    public GlobalException(String message) {
        super(message);
        this.msg = message;

    }

    public String getMsg(){
        return msg;
    }
}
