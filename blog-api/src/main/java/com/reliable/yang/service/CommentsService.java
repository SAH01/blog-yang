package com.reliable.yang.service;

import com.reliable.yang.vo.Result;
import com.reliable.yang.vo.params.CommentParam;

/**
 * 根据文章ID查询评论列表
 * @author Administrator
 * @date 2022-06-26 17:44
 */
public interface CommentsService {
	Result commentsByArticleId(Long articleId);
	Result comment(CommentParam commentParam);
}

