package com.yicj.study.config;

import javax.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yicj.study.filters.LoginFilter;

@Configuration
public class LogigFilterConfig {
	
	@Bean
	public FilterRegistrationBean<Filter> loginFilterRegistration() {
		//新建过滤器注册类
		FilterRegistrationBean<Filter> registration
					= new FilterRegistrationBean<>() ;	
		//添加我们写好的过滤器
		registration.setFilter(new LoginFilter());
		//设置过滤器的URL模式
		registration.addUrlPatterns("/*");
		return registration ;
	}
}
