package com.reliable.yang.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;


import java.util.List;

/**
 * @author Administrator
 * @date 2022-06-26 17:45
 */
@Data
public class CommentVo  {
//	@JsonSerialize(using = ToStringSerializer.class)
	private String id;

	private UserVo author;

	private String content;

	private List<CommentVo> childrens;

	private String createDate;

	private Integer level;

	private UserVo toUser;
}