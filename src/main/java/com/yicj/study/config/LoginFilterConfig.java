package com.yicj.study.config;

import javax.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

import com.yicj.study.web.filters.LoginFilter;

//@Configuration
public class LoginFilterConfig {
	
	//@Bean
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
