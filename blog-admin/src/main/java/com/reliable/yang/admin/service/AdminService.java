package com.reliable.yang.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.reliable.yang.admin.mapper.AdminMapper;
import com.reliable.yang.admin.mapper.PermissionMapper;
import com.reliable.yang.admin.pojo.Admin;
import com.reliable.yang.admin.pojo.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 * @date 2022-07-04 22:06
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class AdminService {

	@Autowired
	private AdminMapper adminMapper;
	@Autowired
	private PermissionMapper permissionMapper;

	public Admin findAdminByUserName(String username){
		LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(Admin::getUsername,username).last("limit 1");
		Admin adminUser = adminMapper.selectOne(queryWrapper);
		return adminUser;
	}

	public List<Permission> findPermissionsByAdminId(Long adminId){
		return permissionMapper.findPermissionsByAdminId(adminId);
	}

}