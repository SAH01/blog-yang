package com.reliable.yang.admin.model.params;

import lombok.Data;

/**
 * @author Administrator
 * @date 2022-07-04 22:02
 */
@Data
public class PageParam {

	private Integer currentPage;

	private Integer pageSize;

	private String queryString;
}