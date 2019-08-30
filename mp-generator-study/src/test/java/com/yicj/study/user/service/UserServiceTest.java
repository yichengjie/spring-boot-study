package com.yicj.study.user.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yicj.study.MpApplication;
import com.yicj.study.user.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=MpApplication.class)
public class UserServiceTest {
	@Autowired
	private IUserService userService ;
	
	@Test
	public void testSelectByPage() {
		IPage<User> page = new Page<User>(1, 2) ;
		IPage<User> retPage = userService.page(page);
		List<User> records = retPage.getRecords();
		records.forEach(System.out::println);
		long pageCount = retPage.getPages();
		System.out.println("====> pageCount : " + pageCount);
		long total = retPage.getTotal();
		System.out.println("====> total : " + total);
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
