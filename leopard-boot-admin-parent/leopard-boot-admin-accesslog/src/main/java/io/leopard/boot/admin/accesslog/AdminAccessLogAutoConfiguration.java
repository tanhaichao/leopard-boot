package io.leopard.boot.admin.accesslog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan
public class AdminAccessLogAutoConfiguration extends WebMvcConfigurerAdapter {

	@Autowired
	private AdminAccessLogHandlerInterceptor adminAccessLogHandlerInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(adminAccessLogHandlerInterceptor);
	}
}