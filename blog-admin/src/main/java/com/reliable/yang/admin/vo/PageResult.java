package com.reliable.yang.admin.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Administrator
 * @date 2022-07-04 22:04
 */

@Data
public class PageResult<T> {

	private List<T> list;

	private Long total;
}