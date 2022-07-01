package com.reliable.yang.controller;

import com.reliable.yang.service.LoginService;
import com.reliable.yang.vo.Result;
import com.reliable.yang.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注册
 * @author Administrator
 * @date 2022-06-21 21:43
 */
@RestController
@RequestMapping("register")
public class RegisterController {

	@Autowired
	private LoginService loginService;

	@PostMapping

	public Result register(@RequestBody LoginParam loginParam){
		// sso 单点登录
		return loginService.register(loginParam);
	}
}