package com.yicj.study.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//现在要返回的是一个页面，所以不能再用@RestController，
//而用普通的@Controller
@Controller
public class TemplatesController {
	
	@RequestMapping("ceshi")
	public String ceshi(HttpServletRequest req) {
		
		req.setAttribute("key", "hello world");
		return "ceshi";  
	}
}
