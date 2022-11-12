package com.lauheng.cors;

import com.lauheng.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Bean
    public WebMvcConfigurer corsConfigs(){
        return new WebMvcConfigurer(){
            public void addCorsMappings(CorsRegistry registry){
                registry.addMapping("/**")
                        .allowedHeaders("*")
                        .allowedMethods("*")
// .allowCredentials(true)
                        .allowedOrigins("*")
                        .maxAge(3600L);
            }
        };
    }

    /**
     * 注册拦截器
     */

    @Override
    public void addInterceptors(InterceptorRegistry registry){
       InterceptorRegistration registration=
               registry.addInterceptor(new LoginInterceptor());
       //添加拦截内容
        registration.addPathPatterns("/**");
        //添加不拦截内容
        registration.excludePathPatterns("/users/loginIn",
                "/users/queryuserInfo","/team/queryTeamAll",
                "/activity/findByTeamId");
    }

}

