package com.reliable.yang.controller;

import com.reliable.yang.common.aop.LogAnnotation;
import com.reliable.yang.service.ArticleService;
import com.reliable.yang.service.ThreadService;
import com.reliable.yang.vo.Result;
import com.reliable.yang.vo.params.ArticleParam;
import com.reliable.yang.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 首页、文章详情页
 * @author Administrator
 * @date 2022-06-20 16:50
 */
@RestController
@RequestMapping("articles")
public class ArticleController {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ThreadService threadService;
	//Result 统一结果返回
	@PostMapping
//	@Cache(expire = 5 * 60 * 1000 ,name = "article_list")
	@LogAnnotation(module = "文章" , operation = "获取文章列表")
	public Result listArticle(@RequestBody PageParams pageParams) {
		//ArticleVo 页面接收的数据
		return articleService.listArticle(pageParams);
	}
	/**
	 * 首页 最热文章
	 * @param
	 * @return
	 */
	@PostMapping("hot")
	public Result hotArticle() {
		int limit = 5;
		return articleService.hotArticle(limit);
	}
	/**
	 * 首页 最新文章
	 * @return
	 */
	@PostMapping("new")
	public Result newArticles(){
		int limit = 5;
		return articleService.newArticles(limit);
	}

	/**
	 * 首页 文章归档
	 * @return
	 */
	@PostMapping("listArchives")
	public Result listArchives(){

		return articleService.listArchives();
	}

	/**
	 * 文章详情
	 * @param articleId
	 * @return
	 */

	@PostMapping("view/{id}")
	public Result findArticleById(@PathVariable("id") Long articleId) {
		return articleService.findArticleById(articleId);
	}

	/**
	 * 写文章
	 * @param articleParam
	 * @return
	 */
	@PostMapping("publish")
	public Result publish(@RequestBody ArticleParam articleParam){
		return articleService.publish(articleParam);
	}
	@PostMapping("{id}")
	public Result articleById(@PathVariable("id") Long articleId){
		return articleService.findArticleById(articleId);
	}
}
