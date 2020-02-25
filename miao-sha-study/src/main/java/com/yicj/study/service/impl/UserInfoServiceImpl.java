package com.yicj.study.service.impl;

import com.yicj.study.dao.UserDOMapper;
import com.yicj.study.dao.UserPasswordDOMapper;
import com.yicj.study.entity.UserDO;
import com.yicj.study.entity.UserPasswordDO;
import com.yicj.study.error.BusinessException;
import com.yicj.study.error.EmBusinessError;
import com.yicj.study.service.model.UserModel;
import com.yicj.study.service.UserInfoService;
import com.yicj.study.validator.ValidationResult;
import com.yicj.study.validator.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.validation.Validator;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserDOMapper userDOMapper ;
    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper ;

    @Autowired
    private ValidatorImpl validator ;

    @Override
    public UserModel getUserById(Integer id) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        UserPasswordDO passwordDO = null ;
        if (userDO != null){
            passwordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        }
        return convertFromDataObject(userDO,passwordDO) ;
    }

    @Transactional
    @Override
    public void register(UserModel model) throws BusinessException {
        if (model == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR) ;
        }

        ValidationResult result = validator.validate(model);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,
                    result.getErrMsg()) ;
        }

        //实现model - > do方法
        UserDO userDO = converUserDOtFromModel(model) ;
        try {
            userDOMapper.insertSelective(userDO) ;
        }catch (DuplicateKeyException ex){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"手机号已重复注册") ;
        }

        //赋值给model
        model.setId(userDO.getId());
        UserPasswordDO passwordDO = convertPasswordDOFromModel(model);
        userPasswordDOMapper.insertSelective(passwordDO) ;
    }

    @Override
    public UserModel validateLogin(String telephone, String encrptPassword) throws BusinessException {
        //通过用户手机获取用户登录信息
        UserDO userDO = userDOMapper.selectByTelephone(telephone);
        if(userDO ==null){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL) ;
        }
        UserPasswordDO passwordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        UserModel model = convertFromDataObject(userDO, passwordDO);
        //比对用户传入的密码与数据库中的密码是否一样
        if (com.alibaba.druid.util.StringUtils.equals(passwordDO.getEncrptPassword(),encrptPassword)){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL) ;
        }
        return model ;
    }

    private UserDO converUserDOtFromModel(UserModel model){
        if(model == null){
            return null ;
        }
        UserDO userDO = new UserDO() ;
        BeanUtils.copyProperties(model,userDO);
        return userDO ;
    }

    private UserPasswordDO convertPasswordDOFromModel(UserModel model){
        if(model == null){
            return null ;
        }
        UserPasswordDO passwordDO = new UserPasswordDO() ;
        passwordDO.setEncrptPassword(model.getEncrptPassword());
        passwordDO.setUserId(model.getId());
        return passwordDO ;
    }


    private UserModel convertFromDataObject(UserDO userDO, UserPasswordDO passwordDO){
        if(userDO ==null){
            return null ;
        }
        UserModel model = new UserModel() ;
        BeanUtils.copyProperties(userDO,model);
        if(passwordDO != null){
            model.setEncrptPassword(passwordDO.getEncrptPassword());
        }
        return model ;
    }
}
