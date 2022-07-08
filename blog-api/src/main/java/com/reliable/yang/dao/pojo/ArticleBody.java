package com.reliable.yang.dao.pojo;

import lombok.Data;

/**
 * @author Administrator
 * @date 2022-06-22 19:17
 * 文章详情信息
 */
@Data
public class ArticleBody {
	private Long id;
	private String content;
	private String contentHtml;
	private Long articleId;
}
