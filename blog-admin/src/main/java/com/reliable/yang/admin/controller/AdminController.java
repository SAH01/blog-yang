package com.reliable.yang.admin.controller;

import com.reliable.yang.admin.model.params.PageParam;
import com.reliable.yang.admin.pojo.Permission;
import com.reliable.yang.admin.service.PermissionService;
import com.reliable.yang.admin.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 * @date 2022-07-04 22:01
 */
@RestController
@RequestMapping("admin")
public class AdminController {

	@Autowired
	private PermissionService permissionService;

	@PostMapping("permission/permissionList")
	public Result permissionList(@RequestBody PageParam pageParam){
		return permissionService.listPermission(pageParam);
	}

	@PostMapping("permission/add")
	public Result add(@RequestBody Permission permission){
		return permissionService.add(permission);
	}

	@PostMapping("permission/update")
	public Result update(@RequestBody Permission permission){
		return permissionService.update(permission);
	}

	@GetMapping("permission/delete/{id}")
	public Result delete(@PathVariable("id") Long id){
		return permissionService.delete(id);
	}
}