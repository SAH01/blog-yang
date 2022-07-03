package com.reliable.yang.common.cache;

import java.lang.annotation.*;

/**
 * @author Administrator
 * @date 2022-07-02 18:57
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {
	long expire() default 1 * 60 * 1000;    //过期时间
	String name() default "";   //缓存标识

}