package com.reliable.yang.vo.params;

import lombok.Data;

/**
 * @author Administrator
 * @date 2022-06-26 18:17
 */
@Data
public class CommentParam {

	private Long articleId;

	private String content;

	private Long parent;

	private Long toUserId;
}