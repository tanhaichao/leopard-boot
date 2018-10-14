package io.leopard.test;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@SpringBootApplication
// @ComponentScan("com.baidu")
// @EnableConfigurationProperties(LeopardAppProperties.class)
@EnableAutoConfiguration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LeopardTestApplication {

	@Bean
	@ConditionalOnClass(name = "io.leopard.boot.LeopardApplication")
	public Object application() throws Exception {
		String className = "io.leopard.boot.LeopardApplication";
		Class<?> clazz = Class.forName(className);
		return clazz.newInstance();
	}
}
