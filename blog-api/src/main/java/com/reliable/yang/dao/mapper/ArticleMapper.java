package com.reliable.yang.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reliable.yang.dao.dos.Archives;
import com.reliable.yang.dao.pojo.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * 查询所有文章列表
 * @author Administrator
 * @date 2022-06-20 16:55
 */
public interface ArticleMapper extends BaseMapper<Article> {
	List<Archives> listArchives();
	// 自定义查询文章列表 因为需要加入年月归档查询
	IPage<Article> listArticle(@Param("page") Page<Article> page,
	                           @Param("categoryId") Long categoryId,
	                           @Param("tagId") Long tagId,
	                           @Param("year") String year,
	                           @Param("month") String month);
}