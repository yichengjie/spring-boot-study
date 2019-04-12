package com.yicj.study.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Optional;

@Configuration
@EnableSwagger2//启用Swagger2
public class Swagger2Config {
	
	 @Bean
	 public Docket createRestApi() {
	     return new Docket(DocumentationType.SWAGGER_2)
	             .apiInfo(apiInfo())
	             .select()
                 .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                 .paths(PathSelectors.any())
	             .build();
	 }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("运维工具平台接口文档")
                .description("更多详细信息请联系具体开发人员")
                .termsOfServiceUrl("https://com.travelsky.fare")
                .contact(new Contact("yicj", "", "626659321@qq.com"))
                .version("1.0")
                .build();
    }

}
