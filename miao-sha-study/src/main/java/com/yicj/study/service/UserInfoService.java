package com.yicj.study.service;


import com.yicj.study.error.BusinessException;
import com.yicj.study.service.model.UserModel;

public interface UserInfoService {
    UserModel getUserById(Integer id) ;

    void register(UserModel model) throws BusinessException;
}
