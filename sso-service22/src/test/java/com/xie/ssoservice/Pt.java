package com.xie.ssoservice;


import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xie.ssoservice.entity.User;
import com.xie.ssoservice.util.JwtUtil;
import com.xie.ssoservice.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Pt {

    @Autowired
    private RedisUtil redisUtil;



    @Test
    public void getSD(){

        User user = new User();
        user.setId("p2242");
        user.setUsername("h1");
        user.setPassword("h1pd");
        user.setWords("That's right!");
        redisUtil.set(user.getId(),user);
        String subject = JwtUtil.genSubject(user);
        String token = JwtUtil.generateJwt(subject,2*60000);
//        String token = UUID.randomUUID().toString().replace("-","");
        User user1 = JwtUtil.getJwtUser(token);

//        User u = redisUtil.get("1", User.class);
        System.out.println(user.toString());

    }
}
