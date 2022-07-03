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
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

	String module() default "";

	String operation() default "";
}