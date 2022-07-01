package com.reliable.yang.service;

import com.reliable.yang.vo.ArticleVo;
import com.reliable.yang.vo.Result;
import com.reliable.yang.vo.params.ArticleParam;
import com.reliable.yang.vo.params.PageParams;
import org.springframework.data.repository.query.Param;

/**
 * @author Administrator
 * @date 2022-06-20 16:51
 */
public interface ArticleService {
	Result listArticle(@Param("pageParams") PageParams pageParams);

	/**
	 * 最热文章
	 * @param limit
	 * @return
	 */
	Result hotArticle(int limit);

	/**
	 * 最新文章
	 * @param limit
	 * @return
	 */
	Result newArticles(int limit);

	/**
	 * 文章归档
	 * @return
	 */
	Result listArchives();

	/**
	 * 根据文章Id返回文章详情
	 * @param articleId
	 * @return
	 */
	Result findArticleById(Long articleId);

	/**
	 * 写文章并发布
	 * @param articleParam
	 * @return
	 */
	Result publish(ArticleParam articleParam);
}