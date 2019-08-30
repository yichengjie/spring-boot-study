1. 注意pom加入mybatis-plus-boot-starter后就不要加入mybatis-spring-boot-starter否则会报错

2. mp这整合springboot
   2.1 application.yml增加配置
	   mybatis-plus: 
		  mapper-locations: classpath*:mappers/*/*Mapper.xml
		  type-aliases-package: com.yicj.study.*.entity
		  configuration:
		    map-underscore-to-camel-case: true
		    cache-enabled: false
   2.2 增加注解 @MapperScan("com.yicj.study.*.mapper")
3.使用mp自带分页组件
  3.1 编写MybatisPlusConfig配置
  3.2 使用的地方加上 代码
       //参数1：第几页
       //参数2：每页几条记录
	   IPage<User> retPage = userService.page(new Page<User>(1, 2)); 

		  
