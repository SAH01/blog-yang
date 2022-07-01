package com.reliable.yang.vo.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Administrator
 * @date 2022-06-20 17:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageParams {
	private int page = 1 ;

	private int pageSize = 10;
}