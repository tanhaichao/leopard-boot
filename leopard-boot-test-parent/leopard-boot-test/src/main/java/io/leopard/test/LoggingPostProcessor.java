package io.leopard.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

public class LoggingPostProcessor implements EnvironmentPostProcessor {

	protected Log logger = LogFactory.getLog(this.getClass());

	protected static boolean isJunit() {
		// 改成判断是否web容器启动更好?RequestContextHolder.getRequestAttributes() == null?

		// new Exception().printStackTrace();
		StackTraceElement[] elements = Thread.currentThread().getStackTrace();
		for (StackTraceElement element : elements) {
			if (element.getClassName().equals("org.junit.runners.ParentRunner")) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		if (!isJunit()) {
			return;
		}
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("logging.config", "classpath:logback-leopard-boot-test.xml");

		if (!properties.isEmpty()) {
			PropertySource<?> propertySource = new MapPropertySource("LeopardBootTest", properties);
			environment.getPropertySources().addFirst(propertySource);
		}
	}

}
