package com.reliable.yang.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.reliable.yang.dao.mapper.ArticleMapper;
import com.reliable.yang.dao.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 线程池
 * @author Administrator
 * @date 2022-06-22 21:56
 */
@Component
public class ThreadService {


	@Async("taskExecutor")
	public void updateViewCount(ArticleMapper articleMapper, Article article){

		Article articleUpdate = new Article();
		articleUpdate.setViewCounts(article.getViewCounts() + 1);
		LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(Article::getId,article.getId());
		queryWrapper.eq(Article::getViewCounts,article.getViewCounts());
		articleMapper.update(articleUpdate,queryWrapper);
		try {
			//睡眠5秒 证明不会影响主线程的使用
			Thread.sleep(5000);
			System.out.println("阅读数更新完成...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
