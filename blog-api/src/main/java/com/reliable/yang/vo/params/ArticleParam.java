package com.reliable.yang.vo.params;

import com.reliable.yang.vo.CategoryVo;
import com.reliable.yang.vo.TagVo;
import lombok.Data;

import java.util.List;

/**
 * 写文章
 * @author Administrator
 * @date 2022-06-26 19:16
 */
@Data
public class ArticleParam {

	private Long id;

	private ArticleBodyParam body;

	private CategoryVo category;

	private String summary;

	private List<TagVo> tags;

	private String title;
}
