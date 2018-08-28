package com.totto.fly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
	
	@RequestMapping("/{page}")
	public String hello(@PathVariable("page") String page) {
		return page;
	}
}
