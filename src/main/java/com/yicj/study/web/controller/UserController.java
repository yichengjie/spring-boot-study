package com.yicj.study.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.*;
import com.yicj.study.domain.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/users")
@Api
public class UserController {
	static Map<Long, User> users = Collections.synchronizedMap(new HashMap<Long, User>());
    
    @ApiOperation(value="获取用户列表", notes="")
    @GetMapping
    public List<User> getUserList() {
        List<User> r = new ArrayList<User>(users.values());
        return r;
    }
    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @PostMapping
    public String postUser(@RequestBody User user) {
        users.put(user.getId(), user);
        return "success";
    }
    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @GetMapping(value="/{id}")
    public User getUser(@PathVariable Long id) {
        return users.get(id);
    }
    @ApiOperation(value="更新用户详细信息", notes="根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    })
    @PutMapping(value="/{id}")
    public String putUser(@PathVariable Long id, @RequestBody User user) {
        User u = users.get(id);
        u.setName(user.getName());
        u.setAge(user.getAge());
        users.put(id, u);
        return "success";
    }
    @ApiOperation(value="删除用户", notes="根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long id) {
        users.remove(id);
        return "success";
    }
    
    @ApiOperation(value="用户登录", notes="根据用户名密码登录系统")
    @ApiParam("电话号码，必选")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query" ,name = "name", value = "用户名", required = true, dataType = "String"),
    	@ApiImplicitParam(paramType = "query", name = "pwd", value = "密码", required = true, dataType = "String")
    })
    @GetMapping(value="/login")
    public String login(@RequestParam String name ,@RequestParam String pwd,HttpSession session) {
    	if("yicj".equals(name) && "123".equals(pwd)) {
    		session.setAttribute("user", name);
    		return "登录成功" ;
    	}else {
    		return "用户名或密码错误" ;
    	}
    }
}
