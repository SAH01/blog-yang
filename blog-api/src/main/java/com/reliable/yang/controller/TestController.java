package com.reliable.yang.controller;

import com.reliable.yang.dao.pojo.SysUser;
import com.reliable.yang.utils.UserThreadLocal;
import com.reliable.yang.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试登录拦截器
 * @author Administrator
 * @date 2022-06-21 22:14
 */
@RestController
@RequestMapping("test")
public class TestController {

	@RequestMapping
	public Result test(){
		SysUser sysUser = UserThreadLocal.get();
		System.out.println("\n登录的用户信息："+sysUser.toString());
		return Result.success(null);
	}
}