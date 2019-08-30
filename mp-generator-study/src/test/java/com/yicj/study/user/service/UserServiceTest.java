package com.yicj.study.user.service;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.pagehelper.PageHelper;
import com.yicj.study.MpApplication;
import com.yicj.study.user.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MpApplication.class)
public class UserServiceTest {
	
	@Autowired
	private IUserService userService ;
	
	@Test
	public void testSelectList() {
		List<User> users = this.userService.list() ;
		users.forEach(System.out::println);
	}
	
	@Test
	public void testSelectUserPage() {
		PageHelper.startPage(1, 2);
		List<User> users = this.userService.list() ;
		users.forEach(System.out::println);
	}

}
