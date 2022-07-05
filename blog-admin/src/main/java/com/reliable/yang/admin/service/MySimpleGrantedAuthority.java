package com.reliable.yang.admin.service;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Administrator
 * @date 2022-07-04 22:12
 */
public class MySimpleGrantedAuthority implements GrantedAuthority {
	private String authority;
	private String path;

	public MySimpleGrantedAuthority(){}

	public MySimpleGrantedAuthority(String authority){
		this.authority = authority;
	}

	public MySimpleGrantedAuthority(String authority,String path){
		this.authority = authority;
		this.path = path;
	}

	@Override
	public String getAuthority() {
		return authority;
	}

	public String getPath() {
		return path;
	}
}