package com.reliable.yang.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.reliable.yang.dao.pojo.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Administrator
 * @date 2022-06-20 16:56
 */
public interface TagMapper extends BaseMapper<Tag> {
	/**
	 * 根据文章id查询标签列表
	 * @param articleId
	 * @return
	 */
	// 一篇文章多个标签
	List<Tag> findTagsByArticleId(Long articleId);
	// 热门标签
	List<Long> findHotsTagIds(int limit);
	// 从文章拿到标签ID 再用标签ID拿到标签名字内容
	List<Tag> findTagsByTagIds(@Param("tagIds") List<Long> hotsTagIds);

}