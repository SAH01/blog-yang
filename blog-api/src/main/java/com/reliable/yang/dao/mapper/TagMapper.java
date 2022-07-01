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
	List<Tag> findTagsByArticleId(Long articleId);

	List<Long> findHotsTagIds(int limit);

	List<Tag> findTagsByTagIds(@Param("tagIds") List<Long> hotsTagIds);

}