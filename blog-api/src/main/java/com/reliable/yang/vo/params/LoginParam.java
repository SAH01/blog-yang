package com.reliable.yang.vo.params;

import lombok.Data;

/**
 * @author Administrator
 * @date 2022-06-21 21:06
 */
import lombok.Data;

@Data
public class LoginParam {

	private String account;

	private String password;

	private String nickname;
}