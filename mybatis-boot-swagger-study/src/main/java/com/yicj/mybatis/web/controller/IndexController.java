package com.yicj.mybatis.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@Api(tags="index-condition")
public class IndexController {
	
	@GetMapping("/")
	public String index() {
		return "index" ;
	}
}
