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

	// 根据ID找到分类名称
	@Override
	public CategoryVo findCategoryById(Long categoryId){
		Category category = categoryMapper.selectById(categoryId);
		CategoryVo categoryVo = new CategoryVo();
		BeanUtils.copyProperties(category,categoryVo);
		// 缓存精度bug
		categoryVo.setId(String.valueOf(category.getId()));
		return categoryVo;
	}


	public CategoryVo copy(Category category){
		CategoryVo categoryVo = new CategoryVo();
		BeanUtils.copyProperties(category,categoryVo);
		categoryVo.setId(String.valueOf(category.getId()));
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
		LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.select(Category::getId,Category::getCategoryName);
		List<Category> categories = categoryMapper.selectList(queryWrapper);
		return Result.success(copyList(categories));
	}
	// 导航栏分类详情 把分类表所有的东西都要显示出来
	@Override
	public Result findAllDetail() {
		List<Category> categories = categoryMapper.selectList(new LambdaQueryWrapper<>());
		//页面交互的对象
		return Result.success(copyList(categories));
	}

	// 分类文章列表 分类详情
	@Override
	public Result categoriesDetailById(Long id) {
		Category category = categoryMapper.selectById(id);
//		CategoryVo categoryVo = copy(category);
		return Result.success(category);
	}
}