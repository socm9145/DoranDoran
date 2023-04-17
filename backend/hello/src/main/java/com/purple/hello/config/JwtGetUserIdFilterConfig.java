package com.purple.hello.config;

import com.purple.hello.filters.JwtGetUserIdFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtGetUserIdFilterConfig {
    @Bean
    public FilterRegistrationBean<JwtGetUserIdFilter> jwtGetUserIdFilter(){
        FilterRegistrationBean<JwtGetUserIdFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtGetUserIdFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
