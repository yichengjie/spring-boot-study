package com.yicj.study.user.service;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yicj.study.MpApplication;
import com.yicj.study.user.entity.User;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=MpApplication.class)
@Slf4j
public class UserServiceTest {
	@Autowired
	private IUserService userService ;
	
	@Test
	public void testSelectOne() {
		QueryWrapper<User> wrapper = new QueryWrapper<User>() ;
		wrapper.eq("id", "1094590409767661570");
		User user = this.userService.getOne(wrapper);
		log.info(user.toString());
	}
	
	@Test
	public void testSelectMaps() {
		QueryWrapper<User> wrapper = new QueryWrapper<User>() ;
		wrapper.isNotNull("name") ;
		List<Map<String, Object>> listMaps = this.userService.listMaps(wrapper);
		listMaps.forEach(System.out::println);
	}
	
	@Test
	public void testSelectByPage() {
		Page<User> page = new Page<User>(1, 2) ;
		IPage<User> retPage = userService.page(page);
		List<User> records = retPage.getRecords();
		records.forEach(System.out::println);
		long pageCount = retPage.getPages();
		System.out.println("====> pageCount : " + pageCount);
		long total = retPage.getTotal();
		System.out.println("====> total : " + total);
	}
	
	
	
	@Test
	public void testUpdateById() {
		User user = new User() ;
		user.setId(1094590409767661570L) ;
		user.setAge(36) ;
		user.setVersion(0) ;
		this.userService.updateById(user) ;
	}
	
	@Test
	public void testUpdate() {
		//修改内容
		User user = new User() ;
		user.setAge(38) ;
		user.setVersion(2) ;
		//修改添加
		UpdateWrapper<User> wrappr = new UpdateWrapper<>() ;
		wrappr.eq("id", 1094590409767661570L) ;
		this.userService.update(user, wrappr) ;
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
