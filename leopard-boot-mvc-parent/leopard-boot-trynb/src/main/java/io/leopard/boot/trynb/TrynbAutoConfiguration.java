package io.leopard.boot.trynb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
// @ConditionalOnClass(HelloService.class)
public class TrynbAutoConfiguration {

	@Bean
	public TrynbExceptionHandler TrynbExceptionHandler() {
		return new TrynbExceptionHandler();
	}
}