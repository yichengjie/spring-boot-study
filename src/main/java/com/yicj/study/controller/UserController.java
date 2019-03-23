package com.yicj.study.controller;

import javax.servlet.http.HttpSession;
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
    
    @RequestMapping("/login")
    public String login(String name ,String pwd,HttpSession session) {
    	if("yicj".equals(name) && "123".equals(pwd)) {
    		session.setAttribute("user", name);
    		return "登录成功" ;
    	}else {
    		return "用户名或密码错误" ;
    	}
    }
}
