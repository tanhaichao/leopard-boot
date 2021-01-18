package io.leopard.boot.trynb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan
// @ConditionalOnClass(HelloService.class)
public class TrynbAutoConfiguration extends WebMvcConfigurerAdapter {

	@Autowired
	private TrynbHandlerExceptionResolver trynbHandlerExceptionResolver;

	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		exceptionResolvers.add(0, trynbHandlerExceptionResolver);
	}

	// @Bean
	// public TrynbExceptionHandler TrynbExceptionHandler() {
	// return new TrynbExceptionHandler();
	// }
}