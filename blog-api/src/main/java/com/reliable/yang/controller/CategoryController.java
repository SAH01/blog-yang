package com.reliable.yang.controller;

import com.reliable.yang.service.CategoryService;
import com.reliable.yang.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 写文章 --- 获取文章所有分类
 * @author Administrator
 * @date 2022-06-26 19:04
 */
@RestController
@RequestMapping("categorys")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	// 写文章模块 获取用户输入的所有分类并显示在编辑页面
	@GetMapping
	public Result listCategory() {
		return categoryService.findAll();
	}
	// 导航栏文章分类
	@GetMapping("detail")
	public Result CategoryDetail() {
		return categoryService.findAllDetail();
	}
	// 分类文章显示列表
	@GetMapping("detail/{id}")
	public Result categoriesDetailById(@PathVariable("id") Long id){
		return categoryService.categoriesDetailById(id);
	}
}
