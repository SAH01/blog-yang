package com.reliable.yang.handler;

import com.alibaba.fastjson.JSON;
import com.reliable.yang.dao.pojo.SysUser;
import com.reliable.yang.service.LoginService;
import com.reliable.yang.utils.UserThreadLocal;
import com.reliable.yang.vo.ErrorCode;
import com.reliable.yang.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 * @date 2022-06-21 22:01
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
	@Autowired
	private LoginService loginService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		if (!(handler instanceof HandlerMethod)){
			return true;
		}
		String token = request.getHeader("Authorization");
		log.info("=================request start===========================");
		String requestURI = request.getRequestURI();
		log.info("request uri:{}",requestURI);
		log.info("request method:{}",request.getMethod());
		log.info("token:{}", token);
		log.info("=================request end===========================");

		if (token == null){
			Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print(JSON.toJSONString(result));
			return false;
		}

		SysUser sysUser = loginService.checkToken(token);
		if (sysUser == null){
			Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print(JSON.toJSONString(result));
			return false;
		}

		// 我希望在controller中直接获取用户信息 使用ThreadLocal保存用户信息
		UserThreadLocal.put(sysUser);
		// 是登录状态则放行
		return true;
	}

	/**
	 * 用完的userLocal信息需要删除 防止内存泄露
	 * @param request
	 * @param response
	 * @param handler
	 * @param ex
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		UserThreadLocal.remove();
	}
}