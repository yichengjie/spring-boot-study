package com.yicj.study;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Application {
	static Logger logger = LoggerFactory.getLogger(Application.class) ;
	public static void main(String[] args) {
		logger.info("----ready to start spring boot -------");
		SpringApplication.run(Application.class, args);
	}
}
