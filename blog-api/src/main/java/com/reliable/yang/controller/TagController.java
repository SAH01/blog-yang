package com.reliable.yang.controller;

import com.reliable.yang.service.TagService;
import com.reliable.yang.vo.Result;
import com.reliable.yang.vo.TagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 热门标签
 * @author Administrator
 * @date 2022-06-21 19:31
 */
@RestController
@RequestMapping("tags")
public class TagController {

	@Autowired
	private TagService tagsService;

	@GetMapping("/hot")
	public Result listHotTags() {
		int limit = 4;
		List<TagVo> tagVoList = tagsService.hot(limit);
		return Result.success(tagVoList);
	}

	// 获取所有标签 显示在发布文章的编辑页
	@GetMapping
	public Result findAll(){
		return tagsService.findAll();
	}

	// 导航栏显示所有标签
	@GetMapping("detail")
	public Result findAllDetail(){
		return tagsService.findAllDetail();
	}
	// 对应标签下显示文章列表
	@GetMapping("detail/{id}")
	public Result findDetailById(@PathVariable("id") Long id){
		return tagsService.findDetailById(id);
	}
}