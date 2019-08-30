package com.yicj.study.user.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.yicj.study.MpApplication;
import com.yicj.study.user.entity.User;
import com.yicj.study.user.service.IUserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MpApplication.class)
public class UserMapperSpringTest {
	
	@Autowired
	private UserMapper userMapper ;
	
	@Test
	public void testSelectAllUser() {
		List<User> users = this.userMapper.selectAllUser();
		users.forEach(System.out::println);
	}
	
	@Test
	public void testSelectList() {
		List<User> users = this.userMapper.selectList(null) ;
		users.forEach(System.out::println);
	}

}
