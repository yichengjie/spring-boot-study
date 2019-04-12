package com.yicj.study.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

//现在要返回的是一个页面，所以不能再用@RestController，
//而用普通的@Controller
@Controller
@RequestMapping("/thymeleaf")
public class ThymeleafController {
	
	@GetMapping("hello")
	public String ceshi(HttpServletRequest req) {
		req.setAttribute("name", "hello world");
		return "thymeleaf";  
	}
	
	@GetMapping("/hello1")
	public String hello1(Map<String, Object> map){
		//ModelAndView
		map.put("name", "Cay");
		return "thymeleaf";
	}
	
	@RequestMapping("/hello2")
	public ModelAndView hello2(){
		ModelAndView mv = new ModelAndView();
		mv.addObject("name", "Amy");
		mv.setViewName("thymeleaf");
		return mv;
	}
}
