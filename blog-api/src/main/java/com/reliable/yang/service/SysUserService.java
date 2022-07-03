package com.reliable.yang.service;

import com.reliable.yang.dao.pojo.SysUser;
import com.reliable.yang.vo.Result;
import com.reliable.yang.vo.UserVo;

/**
 * @BelongsProject: blog-parent
 * @BelongsPackage: com.reliable.yang.service
 * @Author: Administrator
 * @CreateTime: 2022-06-21 17:59
 * @Description: 作者
 */
public interface SysUserService {
	//【返回作者昵称（首页文章列表展示）】
	SysUser findSysUserById(Long userId);
	//【登录】 登录时，在数据库查找是否有对应用户
	SysUser findUser(String account, String pwd);
	//【登录（注册）成功之后会返回浏览器一个token，浏览器会调用接口查看该token对应的用户信息是否存在】
	// 该方法用于返回由token（redis）查询到的用户信息
	Result getUserInfoByToken(String token);
	//【注册】 判断正在注册的用户名是否已经存在
	SysUser findUserByAccount(String account);
	//【注册】 保存用户信息
	void save(SysUser sysUser);
	//查询用户信息
	UserVo findUserVoById(Long id);
}

