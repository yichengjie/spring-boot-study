package com.yicj.study.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yicj.study.domain.User;

@RestController
public class UserController {
	 /**
     * 定义一个接口，接受两个参数
     * @param id
     * @param name
     * @return
     */
    @RequestMapping("findUser")
    public User findUser(int id, String name){
        User user = new User();
        user.setName(name);
        user.setId(id);
        return user;
    }
    
    @RequestMapping("/hello")
    public String hello() {
    	
    	return "hello" ;
    }
}
