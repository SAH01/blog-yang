package com.reliable.yang.controller;

import com.reliable.yang.service.CommentsService;
import com.reliable.yang.vo.Result;
import com.reliable.yang.vo.params.CommentParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 * @date 2022-06-26 17:44
 */
@RestController
@RequestMapping("comments")
public class CommentsController {

	@Autowired
	private CommentsService commentsService;

	// 展示评论信息
	@GetMapping("article/{id}")
	public Result comments(@PathVariable("id") Long articleId){

		return commentsService.commentsByArticleId(articleId);

	}
	// 实现评论功能
	@PostMapping("create/change")
	public Result comment(@RequestBody CommentParam commentParam){
		return commentsService.comment(commentParam);
	}
}