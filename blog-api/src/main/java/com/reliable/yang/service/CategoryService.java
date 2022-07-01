package com.reliable.yang.service;

import com.reliable.yang.vo.CategoryVo;
import com.reliable.yang.vo.Result;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @BelongsProject: blog-parent
 * @BelongsPackage: com.reliable.yang.service
 * @Author: Administrator
 * @CreateTime: 2022-06-22 19:30
 * @Description: 文章详情页 分类
 */
public interface CategoryService {
	CategoryVo findCategoryById(Long categoryId);
	// 写文章模块 获取用户输入的所有分类并显示在编辑页面
	public Result findAll();
}
