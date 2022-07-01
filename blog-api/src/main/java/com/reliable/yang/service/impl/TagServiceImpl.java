package com.reliable.yang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.reliable.yang.dao.mapper.TagMapper;
import com.reliable.yang.dao.pojo.Tag;
import com.reliable.yang.service.TagService;
import com.reliable.yang.vo.Result;
import com.reliable.yang.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Administrator
 * @date 2022-06-21 17:47
 */
@Service
public class TagServiceImpl implements TagService {
	@Autowired
	private TagMapper tagMapper;

	public TagVo copy(Tag tag){
		TagVo tagVo = new TagVo();
		BeanUtils.copyProperties(tag,tagVo);
		return tagVo;
	}
	public List<TagVo> copyList(List<Tag> tagList){
		List<TagVo> tagVoList = new ArrayList<>();
		for (Tag tag : tagList) {
			tagVoList.add(copy(tag));
		}
		return tagVoList;
	}

	@Override
	public List<TagVo> findTagsByArticleId(Long id) {
		List<Tag> tags = tagMapper.findTagsByArticleId(id);
		return copyList(tags);
	}

	/**
	 * 最热标签
	 * 1. 标签所拥有的文章数量最多
	 * 2. 根据tag_id进行分组计数 从大到小排列 取前limit个
	 *
	 * @param limit
	 * @return
	 */
	@Override
	public List<TagVo> hot(int limit) {

		List<Long> hotsTagIds = tagMapper.findHotsTagIds(limit);
		if (CollectionUtils.isEmpty(hotsTagIds)){
			return Collections.emptyList();
		}
		// select * from tag where id in ()
		List<Tag> tagList = tagMapper.findTagsByTagIds(hotsTagIds);
		return copyList(tagList);
	}

	/**
	 * 获取所有标签显示在文章编辑页
	 * @return
	 */
	@Override
	public Result findAll() {
		List<Tag> tags = this.tagMapper.selectList(new LambdaQueryWrapper<>());
		return Result.success(copyList(tags));
	}

}