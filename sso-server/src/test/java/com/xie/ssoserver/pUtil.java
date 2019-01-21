package com.xie.ssoserver;



import com.xie.ssoserver.entity.User;
import com.xie.ssoserver.util.JwtUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class pUtil {



    @Autowired
    private JwtUtil jwtUtil;

    @Test
    public void getValue(){
        Map<String,User> map = new HashMap<>();

        User user = new User();
        user.setId("2");
        user.setUsername("h1");
        user.setPassword("h1pd");
        user.setWords("That's right!");
        map.put("2",user);
        String subject = jwtUtil.genSubject(user);
        String token = jwtUtil.generateJwt(subject,2*60000);
//        String token = UUID.randomUUID().toString().replace("-","");
        User user1 = jwtUtil.getJwtUser(token);
        user1 = map.get(user1.getId());

        System.out.println(user1.toString());

 }


}
