package com.yicj.mybatis.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.yicj.study.MpApplication;
import com.yicj.study.entity.User;
import com.yicj.study.mapper.UserMapper;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = MpApplication.class)
public class UserMapperTest {
	
	@Autowired
	private UserMapper userMapper ;
	
	@Test
	public void testSelectAll2() {
		List<User> users = this.userMapper.selectList(null);
		users.forEach(System.out::println);
	}
	
	@Test
	public void testSelectUserAll() {
		List<User> users = this.userMapper.selectUserAll();
		users.forEach(user-> System.out.println(user));
	}
}
