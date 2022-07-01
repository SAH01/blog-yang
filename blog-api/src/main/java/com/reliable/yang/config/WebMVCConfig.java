package com.reliable.yang.config;

import com.reliable.yang.handler.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置统一登录拦截器
 * @author Administrator
 * @date 2022-06-21 22:01
 */
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

	@Autowired
	private LoginInterceptor loginInterceptor;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		//跨域配置
		registry.addMapping("/**").allowedOrigins("http://localhost:8080");
	}

	//登录统一拦截器 （测试test）
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginInterceptor)
				.addPathPatterns("/test")
				.addPathPatterns("/comments/create/change")
				.addPathPatterns("/articles/publish");
	}
}