package com.reliable.yang.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.reliable.yang.dao.pojo.SysUser;
import com.reliable.yang.service.LoginService;
import com.reliable.yang.service.SysUserService;
import com.reliable.yang.utils.JWTUtils;
import com.reliable.yang.vo.ErrorCode;
import com.reliable.yang.vo.Result;
import com.reliable.yang.vo.params.LoginParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 * @date 2022-06-21 21:03
 */
@Service
@Transactional
public class LoginServiceImpl implements LoginService {
	private static final String slat = "mszlu!@###";
	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Override
	public Result login(LoginParam loginParam) {
		/**
		 * 1. 检查参数合法性
		 * 2. 根据用户名和密码验证是否存在
		 * 3. 不存在则失败
		 * 4. 存在则生成token返回到前端
		 * 5. 把token放到redis中 token：user信息 设置过期时间（先认证token是否合法，然后直接去redis验证）
		 */

		String account = loginParam.getAccount();
		String password = loginParam.getPassword();
		if (StringUtils.isBlank(account) || StringUtils.isBlank(password)){
			return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
		}
		String pwd = DigestUtils.md5Hex(password + slat);   //md5加密
		SysUser sysUser = sysUserService.findUser(account,pwd);
		if (sysUser == null){
			return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
		}
		//登录成功，使用JWT生成token，返回token和redis中
		String token = JWTUtils.createToken(sysUser.getId());
		redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
		return Result.success(token);
	}

	@Override
	public Result logout(String token) {
		redisTemplate.delete("TOKEN_"+token);
		return Result.success(null);
	}

	/**
	 * 注册
	 * @param loginParam
	 * @return
	 */
	@Override
	public Result register(LoginParam loginParam) {
		String account = loginParam.getAccount();
		String password = loginParam.getPassword();
		String nickname = loginParam.getNickname();
		if (StringUtils.isBlank(account)
				|| StringUtils.isBlank(password)
				|| StringUtils.isBlank(nickname)
		){
			return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
		}
		SysUser sysUser = this.sysUserService.findUserByAccount(account);
		if (sysUser != null){
			return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(),ErrorCode.ACCOUNT_EXIST.getMsg());
		}
		sysUser = new SysUser();
		sysUser.setNickname(nickname);
		sysUser.setAccount(account);
		sysUser.setPassword(DigestUtils.md5Hex(password+slat));
		sysUser.setCreateDate(System.currentTimeMillis());
		sysUser.setLastLogin(System.currentTimeMillis());
		sysUser.setAvatar("/static/img/logo.b3a48c0.png");
		sysUser.setAdmin(1); //1 为true
		sysUser.setDeleted(0); // 0 为false
		sysUser.setSalt("");
		sysUser.setStatus("");
		sysUser.setEmail("");
		this.sysUserService.save(sysUser);

		//token
		String token = JWTUtils.createToken(sysUser.getId());

		redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
		return Result.success(token);
	}

	/**
	 * 判断当前token是否存在 【登录】
	 * @param token
	 * @return
	 */
	@Override
	public SysUser checkToken(String token) {
		if (StringUtils.isBlank(token)){
			return  null;
		}
		Map<String,Object> stringObjectMap = JWTUtils.checkToken(token);
		if(stringObjectMap == null){
			return  null;
		}

		String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);

		if(StringUtils.isBlank(userJson)){
			return null;
		}
		SysUser sysUser = JSON.parseObject(userJson,SysUser.class);

		return sysUser;
	}
}