package com.yicj.study.user.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.yicj.study.MpApplication;
import com.yicj.study.user.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=MpApplication.class)
public class UserServiceTest {
	@Autowired
	private IUserService userService ;
	
	@Test
	public void testDeleteAll() {
		this.userService.deleteAll(); 
	}
	
	@Test
	public void testInsertBatchSomeColumn() {
		User user1 = new User() ;
		user1.setId(1087982257332887554L) ;
		user1.setName("李四") ;
		user1.setAge(22) ;
		user1.setEmail("zhangsan@qq.com") ;
		user1.setCreateTime(LocalDateTime.now()) ;
		
		User user2 = new User() ;
		user2.setId(1087982257332887555L) ;
		user2.setName("张三") ;
		user2.setAge(25) ;
		user2.setEmail("lisi@qq.com") ;
		user2.setCreateTime(LocalDateTime.now()) ;
		List<User> list = Arrays.asList(user1,user2);
		this.userService.insertBatchSomeColumn(list) ;
	}
	
}
