package org.example.config;

import jakarta.annotation.Resource;
import org.example.interceptor.TraceIdInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author nanak
 *
 * WebMvcConfigurer 配置类
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Resource
    private TraceIdInterceptor traceIdInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截所有请求，排除静态资源
        registry.addInterceptor(traceIdInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**", "/favicon.ico");
    }
}
