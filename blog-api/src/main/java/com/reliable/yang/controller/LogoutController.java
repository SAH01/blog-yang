package com.reliable.yang.controller;

import com.reliable.yang.service.LoginService;
import com.reliable.yang.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 退出登录
 * @author Administrator
 * @date 2022-06-21 21:38
 */
@RestController
@RequestMapping("logout")
public class LogoutController {

	@Autowired
	private LoginService loginService;

	@GetMapping
	public Result logout(@RequestHeader("Authorization") String token){
		return loginService.logout(token);
	}
}