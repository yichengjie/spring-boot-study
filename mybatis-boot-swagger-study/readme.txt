1. 在pom.xml中增加swagger的依赖
	<dependency>
	    <groupId>io.springfox</groupId>
	    <artifactId>springfox-swagger2</artifactId>
	    <version>2.7.0</version>
	</dependency>
	
	<dependency>
	    <groupId>io.springfox</groupId>
	    <artifactId>springfox-swagger-ui</artifactId>
	    <version>2.7.0</version>
	</dependency>

2. 增加swagger的配置 Swagger2Config.java

3. 增加获取配置文件内容的依赖
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-configuration-processor</artifactId>
		<optional>true</optional>
	</dependency>

4. controller上增加注解@Api(tags="index-condition")
	