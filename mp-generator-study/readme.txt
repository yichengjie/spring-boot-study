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
2.整合page-helper
  2.1 加入jar依赖 pagehelper,pagehelper-spring-boot-autoconfigure,pagehelper-spring-boot-starter
  2.2 application.yml增加配置
  	  pagehelper:
		  helper-dialect: mysql
		  reasonable: true
		  support-methods-arguments: true
		  params: countSql 
		  
