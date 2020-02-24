package com.yicj.study.controller;

import com.yicj.study.error.BusinessException;
import com.yicj.study.error.EmBusinessError;
import com.yicj.study.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseController {

    //对于Business类的这种异常来说，应该时业务逻辑上的问题，或则时正常的业务逻辑错误，
    //而并非是服务端不能处理的错误
    //定义exceptionHandler解决未被controller层吸收的exception
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object exceptionHandler(HttpServletRequest req, Exception ex){
        Map<String,Object> respData = new HashMap<>() ;
        if(ex instanceof BusinessException){
            BusinessException businessException = (BusinessException)ex ;
            respData.put("errCode",businessException.getErrCode()) ;
            respData.put("errMsg",businessException.getErrMsg()) ;
        }else {
            respData.put("errCode", EmBusinessError.UNKNOW_ERROR.getErrCode()) ;
            respData.put("errMsg",EmBusinessError.UNKNOW_ERROR.getErrMsg()) ;
        }
        CommonReturnType commonReturnType = CommonReturnType.fail(respData) ;
        return commonReturnType ;

    }
}
