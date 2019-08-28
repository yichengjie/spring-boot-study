package com.yicj.mybatis.mapper;


import java.util.Date;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.yicj.mybatis.entity.User;

//目标：测试一下spring的bean的某些功能
@RunWith(SpringJUnit4ClassRunner.class) // junit整合spring的测试//立马开启了spring的注解
@ContextConfiguration(locations = "classpath:spring/applicationContext-*.xml") // 加载核心配置文件，自动构建spring容器
public class UserMapperSpringTest {
	@Autowired
	private UserMapper userMapper ;

	@Test
	public void testQueryUserByTableName() {
		List<User> userList = this.userMapper.queryUserByTableName("tb_user");
		userList.forEach(user->System.out.println(user));
	}
	
	@Test
	public void testLogin() {
		User user = this.userMapper.login("hj", "123456");
		System.out.println(user);
	}
	
	@Test
	public void testQueryUserById() {
		User user = this.userMapper.queryUserById("2") ;
		System.out.println(user);
	}
	
	@Test
	public void testQueryUserAll() {
		List<User> userList = this.userMapper.queryUserAll();
		userList.forEach(user -> System.out.println(user));
	}
	
	@Test
	public void testInsertUser() {
		User user = new User() ;
		user.setAge(20);
		user.setBirthday(new Date());
		user.setName("大神");
		user.setPassword("123456");
		user.setSex(2);
		user.setUserName("bigGod222");
		this.userMapper.insertUser(user);
		System.out.println(user.getId());
	}
	
	@Test
	public void testUpdateUser() {
		User user = new User() ;
		user.setBirthday(new Date());
		user.setName("静静");
		user.setPassword("123456");
		user.setSex(0);
		user.setUserName("Jinjin");
		user.setId("1");
		this.userMapper.updateUser(user);
	}
	
	@Test
	public void testDeleteUserById() {
		this.userMapper.deleteUserById("1");
	}
	
	@Test
	public void testQueryUserListByName1() {
		List<User> users = this.userMapper.queryUserListByName1("hj") ;
		users.forEach(user->System.out.println(user));
	}
	
	@Test
	public void testQueryUserListByNameOrAge() {
		List<User> users = this.userMapper.queryUserListByNameOrAge(null, 22);
		users.stream().forEach(user->System.out.println(user));
	}
	
	@Test
	public void testQueryUserListByNameAndAge() {
		List<User> users = this.userMapper.queryUserListByNameAndAge("鹏程", 22);
		users.forEach(user->System.out.println(user));
	}
	
	@Test
	public void testQueryUserListByIds() {
		List<User> users = this.userMapper.queryUserListByIds(new String []{"1","2"});
		users.forEach(user-> System.out.println(user));
	}
}
