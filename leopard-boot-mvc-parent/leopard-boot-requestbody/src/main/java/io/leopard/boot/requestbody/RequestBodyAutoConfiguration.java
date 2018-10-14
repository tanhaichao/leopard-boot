package io.leopard.boot.requestbody;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

public class RequestBodyAutoConfiguration {
	// @Bean
	// public RemoteIpFilter remoteIpFilter() {
	// return new RemoteIpFilter();
	// }

	@Bean
	public FilterRegistrationBean testFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new RequestBodyFilter());// 添加过滤器
		registration.addUrlPatterns("/*");// 设置过滤路径，/*所有路径
		registration.setName("RequestBodyFilter");// 设置优先级
		registration.setOrder(1);// 设置优先级
		return registration;
	}
}
