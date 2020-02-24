package com.yicj.study.error;

public interface CommonError {

    int getErrCode() ;
    String getErrMsg() ;
    CommonError setErrMsg(String errMsg) ;

}
