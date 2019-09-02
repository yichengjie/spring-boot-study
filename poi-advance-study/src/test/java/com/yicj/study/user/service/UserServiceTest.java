package com.yicj.study.user.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.yicj.study.MpApplication;
import com.yicj.study.entity.User;
import com.yicj.study.service.IUserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=MpApplication.class)
public class UserServiceTest {
	@Autowired
	private IUserService userService ;
	
	@Test
	public void testQueryAll() {
		List<User> list = this.userService.list();
		list.forEach(System.out::println);
	}
	
}
