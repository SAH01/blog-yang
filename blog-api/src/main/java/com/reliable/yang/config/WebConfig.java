package com.reliable.yang.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 前后端分离，配置访问域名
 * @author Administrator
 * @date 2022-06-20 16:36
 */
@Configuration
public class WebConfig  implements WebMvcConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		//跨域配置，不可设置为*，不安全, 前后端分离项目，可能域名不一致
		//本地测试 端口不一致 也算跨域
		registry.addMapping("/**").allowedOrigins("http://localhost:8080");
	}
}