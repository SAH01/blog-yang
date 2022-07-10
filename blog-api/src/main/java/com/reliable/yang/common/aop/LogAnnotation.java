package com.reliable.yang.common.aop;

/**
 * @BelongsProject: blog-parent
 * @BelongsPackage: com.reliable.yang.common.aop
 * @Author: Administrator
 * @CreateTime: 2022-07-02 17:06
 * @Description: 日志
 */

import java.lang.annotation.*;

/**
 * 日志注解
 */
// Type表示可以放到类上面 METHOD 表示可以放在方法上
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {
	// @interface意思是声明一个注解，方法名对应参数名，返回值类型对应参数类型。
	String module() default "";

	String operation() default "";
}