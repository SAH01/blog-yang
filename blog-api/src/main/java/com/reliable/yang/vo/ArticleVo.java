package com.reliable.yang.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

/**
 * @author Administrator
 * @date 2022-06-20 16:51
 */
@Data
public class ArticleVo {
	//    @JsonSerialize(using = ToStringSerializer.class)
	private String id;

	private String title;

	private String summary;

	private Integer commentCounts;

	private Integer viewCounts;

	private Integer weight;
	/**
	 * 创建时间
	 */
	private String createDate;

	private UserVo author;

	private ArticleBodyVo body;

	private List<TagVo> tags;

	private CategoryVo category;

}