package com.yicj.study.web.controller;

import com.yicj.study.common.Init;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Init("test")
@Controller
public class HelloController {
	Logger logger = LoggerFactory.getLogger(HelloController.class) ;

	@Init("test-home")
	@GetMapping("/")
	@ResponseBody
	String home() {
		logger.info("hello world"); 
		return "Hello World" ;
	}

	@GetMapping("/websocket/{name}")
	public String webSocket(@PathVariable String name, Model model) {
		try {
			logger.info("跳转到websocket的页面上");
			model.addAttribute("username", name);
			return "websocket";
		} catch (Exception e) {
			logger.info("跳转到websocket的页面上发生异常，异常信息是：" + e.getMessage());
			return "error";
		}
	}
}
