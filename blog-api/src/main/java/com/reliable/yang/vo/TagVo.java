package com.reliable.yang.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author Administrator
 * @date 2022-06-20 17:23
 */
@Data
public class TagVo {
//	@JsonSerialize(using = ToStringSerializer.class)
	private String id;
	private String tagName;
}