package com.yicj.mybatis.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.annotations.Api;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2//启用Swagger2
@ConfigurationProperties(prefix = "swagger2")
public class Swagger2Config {
	
	private boolean enable = false ;
	
	 @Bean
	 public Docket createRestApi() {
	     return new Docket(DocumentationType.SWAGGER_2)
	         .apiInfo(apiInfo())
	         .select()
	         .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
	         .paths(PathSelectors.any())
	         .build()
	         .apiInfo(apiInfo())
	         .enable(enable);
	 }
	

	 private ApiInfo apiInfo() {
	     return new ApiInfoBuilder()
	             .title("测试接口文档")
	             .description("更多详细信息请联系具体开发人员")
	             .termsOfServiceUrl("https://com.yicj.study")
	             .contact(new Contact("yicj", "", "626659321@qq.com")) 
	             .version("1.0")
	             .build();
	 }

	 public boolean isEnable() {
		return enable;
	 }
	 public void setEnable(boolean enable) {
		this.enable = enable;
	 }
}
