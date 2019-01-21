package com.xie.ssoserver.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 密码只能数字字母6-20位
 */
public class ValidatorUtil {

    private static final Pattern passPattern = Pattern.compile("^[0-9a-zA-Z]{6,33}");

    public static boolean isPassword(String s){
        if(s.isEmpty()){
            return false;
        }
        Matcher m = passPattern.matcher(s);
        return m.matches();
    }

    public static void main(String[] args) {
        System.out.println(isPassword("AAAAaa"));
    }
}
