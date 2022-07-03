package com.reliable.yang.service;

import com.reliable.yang.vo.Result;
import com.reliable.yang.vo.TagVo;

import java.util.List;

/**
 * @BelongsProject: blog-parent
 * @BelongsPackage: com.reliable.yang.service
 * @Author: Administrator
 * @CreateTime: 2022-06-21 17:43
 * @Description: 标签
 */
public interface TagService {
	List<TagVo> findTagsByArticleId(Long articleId);
	List<TagVo> hot(int limit);

	// 获取 所有标签显示在文章编辑页
	Result findAll();
	// 导航栏
	Result findAllDetail();
	// 标签文章列表
	Result findDetailById(Long id);
}
