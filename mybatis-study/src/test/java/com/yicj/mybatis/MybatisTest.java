package com.yicj.mybatis;

import java.io.IOException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import com.yicj.mybatis.common.MybatisUtils;
import com.yicj.mybatis.entity.User;

//https://blog.csdn.net/hellozpc/article/details/80878563
public class MybatisTest {
	
	@Test
	public void testHello() throws IOException {
		String resource = "mybatis-config.xml" ;
		SqlSessionFactory factory = MybatisUtils.createSqlSessionFactory(resource);
		SqlSession sqlSession = factory.openSession();
		try {
			User user = sqlSession.selectOne("MyMapper.selectUser",100) ;
			System.out.println(user);
		} finally {
			sqlSession.close();
		}
	}
	
}
