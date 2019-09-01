package com.yicj.study.user.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.yicj.study.MpApplication;
import com.yicj.study.user.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=MpApplication.class)
public class UserServiceTest {
	@Autowired
	private IUserService userService ;
	
	@Test
	public void testSelectByPage() {
		PageHelper.startPage(1, 3);
		List<User> users = userService.list();
		users.forEach(System.out::println);
	}
	
	//名字中包含雨，且年龄小于40
	@Test
	public void selectByWrapper() {
		QueryWrapper<User> query = Wrappers.query();
		query.like("name", "雨") ;
		query.lt("age", 40) ;
		List<User> list = userService.list(query);
		list.forEach(System.out::println);
	}
	
}
