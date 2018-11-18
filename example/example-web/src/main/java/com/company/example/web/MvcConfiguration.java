package com.company.example.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * MVC配置
 * 
 * @author 谭海潮
 *
 */
@Configuration
public class MvcConfiguration extends WebMvcConfigurerAdapter {

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		configurer.setUseSuffixPatternMatch(false);// 设置是否是后缀模式匹配，如“/user”是否匹配/user.*，默认真即匹配
		configurer.setUseTrailingSlashMatch(false);// 设置是否自动后缀路径模式匹配，如“/user”是否匹配“/user/”，默认真即匹配；
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// 设置了可以被跨域访问的路径和可以被哪些主机跨域访问
	}

	/**
	 * 添加拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 静态资源访问
	}
}