package com.xie.ssoserver.result;


import lombok.Data;

@Data
public class Msg {

    private int code;
    private String message;

    public Msg(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Msg PASSWORD_WRONG = new Msg(2067, "密码格式错误");
    public static Msg SUCCESS= new Msg(0, "成功ss");

}
