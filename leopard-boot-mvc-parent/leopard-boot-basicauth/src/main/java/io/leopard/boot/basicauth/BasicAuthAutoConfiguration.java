package io.leopard.boot.basicauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan
public class BasicAuthAutoConfiguration extends WebMvcConfigurerAdapter {

	@Autowired
	private BasicAuthHandlerInterceptor basicAuthHandlerInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(basicAuthHandlerInterceptor);
	}
}