package com.reliable.yang.vo;

/**
 * @author Administrator
 * @date 2022-06-22 19:20
 */
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class CategoryVo {
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	private String avatar;

	private String categoryName;
}
