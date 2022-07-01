package com.reliable.yang.utils;

import com.reliable.yang.dao.pojo.SysUser;

/**
 * @author Administrator
 * @date 2022-06-22 18:57
 * ThreadLocal 本地存储用户信息
 */

public class UserThreadLocal {

	private UserThreadLocal(){}

	private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();
	// 存
	public static void put(SysUser sysUser){
		LOCAL.set(sysUser);
	}
	// 取
	public static SysUser get(){
		return LOCAL.get();
	}
	// 删除
	public static void remove(){
		LOCAL.remove();
	}
}