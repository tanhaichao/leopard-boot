package io.leopard.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
// @EnableConfigurationProperties({ LeopardAppProperties.class })
@Import(LeopardScannerRegistrar.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LeopardAutoConfiguration {

	@Autowired(required = false) // TODO
	private LeopardAppProperties leopardAppProperties;

	public LeopardAppProperties getLeopardAppProperties() {
		return leopardAppProperties;
	}

	public void setLeopardAppProperties(LeopardAppProperties leopardAppProperties) {
		this.leopardAppProperties = leopardAppProperties;
	}

}