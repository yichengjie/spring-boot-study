package com.yicj.study.service.impl;

import com.yicj.study.dao.UserDOMapper;
import com.yicj.study.dao.UserPasswordDOMapper;
import com.yicj.study.entity.UserDO;
import com.yicj.study.entity.UserPasswordDO;
import com.yicj.study.model.UserModel;
import com.yicj.study.service.UserInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserDOMapper userDOMapper ;
    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper ;

    @Override
    public UserModel getUserById(Integer id) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        UserPasswordDO passwordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        return convertFromDataObject(userDO,passwordDO) ;
    }


    private UserModel convertFromDataObject(UserDO userDO, UserPasswordDO passwordDO){
        UserModel model = new UserModel() ;
        BeanUtils.copyProperties(userDO,model);
        if(passwordDO != null){
            model.setEncrptPassword(passwordDO.getEncrptPassword());
        }
        return model ;
    }
}
