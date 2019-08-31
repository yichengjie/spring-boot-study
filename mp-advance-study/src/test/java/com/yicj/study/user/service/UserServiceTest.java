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
	
	@Test
	public void testQuery1() {
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		wrapper.lambda().eq(User::getId, 1088250446457389058L) ;
		User user = this.userService.getOne(wrapper);
		System.out.println(user);
	}
	
	@Test
	public void testQuery2() {
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		wrapper.lambda().and(w -> w.like(User::getName, "雨").or(o -> o.lt(User::getAge, 50) )) ;
		List<User> users = this.userService.list(wrapper);
		users.forEach(System.out::println);
	}
	
	@Test
	public void testQuery3() {
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		wrapper.lambda().like(User::getName, "雨").lt(User::getAge, 50) ;
		List<User> users = this.userService.list(wrapper);
		users.forEach(System.out::println);
	}
	
	@Test
	public void testQuery4() {
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		wrapper.lambda().like(User::getName, "雨").or().lt(User::getAge, 50) ;
		List<User> users = this.userService.list(wrapper);
		users.forEach(System.out::println);
	}
	
	@Test
	public void testListByMyWrapper() {
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		wrapper.lambda().like(User::getName, "雨") ;
		List<User> users = this.userService.listByMyWrapper(wrapper);
		users.forEach(u->log.info(u.toString()));
	}
	
	@Test
	public void testListByMyWrapper2() {
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		wrapper.lambda().gt(User::getAge, 20).eq(User::getDeleted, 0) ;
		List<User> users = this.userService.listByMyWrapper(wrapper);
		users.forEach(u->log.info(u.toString()));
	}
	
	@Test
	public void testLogicDeleteById() {
		this.userService.removeById(1087982257332887555L) ;
	}
	
	//测试自动填充当前时间
	@Test
	public void testInsert() {
		User user = new User() ;
		user.setName("王五") ;
		user.setAge(25) ;
		user.setEmail("wangwu@qq.com") ;
		user.setManagerId(1087982257332887554L) ;
		boolean flag = this.userService.save(user);
		System.out.println("save success flag is : " + flag);
	}
	
	@Test
	public void testUpdateById2() {
		User user = new User() ;
		user.setName("王五2") ;
		user.setId(1167810772487749634L) ;
		this.userService.updateById(user) ;
	}
	
}
