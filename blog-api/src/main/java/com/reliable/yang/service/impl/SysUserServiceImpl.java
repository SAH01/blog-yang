package com.reliable.yang.service.impl;

import com.reliable.yang.vo.UserVo;
import org.springframework.data.redis.core.RedisTemplate;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.reliable.yang.dao.mapper.SysUserMapper;
import com.reliable.yang.dao.pojo.SysUser;
import com.reliable.yang.service.SysUserService;
import com.reliable.yang.utils.JWTUtils;
import com.reliable.yang.vo.ErrorCode;
import com.reliable.yang.vo.LoginUserVo;
import com.reliable.yang.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Administrator
 * @date 2022-06-21 17:59
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class SysUserServiceImpl implements SysUserService {
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	/**
	 * 首页文章列表 需要的作者 是空要默认赋值
	 * @param userId
	 * @return
	 */
	@Override
	public SysUser findSysUserById(Long userId) {
		SysUser sysUser = sysUserMapper.selectById(userId);
		if (sysUser == null) {
			sysUser = new SysUser();
			sysUser.setNickname("靠谱杨");
		}
		return sysUser;
	}

	/**
	 * 首次登陆
	 * @param account
	 * @param pwd
	 * @return
	 */
	@Override
	public SysUser findUser(String account, String pwd) {
		LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SysUser::getAccount,account);
		queryWrapper.eq(SysUser::getPassword,pwd);
		queryWrapper.select(SysUser::getId,SysUser::getAccount,SysUser::getAvatar,SysUser::getNickname);
		queryWrapper.last("limit 1");
		SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
		return sysUser;
	}

	/**
	 * 登录后跳转页面验证token
	 * @param token
	 * @return
	 */
	@Override
	public Result getUserInfoByToken(String token) {
		/**
		 * 1. token合法性校验（是否为空，解析是否成功，redis是否存在）
		 * 2. 失败-》返回错误信息
		 * 3. 成功-》返回结果 (LoginUserVo)
		 */
		Map<String, Object> map = JWTUtils.checkToken(token);
		if (map == null){
			return Result.fail(ErrorCode.NO_LOGIN.getCode(),ErrorCode.NO_LOGIN.getMsg());
		}
		String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
		if (StringUtils.isBlank(userJson)){
			return Result.fail(ErrorCode.NO_LOGIN.getCode(),ErrorCode.NO_LOGIN.getMsg());
		}
		SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
		LoginUserVo loginUserVo = new LoginUserVo();
		loginUserVo.setAccount(sysUser.getAccount());
		loginUserVo.setAvatar(sysUser.getAvatar());
		loginUserVo.setId(String.valueOf(sysUser.getId()));
		loginUserVo.setNickname(sysUser.getNickname());
		return Result.success(loginUserVo);
	}

	/**
	 * 注册时判断当前用户是否已经存在
	 * @param account
	 * @return
	 */
	@Override
	public SysUser findUserByAccount(String account) {
		LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SysUser::getAccount,account);
		queryWrapper.last("limit 1");
		return sysUserMapper.selectOne(queryWrapper);
	}

	/**
	 * 保存用户信息
	 * @param sysUser
	 */
	@Override
	public void save(SysUser sysUser) {
		//注意 默认生成的id 是分布式id 采用了雪花算法
		this.sysUserMapper.insert(sysUser);
	}

	/**
	 * 查询作者信息 （评论）
	 * @param id
	 * @return
	 */
	@Override
	public UserVo findUserVoById(Long id) {
		SysUser sysUser = sysUserMapper.selectById(id);
		if (sysUser == null){
			sysUser = new SysUser();
			sysUser.setId(123L);
			sysUser.setAvatar("/static/img/logo.b3a48c0.png");
			sysUser.setNickname("码神之路");
		}
		UserVo userVo = new UserVo();
		userVo.setAvatar(sysUser.getAvatar());
		userVo.setNickname(sysUser.getNickname());
		userVo.setId(String.valueOf(sysUser.getId()));
		return userVo;
	}
}