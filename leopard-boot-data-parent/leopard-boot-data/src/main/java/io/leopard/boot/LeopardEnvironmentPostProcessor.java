package io.leopard.boot;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

public class LeopardEnvironmentPostProcessor implements EnvironmentPostProcessor {

	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("spring.main.banner-mode", "off");
		properties.put("spring.mvc.favicon.enabled", "false");

		if (SystemUtils.IS_OS_WINDOWS) {// TODO 暂时写死
			// properties.put("server.port", "");
			properties.put("server.address", "");
		}

		PropertySource<?> propertySource = new MapPropertySource("LeopardDefaultEnv", properties);
		environment.getPropertySources().addFirst(propertySource);
	}

}
