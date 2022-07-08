package com.reliable.yang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.reliable.yang.dao.mapper.CommentMapper;
import com.reliable.yang.dao.pojo.Comment;
import com.reliable.yang.dao.pojo.SysUser;
import com.reliable.yang.service.CommentsService;
import com.reliable.yang.service.SysUserService;
import com.reliable.yang.utils.UserThreadLocal;
import com.reliable.yang.vo.CommentVo;
import com.reliable.yang.vo.Result;
import com.reliable.yang.vo.UserVo;
import com.reliable.yang.vo.params.CommentParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * 1. 根据文章ID 得到 评论列表 从comment表获取
 * 2. 根据作者ID 得到 作者信息
 * 3. 判断 level 查询有没有下一层评论
 * 4. 如果有 则根据评论ID 查询 parent_id
 * @author Administrator
 * @date 2022-06-26 17:45
 */
@Service
public class CommentsServiceImpl implements CommentsService {
	@Autowired
	private CommentMapper commentMapper;
	@Autowired
	private SysUserService sysUserService;

	public CommentVo copy(Comment comment){
		CommentVo commentVo = new CommentVo();
		BeanUtils.copyProperties(comment,commentVo);

		commentVo.setId(String.valueOf(comment.getId()));
		//时间格式化
		commentVo.setCreateDate(new DateTime(comment.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
		// 作者信息
		Long authorId = comment.getAuthorId();
		UserVo userVo = sysUserService.findUserVoById(authorId);
		commentVo.setAuthor(userVo);
		// 子评论
		List<CommentVo> commentVoList = findCommentsByParentId(comment.getId());
		commentVo.setChildrens(commentVoList);
		// 给谁评论
		if (comment.getLevel() > 1) {
			Long toUid = comment.getToUid();
			UserVo toUserVo = sysUserService.findUserVoById(toUid);
			commentVo.setToUser(toUserVo);
		}
		return commentVo;
	}

	// 查找子评论
	private List<CommentVo> findCommentsByParentId(Long id) {
		LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(Comment::getParentId,id);
		queryWrapper.eq(Comment::getLevel,2);
		List<Comment> comments = this.commentMapper.selectList(queryWrapper);
		return copyList(comments);
	}

	public List<CommentVo> copyList(List<Comment> commentList){
		List<CommentVo> commentVoList = new ArrayList<>();
		for (Comment comment : commentList) {
			commentVoList.add(copy(comment));
		}
		return commentVoList;
	}

	/**
	 * 显示评论
	 * @param articleId
	 * @return
	 */
	@Override
	public Result commentsByArticleId(Long articleId) {
		LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(Comment::getArticleId,articleId);
		queryWrapper.eq(Comment::getLevel,1);
		List<Comment> comments = commentMapper.selectList(queryWrapper);
		return Result.success(copyList(comments));
	}

	/**实现写评论功能
	 * @param commentParam
	 * @return
	 */
	@Override
	public Result comment(CommentParam commentParam) {
		SysUser sysUser = UserThreadLocal.get();
		Comment comment = new Comment();
		comment.setArticleId(commentParam.getArticleId());
		comment.setAuthorId(sysUser.getId());
		comment.setContent(commentParam.getContent());
		comment.setCreateDate(System.currentTimeMillis());
		Long parent = commentParam.getParent();
		if (parent == null || parent == 0) {
			comment.setLevel(1);
		}else{
			comment.setLevel(2);
		}
		comment.setParentId(parent == null ? 0 : parent);
		Long toUserId = commentParam.getToUserId();
		comment.setToUid(toUserId == null ? 0 : toUserId);
		this.commentMapper.insert(comment);  //插入数据库
		return Result.success(null);
	}
}