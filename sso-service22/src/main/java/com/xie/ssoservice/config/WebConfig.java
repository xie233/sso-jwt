package com.xie.ssoservice.config;

import com.xie.ssoservice.interceptor.JwtIntercepto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public JwtIntercepto jwtInterceptor() {
        return new JwtIntercepto();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor());


    }
}
