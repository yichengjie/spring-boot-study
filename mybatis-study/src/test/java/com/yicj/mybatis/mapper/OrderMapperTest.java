package com.yicj.mybatis.mapper;

import java.io.IOException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import com.yicj.mybatis.common.MybatisUtils;
import com.yicj.mybatis.entity.Order;

public class OrderMapperTest {
	
	private OrderMapper orderMapper ;
	
	
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
		this.orderMapper = sqlSession.getMapper(OrderMapper.class) ;
	}
	
	@Test
	public void testQueryOrderWithUserByOrderNumber() {
		Order order = orderMapper.queryOrderWithUserByOrderNumber("201807010001");
		System.out.println(order);
	}
	
	@Test
	public void testQueryOrderWithUserAndDetailByOrderNumber() {
		Order order = this.orderMapper.queryOrderWithUserAndDetailByOrderNumber("201807010001");
		order.getDetailList().forEach(detail -> System.out.println(detail));
		//System.out.println(order.getDetailList().size());
	}
	
	@Test
	public void testQueryOrderWithUserAndDetailItemByOrderNumber() {
		Order order = this.orderMapper.queryOrderWithUserAndDetailItemByOrderNumber("201807010001") ;
		order.getDetailList().forEach(detail -> System.out.println(detail));
	}
}
