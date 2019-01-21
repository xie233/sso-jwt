package com.xie.ssoservice.controller;


import com.xie.ssoservice.entity.User;
import com.xie.ssoservice.util.CookieUtil;
import com.xie.ssoservice.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;


@Slf4j
@Controller
public class HomeController {


    @RequestMapping("/")
    public String home(HttpServletRequest request, Model model){
        User user = JwtUtil.getJwtUser(CookieUtil.get(request,"accessToken"));
        model.addAttribute("user",user);
        return "home";
    }



}
