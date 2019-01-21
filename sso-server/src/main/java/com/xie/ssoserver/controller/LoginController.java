package com.xie.ssoserver.controller;

import com.xie.ssoserver.vo.UserVO;
import com.xie.ssoserver.entity.User;
import com.xie.ssoserver.result.Result;
import com.xie.ssoserver.util.CookieUtil;
import com.xie.ssoserver.util.JwtUtil;
import com.xie.ssoserver.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.concurrent.atomic.AtomicInteger;


@Controller
@Slf4j
public class LoginController {

    private AtomicInteger uid = new AtomicInteger(1);
    @Value(value = "${TTL}")
    private String TTL;
    @Value(value = "${refreshTTL}")
    private String refreshTTL;
    private static String accessToken;
    private static String refreshJwt;

    @Autowired
    RedisUtil redisUtil;

    @RequestMapping("/")
    public String index(){
        return "index";
    }




    @PostMapping("/login")
    @ResponseBody
    public Result<User> login(HttpServletResponse response,
                      @Valid UserVO userVO, Model model) throws Exception {
        log.info(userVO.toString());
        if (true) {
            String idd = String.valueOf(uid.getAndIncrement());
            User user = new User();
            user.setId(idd);
            user.setUsername(userVO.getUsername());
            user.setPassword(userVO.getWords());
            user.setWords("我可啥也没说");
            model.addAttribute("user",user);
            redisUtil.set(idd,user);
            String subject = JwtUtil.genSubject(user);
            accessToken = JwtUtil.generateJwt(subject, Long.parseLong(TTL) * 60000);
            log.info("accessToken: "+accessToken);
//            refreshJwt = JwtUtil.generateJwt(subject, Long.parseLong(refreshTTL) * 60000);
            CookieUtil.addCookie(response, "accessToken", accessToken);
//            CookieUtil.addCookie(response, "refreshJwt", refreshJwt);
            return Result.ok(user);
        } else {
            return null;
        }
    }
}
