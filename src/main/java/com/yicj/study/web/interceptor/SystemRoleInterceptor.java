package com.yicj.study.web.interceptor;

import com.yicj.study.common.Init;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SystemRoleInterceptor extends HandlerInterceptorAdapter {
    Logger logger = LoggerFactory.getLogger(SystemRoleInterceptor.class) ;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
            if(HandlerMethod.class.isInstance(handler)){
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                Init initType =  handlerMethod.getBeanType().getAnnotation(Init.class) ;
                Init initMethod = handlerMethod.getMethodAnnotation(Init.class) ;
                String typeValue = "" ;
                String methodValue = "" ;
                if(initType!=null){
                    typeValue = initType.value() ;
                }
                if(initMethod!=null){
                    methodValue = initMethod.value() ;
                }
                logger.info("typeValue : " + typeValue + " ,methodValue : " + methodValue );
            }
            return super.preHandle(request, response, handler);
    }
}
