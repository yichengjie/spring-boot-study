package com.yicj.study.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/freemarker")
public class FreemarkerController {
	
	@GetMapping("/hello")
	public String hello0(){
		//ModelAndView
		return "freemarker";
	}
	
	@GetMapping("/hello1")
	public String hello1(Map<String, Object> map){
		//ModelAndView
		map.put("name", "Cay");
		return "freemarker";
	}
	
	@GetMapping("/hello2")
	public ModelAndView hello2(){
		ModelAndView mv = new ModelAndView();
		mv.addObject("name", "Amy");
		mv.setViewName("freemarker");
		return mv;
	}
}
