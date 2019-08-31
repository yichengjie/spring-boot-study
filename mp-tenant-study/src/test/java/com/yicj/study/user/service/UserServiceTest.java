package com.yicj.study.user.service;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yicj.study.MpApplication;
import com.yicj.study.common.GlobalSession;
import com.yicj.study.user.entity.User;
import com.yicj.study.vo.UserVo;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=MpApplication.class)
@Slf4j
public class UserServiceTest {
	@Autowired
	private IUserService userService ;
	
	@Before
	public void init() {
		UserVo vo = new UserVo() ;
		vo.setTenantId("HN");
		GlobalSession.login(vo); 
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
	
	@Test
	public void testListByMyWrapper() {
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		wrapper.lambda().like(User::getName, "雨") ;
		List<User> users = this.userService.listByMyWrapper(wrapper);
		users.forEach(u->log.info(u.toString()));
	}
}
