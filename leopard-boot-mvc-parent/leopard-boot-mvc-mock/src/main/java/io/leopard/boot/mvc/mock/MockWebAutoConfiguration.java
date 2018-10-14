package io.leopard.boot.mvc.mock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.leopard.boot.mvc.mock.asserter.AssertControllerManager;

@Configuration
public class MockWebAutoConfiguration {

	@Bean
	public AssertControllerManager AssertControllerManager() {
		return new AssertControllerManager();
	}

}