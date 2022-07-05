package com.reliable.yang.admin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Administrator
 * @date 2022-07-04 22:02
 */
@Data
public class Permission {

	@TableId(type = IdType.AUTO)
	private Long id;

	private String name;

	private String path;

	private String description;
}
