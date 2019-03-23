package com.yicj.study.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {
	//表示符：表示当前用户未登录（可以根据自己项目的需要改为json样式）
	String NO_LOGIN = "您还未登录" ;
	//不需要登录就可以访问的路径(比如:注册登录等)
	String [] includeUrls = new String [] {"/login","register","/login.html"} ;
	@Override
	public void doFilter(ServletRequest servletReq, ServletResponse servletResp, FilterChain filterChina)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest)servletReq ;
		HttpServletResponse response = (HttpServletResponse)servletResp ;
		HttpSession session = request.getSession(false) ;	
		String uri = request.getRequestURI() ;
		System.out.println("filter url : " + uri);
		//是否需要过滤
		String contextPath = request.getContextPath() ;
		boolean needFilter = isNeedFilter(uri,contextPath) ;
		if(!needFilter) {//不需要过滤直接传给下一个过滤器
			filterChina.doFilter(servletReq, servletResp);
		}else {//需要过滤
			if(session != null && session.getAttribute("user")!=null) {
				filterChina.doFilter(servletReq, servletResp);
			}else {
				String requestType = request.getHeader("X-Requested-With") ;
				//判断是否是ajax请求(因为ajax页面不跳转,所以这块需要判断)
				if(requestType != null && 
						"XMLHttpRequest".equals(requestType)) {
					response.getWriter().write(this.NO_LOGIN);
				}else {
					//重定向到登录页(需要在static问价夹下建立此html文件)
					response.sendRedirect(contextPath+"/login.html");
				}
				return ;
			}
		}
	}
	
	private boolean isNeedFilter(String uri,String contextPath) {
		uri = uri.substring(contextPath.length(), uri.length()) ;
		System.out.println("uri : " + uri);
		for(String includeUrl : includeUrls) {
			if(includeUrl.equals(uri)) {
				return false;
			}
		}
		return true;
	}

}
