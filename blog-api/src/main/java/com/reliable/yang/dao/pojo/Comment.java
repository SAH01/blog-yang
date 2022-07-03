package com.reliable.yang.dao.pojo;

/**
 * @author Administrator
 * @date 2022-06-26 17:43
 */
import lombok.Data;

@Data
public class Comment {

	private String id;

	private String content;

	private Long createDate;

	private Long articleId;

	private Long authorId;

	private Long parentId;

	private Long toUid;

	private Integer level;
}