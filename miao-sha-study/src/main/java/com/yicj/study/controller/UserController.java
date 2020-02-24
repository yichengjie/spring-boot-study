package com.yicj.study.controller;

import com.yicj.study.controller.vo.UserVo;
import com.yicj.study.error.BusinessException;
import com.yicj.study.error.EmBusinessError;
import com.yicj.study.response.CommonReturnType;
import com.yicj.study.service.model.UserModel;
import com.yicj.study.service.UserInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller("user")
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserInfoService userInfoService ;

    @GetMapping("/getUser")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam("id") Integer id) throws BusinessException {
        UserModel userModel = userInfoService.getUserById(id);
        if (userModel == null){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST) ;
        }
        return CommonReturnType.success(this.convertFromModel(userModel)) ;
    }


    private UserVo convertFromModel(UserModel userModel){
        UserVo userVo = new UserVo() ;
        BeanUtils.copyProperties(userModel,userVo);
        return userVo ;
    }

}
