package com.yicj.study;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SpringBootStudyApplication {
	static Logger logger = LoggerFactory.getLogger(SpringBootStudyApplication.class) ;
	public static void main(String[] args) {
		logger.info("----ready to start spring boot -------");
		SpringApplication.run(SpringBootStudyApplication.class, args);
	}

}
