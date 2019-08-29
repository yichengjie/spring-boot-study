package com.yicj.mybatis.mapper;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.yicj.mybatis.MybatisApplication;
import com.yicj.mybatis.entity.Order;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybatisApplication.class)
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
