package com.yicj.study.controller;

import com.yicj.study.controller.vo.UserVo;
import com.yicj.study.error.BusinessException;
import com.yicj.study.error.EmBusinessError;
import com.yicj.study.response.CommonReturnType;
import com.yicj.study.service.model.UserModel;
import com.yicj.study.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;


@Controller("user")
@RequestMapping("/user")
@Slf4j
public class UserController extends BaseController {

    @Autowired
    private UserInfoService userInfoService ;

    //spring包装过的request，实际上是一个代理
    @Autowired
    private HttpServletRequest httpServletRequest ;

    //用户获取otp短信接口
    @GetMapping("/getOtp")
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name = "telephone") String telephone){
        //需要按照一定规则生成otp验证码
        Random random = new Random() ;
        int code = random.nextInt(99999);
        code += 10000 ;
        String otpCode = String.valueOf(code) ;

        //将otp验证码同对应的用户手机关联(使用redis将手机号与code关联)
        httpServletRequest.getSession().setAttribute(telephone,otpCode);
        //将otp验证码通过短信通道发送给用户，省略
        log.info("telephone = {}, otpCode = {}", telephone,otpCode);
        return CommonReturnType.success(null) ;
    }

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
