package com.reliable.yang.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.reliable.yang.admin.pojo.Permission;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * @author Administrator
 * @date 2022-07-04 22:03
 */
public interface PermissionMapper extends BaseMapper<Permission> {

	List<Permission> findPermissionsByAdminId(@Value("adminId") Long adminId);
}