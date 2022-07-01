package com.reliable.yang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.reliable.yang.dao.mapper.CategoryMapper;
import com.reliable.yang.dao.pojo.ArticleBody;
import com.reliable.yang.dao.pojo.Category;
import com.reliable.yang.service.CategoryService;
import com.reliable.yang.vo.CategoryVo;
import com.reliable.yang.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2022-06-22 19:35
 */
@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryMapper categoryMapper;

	@Override
	public CategoryVo findCategoryById(Long categoryId){
		Category category = categoryMapper.selectById(categoryId);
		CategoryVo categoryVo = new CategoryVo();
		BeanUtils.copyProperties(category,categoryVo);
		return categoryVo;
	}


	public CategoryVo copy(Category category){
		CategoryVo categoryVo = new CategoryVo();
		BeanUtils.copyProperties(category,categoryVo);
		return categoryVo;
	}
	public List<CategoryVo> copyList(List<Category> categoryList){
		List<CategoryVo> categoryVoList = new ArrayList<>();
		for (Category category : categoryList) {
			categoryVoList.add(copy(category));
		}
		return categoryVoList;
	}
	// 写文章模块 获取用户输入的所有分类并显示在编辑页面
	@Override
	public Result findAll() {
		List<Category> categories = this.categoryMapper.selectList(new LambdaQueryWrapper<>());
		return Result.success(copyList(categories));
	}
}