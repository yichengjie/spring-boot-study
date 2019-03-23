package com.yicj.study.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TemplatesController {
	
	@RequestMapping("ceshi")
	public String ceshi() {
		 return "ceshi";  
	}
}
