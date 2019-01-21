package com.xie.ssoservice.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xie.ssoservice.entity.User;
import com.xie.ssoservice.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;


@Slf4j
@Component
public class JwtUtil {


    @Autowired
    private RedisUtil redisUtil;

    private static JwtUtil jwtUtil;

    @PostConstruct
    private void init(){
        jwtUtil=this;
    }
    public static RedisUtil getRedisUtil() {
        return jwtUtil.redisUtil;
    }

    private static String jwtKey;



    @Value("${salt}")
    public void setJwtKey(String jwtKey) {
        JwtUtil.jwtKey = jwtKey;
    }

    public String getJwtKey(){
        return jwtKey;
    }


    /**
     *
     *
     * @param subject
     * @param ttl
     * @return
     */


    public static String generateJwt(String subject,long ttl){
        long now = System.currentTimeMillis();
        Date nowDate = new Date(now);
        log.info("当前时间是: "+nowDate);
        long expire = now+ttl;
        Date expeiredAt = new Date(expire);
        Algorithm algorithm = Algorithm.HMAC256(jwtKey);
        try{
            String token = JWT.create()
                    .withIssuer("xie")
                    .withExpiresAt(expeiredAt)
                    .withSubject(subject)
                    .sign(algorithm);
            return token;
        }catch (JWTCreationException exception) {
            throw new GlobalException("token生成异常");
        }
    }

    public static DecodedJWT verifyToken(String token) throws JWTVerificationException{

        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("xie")
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt;
        }catch (JWTVerificationException e){
            throw new GlobalException("token验证异常");
        }


    }

    /**
     *
     * @param token
     * @return
     */

    public static User getJwtUser(String token) {
        String subject = null;
        User user = null;
        String id = null;
        try {
            DecodedJWT jwt = verifyToken(token);
            subject = jwt.getSubject();
            user = JSON.parseObject(subject, User.class);
            log.info("getJwt: "+ user.getId());
            user = getRedisUtil().get(user.getId(),User.class);

        } catch (Exception e) {
            throw new GlobalException("获取jwt内的user对象时异常");
        }
        return user;
    }
    /**
     * 生成subject信息
     *
     * @param user
     * @return
     */
    public static String genSubject(User user) {
        JSONObject jo = new JSONObject();
        jo.put("username", user.getUsername());
        jo.put("id", user.getId());
        return jo.toJSONString();
    }




}
