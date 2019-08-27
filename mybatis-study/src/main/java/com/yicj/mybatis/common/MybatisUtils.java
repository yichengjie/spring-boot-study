package com.yicj.mybatis.common;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisUtils {
	
	public static SqlSessionFactory createSqlSessionFactory(String resource) throws IOException {
		InputStream inputStream = Resources.getResourceAsStream(resource) ;
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
		return factory ;
	}

}
