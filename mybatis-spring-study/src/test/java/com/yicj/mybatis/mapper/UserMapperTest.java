package com.yicj.mybatis.mapper;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;

import com.yicj.mybatis.common.MybatisUtils;
import com.yicj.mybatis.entity.User;

public class UserMapperTest {

	public UserMapper userMapper ;
	
	
	@Before
	public void setUp() throws IOException {
		//指定配置文件
		String resource = "mybatis-config.xml";
		//构建sqlSessionFactory
		SqlSessionFactory factory = MybatisUtils.createSqlSessionFactory(resource);
		//获取sqlSession
		SqlSession sqlSession = factory.openSession(true);
		//1、映射文件的命名空间namespace必须和mapper接口全路径
		//2、映射文件statement的id必须和mapper接口方法名保持一致
		//3、Statement的resultType必须和mapper接口方法的返回类型一致
		//4、statement的parameterType必须和mapper接口方法的参数类型一致(不一定)
		this.userMapper = sqlSession.getMapper(UserMapper.class) ;
	}
	
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
