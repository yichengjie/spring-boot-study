package com.yicj.study.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Setter
@Getter
public class CommonReturnType <T> implements Serializable  {

    //表明对应请求的返回处理结果"success"或"fail"
    private String status ;
    //若status=success,则data返回前端需要的json数据
    //若status=fail,则data使用通用的错误码格式
    private T data ;

    public static <R> CommonReturnType  success(R data){
        return create(data,"success") ;
    }

    public static <R> CommonReturnType  fail(R data){
        return create(data,"fail") ;
    }

    private static <R> CommonReturnType  create(R data ,String status){
        CommonReturnType<R> type = new CommonReturnType<>();
        type.setData(data);
        type.setStatus(status);
        return type ;
    }

}
