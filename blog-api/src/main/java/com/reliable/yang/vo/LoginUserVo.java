package com.reliable.yang.vo;

/**
 * @author Administrator
 * @date 2022-06-21 21:27
 */
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class LoginUserVo {
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	private String account;

	private String nickname;

	private String avatar;
}
