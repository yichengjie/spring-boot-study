package com.yicj.study.controller;

import com.yicj.study.model.UserModel;
import com.yicj.study.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("user")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserInfoService userInfoService ;

    @GetMapping("/getUser")
    public UserModel getUser(@RequestParam("id") Integer id){
        UserModel userModel = userInfoService.getUserById(id);
        return userModel;
    }
}
