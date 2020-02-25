package com.yicj.study.controller;

import com.alibaba.druid.util.StringUtils;
import com.yicj.study.controller.vo.UserVo;
import com.yicj.study.error.BusinessException;
import com.yicj.study.error.EmBusinessError;
import com.yicj.study.response.CommonReturnType;
import com.yicj.study.service.model.UserModel;
import com.yicj.study.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;


@Controller("user")
@RequestMapping("/user")
@Slf4j
@CrossOrigin //支持跨域
public class UserController extends BaseController {

    @Autowired
    private UserInfoService userInfoService ;

    //spring包装过的request，实际上是一个代理
    @Autowired
    private HttpServletRequest httpServletRequest ;

    //用户注册接口
    @RequestMapping(value = "/register",consumes = {"application/x-www-form-urlencoded"})
    @ResponseBody
    public CommonReturnType register(String telephone,String otpCode,
         String name, Integer gender, Integer age,String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //验证手机号和对应的otpCode相符合
        String serverOtpCode = (String)this.httpServletRequest.getSession().getAttribute(telephone);
        if (!StringUtils.equals(serverOtpCode,otpCode)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"短信验证码不符合") ;
        }
        //用户的注册流程
        UserModel model = new UserModel() ;
        model.setTelephone(telephone);
        model.setName(name);
        model.setGender(gender);
        model.setAge(age);
        model.setRegisterMode("byphone");
        model.setEncrptPassword(encodeByMd5(password));
        userInfoService.register(model);
        return CommonReturnType.success(null) ;
    }


    private String encodeByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance("MD5") ;
        BASE64Encoder base64Encoder = new BASE64Encoder() ;
        //加密字符串
        String newStr = base64Encoder.encode(md5.digest(str.getBytes("utf-8"))) ;
        return newStr ;
    }

    //用户获取otp短信接口
    //consumes对应的后端提供前端的contentType需要消费的一个名字
    @GetMapping(value = "/getOtp",consumes = {"application/x-www-form-urlencoded"})
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
