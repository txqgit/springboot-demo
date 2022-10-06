package com.example.common.advice.restadvice;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration  //必须加这个注解, Spring才能统一管理当前的拦截器实例
public class ComRestControllerConfigure implements WebMvcConfigurer {
    /* 在应用启动阶段就执行 */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ComRestControllerInterceptor());
    }
}
