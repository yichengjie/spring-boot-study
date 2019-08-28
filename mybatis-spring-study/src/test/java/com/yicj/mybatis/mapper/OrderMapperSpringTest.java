package com.yicj.mybatis.mapper;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.yicj.mybatis.entity.Order;

//目标：测试一下spring的bean的某些功能
@RunWith(SpringJUnit4ClassRunner.class) // junit整合spring的测试//立马开启了spring的注解
@ContextConfiguration(locations = "classpath:spring/applicationContext-*.xml") // 加载核心配置文件，自动构建spring容器
public class OrderMapperSpringTest {
	@Autowired
	private OrderMapper orderMapper ;

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
