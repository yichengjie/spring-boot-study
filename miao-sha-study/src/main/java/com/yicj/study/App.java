package com.yicj.study;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages = {"com.yicj.study"})
@MapperScan("com.yicj.study.dao")
@RestController
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class,args) ;
    }

}
