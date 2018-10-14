package io.leopard.jvmdns;

import java.io.IOException;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class JvmdnsEnvironmentPostProcessor implements EnvironmentPostProcessor {

	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		Resource resource = new ClassPathResource("jvmdns.properties");
		if (!resource.exists()) {// 配置文件不存在，不启用JVMDNS
			return;
		}
		load(resource);
	}

	public static void load(Resource resource) {
		Properties properties;
		try {
			properties = PropertiesLoaderUtils.loadProperties(resource);
		}
		catch (IOException e) {
			throw new RuntimeException("加载JVMDNS配置出错.", e);
		}
		JavaHost.updateVirtualDns(properties);
	}
}