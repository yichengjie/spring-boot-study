package com.yicj.study.config;


import com.yicj.study.web.interceptor.SystemRoleInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

     @Override
     public void addInterceptors(InterceptorRegistry registry){
         List<String> commonPathPatterns = getExcludeCommonPathPatterns();
         registry.addInterceptor(new SystemRoleInterceptor())
                 .addPathPatterns("/**")
                 .excludePathPatterns(commonPathPatterns.toArray(new String[] {}));
     }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//设置允许跨域的路径
                .allowedOrigins("*")//设置允许跨域请求的域名
                .allowCredentials(true)//是否允许证书 不再默认开启
                .allowedMethods("GET", "POST", "PUT", "DELETE")//设置允许的方法
                .maxAge(3600);//跨域允许时间
    }

    /**
     * 排他设置
     * @return list
     */
    private List<String> getExcludeCommonPathPatterns() {
        List<String> list = new ArrayList<String>();
        String[] urls = { "/login/**", "/task/**", "/v2/api-docs", "/swagger-resources/**" };
        Collections.addAll(list, urls);
        return list;
    }


}
