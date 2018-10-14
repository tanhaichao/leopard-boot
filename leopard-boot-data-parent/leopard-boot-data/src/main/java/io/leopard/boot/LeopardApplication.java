package io.leopard.boot;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import io.leopard.spring.LeopardBeanFactoryAware;

@SpringBootApplication
// @ComponentScan("com.baidu")
// @EnableConfigurationProperties(LeopardAppProperties.class)
@EnableAutoConfiguration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LeopardApplication {

	@Bean
	public LeopardBeanFactoryAware LeopardBeanFactoryAware() {
		return new LeopardBeanFactoryAware();
	}
}
