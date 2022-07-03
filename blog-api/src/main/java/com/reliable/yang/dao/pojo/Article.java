package com.reliable.yang.dao.pojo;

/**
 * @author Administrator
 * @date 2022-06-20 16:46
 * 文章基本信息
 */
import lombok.Data;

@Data
public class Article {

	public static final int Article_TOP = 1;

	public static final int Article_Common = 0;

	private String id;

	private String title;

	private String summary;

	private Integer commentCounts;

	private Integer viewCounts;

	/**
	 * 作者id
	 */
	private Long authorId;
	/**
	 * 内容id
	 */
	private Long bodyId;
	/**
	 *类别id
	 */
	private Long categoryId;

	/**
	 * 置顶
	 */
	private Integer weight = Article_Common;

	/**
	 * 创建时间
	 */
	private Long createDate;
}
