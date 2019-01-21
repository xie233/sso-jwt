package com.xie.ssoservice.interceptor;


import com.xie.ssoservice.util.CookieUtil;
import com.xie.ssoservice.util.JwtUtil;


import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@Component
public class JwtIntercepto implements HandlerInterceptor{


    @Value(value = "${TTL}")
    private String TTL;

    
    
    @Value("${ServerHost}")
    String serverHost;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String jwt = CookieUtil.get(request, "accessToken");

        log.info("accessToken is :" + jwt);
        if(jwt==null){
            response.sendRedirect(serverHost);
            return false;
        }
        try {
            JwtUtil.verifyToken(jwt);

        } catch (Exception e) {
            log.info("jwt过期, 需要重新登录");
            log.info(serverHost + "?redirect=" + request.getRequestURL());
            response.sendRedirect(serverHost+ "?redirect=" + request.getRequestURL());
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
