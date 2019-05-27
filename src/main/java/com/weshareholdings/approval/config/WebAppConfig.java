package com.weshareholdings.approval.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Component
public class WebAppConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册自定义拦截器，添加拦截路径和排除拦截路径
        List<String> excludePath = new ArrayList<>();
        excludePath.add("/snaker/auth/login");
        excludePath.add("/snaker/wechat/login");
        registry.addInterceptor(new InterceptorConfig()).addPathPatterns("/snaker/**").excludePathPatterns(excludePath);
        super.addInterceptors(registry);
    }

    @Bean
    public InterceptorConfig getInterceptorConfig() {
        return new InterceptorConfig();
    }
}
