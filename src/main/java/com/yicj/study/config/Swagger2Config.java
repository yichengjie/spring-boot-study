package com.yicj.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
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
	             //.apis(RequestHandlerSelectors.basePackage("com.yicj.study.controller"))
	             .apis(Swagger2Config.basePackage("com.yicj.study.controller,com.yicj.study.domain"))
	             .paths(PathSelectors.any())
	             .build();
	 }
	 
	 /**
     * Predicate that matches RequestHandler with given base package name for the class of the handler method.
     * This predicate includes all request handlers matching the provided basePackage
     * @param basePackage - base package of the classes
     * @return this
     */
     public static Predicate<RequestHandler> basePackage(final String basePackage) {
        return new Predicate<RequestHandler>() {
            @Override
            public boolean apply(RequestHandler input) {
                return declaringClass(input).transform(handlerPackage(basePackage)).or(true);
            }
        };
     }
     
     /**
      * @param input RequestHandler
      * @return Optional
      */
     private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
         return Optional.fromNullable(input.declaringClass());
     }


	 /**
	     * 处理包路径配置规则,支持多路径扫描匹配以逗号隔开
	     * @param basePackage 扫描包路径
	     * @return Function
	     */
     private static Function<Class<?>, Boolean> handlerPackage(final String basePackage) {
        return new Function<Class<?>, Boolean>() {
            @Override
            public Boolean apply(Class<?> input) {
                for (String strPackage : basePackage.split(",")) {
                    boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                    if (isMatch) {
                        return true;
                    }
                }
                return false;
            }
        };
     }

	 private ApiInfo apiInfo() {
	     return new ApiInfoBuilder()
	             .title("Spring Boot中使用Swagger2构建RESTful APIs")
	             .description("更多Spring Boot相关文章请关注：http://blog.didispace.com/")
	             .termsOfServiceUrl("http://blog.didispace.com/")
	             .contact(new Contact("秦贺", "", "qinhelili@gmail.com")) 
	             .version("1.0")
	             .build();
	 }
}
