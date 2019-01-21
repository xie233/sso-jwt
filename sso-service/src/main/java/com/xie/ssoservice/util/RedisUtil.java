package com.xie.ssoservice.util;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class RedisUtil {

    @Autowired
    RedisConfig config;

    public JedisPool pool(){
        JedisPool jedisPool = new JedisPool(
                config.getHost(),
                config.getPort()
                );
        return jedisPool;
    }

    public <T> boolean set(String key,T value){
        Jedis jedis = null;
        try{
            jedis = pool().getResource();
            String v = beanToString(value);
            jedis.set(key,v);
            return true;
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }

    public <T> T get(String key,Class<T> tClass){
        Jedis jedis = null;
        try{
            jedis = pool().getResource();
            String v = jedis.get(key);
            T t = stringToBean(v,tClass);
            return t;
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }

    }
    private <T> T stringToBean(String value,Class<T> tClass){
        if(value==null|| value.length() <= 0 || tClass == null){
            return null;
        }
        if(tClass ==int.class || tClass==Integer.class){
            return (T)Integer.valueOf(value);
        }else if(tClass==String.class){
            return (T)value;
        }else if(tClass==long.class|| tClass==Long.class){
            return (T)Long.valueOf(value);
        }else {
            return JSON.parseObject(value,tClass);
        }
    }

    private <T> String beanToString(T value){
        if(value==null){
            return null;
        }
        Class<?> clazz = value.getClass();
        if(clazz ==int.class || clazz==Integer.class){
            return ""+value;
        }else if(clazz==String.class){
            return (String)value;
        }else if(clazz==long.class|| clazz==Long.class){
            return ""+value;
        }else {
            return JSON.toJSONString(value);
        }

    }




}
