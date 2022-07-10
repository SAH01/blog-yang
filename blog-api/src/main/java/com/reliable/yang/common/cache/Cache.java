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
	// @interface意思是声明一个注解，方法名对应参数名，返回值类型对应参数类型。
	long expire() default 1 * 60 * 1000;    //过期时间
	String name() default "";   //缓存标识

}