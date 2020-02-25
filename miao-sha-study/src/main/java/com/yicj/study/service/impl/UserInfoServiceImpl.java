package com.yicj.study.service.impl;

import com.yicj.study.dao.UserDOMapper;
import com.yicj.study.dao.UserPasswordDOMapper;
import com.yicj.study.entity.UserDO;
import com.yicj.study.entity.UserPasswordDO;
import com.yicj.study.error.BusinessException;
import com.yicj.study.error.EmBusinessError;
import com.yicj.study.service.model.UserModel;
import com.yicj.study.service.UserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserDOMapper userDOMapper ;
    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper ;

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
        if(StringUtils.isEmpty(model.getName()) ||
                model.getGender() == null ||
                model.getAge() == null ||
                StringUtils.isEmpty(model.getTelephone())){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR)
        }
        //实现model - > do方法
        UserDO userDO = converUserDOtFromModel(model) ;
        userDOMapper.insertSelective(userDO) ;
        //赋值给model
        model.setId(userDO.getId());
        UserPasswordDO passwordDO = convertPasswordDOFromModel(model);
        userPasswordDOMapper.insertSelective(passwordDO) ;
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
