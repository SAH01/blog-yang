package com.reliable.yang.dao.dos;

/**
 * 归档 实体类（日期 - XX篇）
 * @author Administrator
 * @date 2022-06-21 20:27
 */
import lombok.Data;

@Data
public class Archives {

	private Integer year;

	private Integer month;

	private Integer count;
}