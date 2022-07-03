package com.reliable.yang.controller;

import com.reliable.yang.service.SysUserService;
import com.reliable.yang.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 返回当前登录用户 token
 * 因为登陆之后 需要跳转页面 跳转的时候是通过验证token进行的
 * @author Administrator
 * @date 2022-06-21 21:24
 */
@RestController
@RequestMapping("users")
public class UserController {

	@Autowired
	private SysUserService sysUserService;

	@GetMapping("currentUser")
	public Result currentUser(@RequestHeader("Authorization") String token){
		return sysUserService.getUserInfoByToken(token);
	}
}