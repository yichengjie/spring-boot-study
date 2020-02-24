package com.yicj.study.error;

public enum  EmBusinessError implements CommonError {
    //通用错误类型00001
    PARAMETER_VALIDATION_ERROR(00001,"参数不合法"),
    //10000开头为用户信息相关错误定义
    USER_NOT_EXIST(10001,"用户不存在")
    ;
    private int errCode ;
    private String errMsg ;

    EmBusinessError(int errCode, String errMsg) {
        this.errCode = errCode ;
        this.errMsg = errMsg ;
    }

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }


    //当使用通用错误时，可以在这里定制不同的errMsg错误信息
    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg ;
        return this;
    }
}
