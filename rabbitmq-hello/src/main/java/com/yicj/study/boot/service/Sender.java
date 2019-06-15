package com.yicj.study.boot.service;

import java.util.Date;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Sender {
	@Autowired
	private AmqpTemplate rabbitTemplate ;
	private String queuesName = "hello" ;
	
	public void send() {
		String context = "hello " + new Date() ;
		log.info("=============> Sender : " + context);
		this.rabbitTemplate.convertAndSend(queuesName, context);
	}
}
