package io.leopard.boot.trynb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// @ConditionalOnClass(HelloService.class)
public class TrynbAutoConfiguration {

	@Bean
	public TrynbExceptionHandler TrynbExceptionHandler() {
		return new TrynbExceptionHandler();
	}
}