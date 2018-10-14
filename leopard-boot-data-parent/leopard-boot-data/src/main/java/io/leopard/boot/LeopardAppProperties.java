package io.leopard.boot;

import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
@PropertySource("classpath:/application.properties")
@ConditionalOnResource(resources = "classpath:/application.properties")
public class LeopardAppProperties {
	private String basePackages;

	// public LeopardAppProperties() {
	// new Exception("LeopardAppProperties").printStackTrace();
	// }

	public String getBasePackages() {
		return basePackages;
	}

	public void setBasePackages(String basePackages) {
		this.basePackages = basePackages;
	}

}
