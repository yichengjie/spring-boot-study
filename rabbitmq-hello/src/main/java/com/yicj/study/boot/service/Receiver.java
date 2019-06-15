package com.yicj.study.boot.service;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@RabbitListener(queues = "hello")
@Slf4j
public class Receiver {
	
	@RabbitHandler
	public void process(String hello) {
		log.info("================> Receiver: " + hello);
	}
	
}
