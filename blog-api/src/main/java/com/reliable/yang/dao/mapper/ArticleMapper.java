package com.reliable.yang.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.reliable.yang.dao.dos.Archives;
import com.reliable.yang.dao.pojo.Article;

import java.util.List;
/**
 * 查询所有文章列表
 * @author Administrator
 * @date 2022-06-20 16:55
 */
public interface ArticleMapper extends BaseMapper<Article> {
	List<Archives> listArchives();
}