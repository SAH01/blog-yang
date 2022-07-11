package com.reliable.yang.admin.service;

import com.reliable.yang.admin.pojo.Admin;
import com.reliable.yang.admin.pojo.Permission;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Administrator
 * @date 2022-07-04 22:06
 */
@Service
@Slf4j
public class AuthService {

	@Autowired
	private AdminService adminService;

	public boolean auth(HttpServletRequest request, Authentication authentication){
		String requestURI = request.getRequestURI();
		log.info("request url:{}", requestURI);
		//true代表放行 false 代表拦截
		Object principal = authentication.getPrincipal();
		if (principal == null || "anonymousUser".equals(principal)){
			//未登录
			log.info("未登录！");
			return false;
		}
		UserDetails userDetails = (UserDetails) principal;
		String username = userDetails.getUsername();
		Admin admin = adminService.findAdminByUserName(username);
		if (admin == null){
			log.info("用户名不存在！");
			return false;
		}
		if (admin.getId() == 1){
			//认为是超级管理员
			return true;
		}
		List<Permission> permissions = adminService.findPermissionsByAdminId(admin.getId());
		requestURI = StringUtils.split(requestURI,'?')[0];
		for (Permission permission : permissions) {
			if (requestURI.equals(permission.getPath())){
				log.info("权限通过");
				return true;
			}
		}
		return false;
	}
}