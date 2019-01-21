package com.xie.ssoservice.util;


import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CookieUtil {


    public static void create(HttpServletResponse response,String name, String value){
        Cookie cookie = new Cookie(name,value);
        cookie.setPath("/");
        cookie.setMaxAge(36000);
        cookie.setHttpOnly(true);//Cookies，当使用带有HttpOnly的cookie标志时，通过JavaScript是无法访问的，并且对XSS是免疫的
        cookie.setDomain("localhost");
        response.addCookie(cookie);
    }

    public static void clear(HttpServletResponse response,String name){
        Cookie cookie = new Cookie(name,null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static String get(HttpServletRequest request,String name){
        Cookie cookie = WebUtils.getCookie(request,name);
        return cookie!=null? cookie.getValue():null;
    }
}
