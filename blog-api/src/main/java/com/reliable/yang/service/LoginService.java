package com.reliable.yang.service;

import com.reliable.yang.dao.pojo.SysUser;
import com.reliable.yang.vo.Result;
import com.reliable.yang.vo.params.LoginParam;

/**
 * @BelongsProject: blog-parent
 * @BelongsPackage: com.reliable.yang.service
 * @Author: Administrator
 * @CreateTime: 2022-06-21 21:02
 * @Description: 登录
 */
public interface LoginService {
	/**
	 * 登录
	 * @param loginParam
	 * @return
	 */
	Result login(LoginParam loginParam);

	Result logout(String token);

	Result register(LoginParam loginParam);


	SysUser checkToken(String token);
}
