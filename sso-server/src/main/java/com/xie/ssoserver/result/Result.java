package com.xie.ssoserver.result;


import lombok.Data;

@Data
public class Result<T> {

    private int code;
    private String msg;
    private T data;

    public static <T> Result<T> ok(T data){
        Result result = new Result(200,"success!");
        result.setData(data);
        return result;
    }
    public static <T> Result<T> error(Msg msg){
        return new Result<>(msg);
    }
    private Result(T data){
        this.data = data;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Result(Msg msg){
        if(msg!=null){
            this.code = msg.getCode();
            this.msg = msg.getMessage();
        }
    }



}
