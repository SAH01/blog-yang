package com.reliable.yang.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Administrator
 * @date 2022-07-04 23:09
 */
@Controller

public class IndexController {
	@RequestMapping("/")
	public String homePage() {
		return "login.html";
	}
}