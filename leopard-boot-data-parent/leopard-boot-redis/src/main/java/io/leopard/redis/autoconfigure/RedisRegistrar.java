package io.leopard.redis.autoconfigure;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import io.leopard.boot.spring.util.EnvironmentUtil;
import io.leopard.redis.RedisImpl;

/**
 * 其他数据源注册
 * 
 * @author 谭海潮
 *
 */
public class RedisRegistrar implements EnvironmentAware, ImportBeanDefinitionRegistrar {

	private List<String> nameList = new ArrayList<>();

	private ConfigurableEnvironment env;

	@Override
	public void setEnvironment(Environment environment) {
		this.env = (ConfigurableEnvironment) environment;
		// System.err.println("getSystemProperties:" + env.getSystemProperties());
		Set<String> nameSet = EnvironmentUtil.findPropertyeNameSet(env, "^.+\\.redis\\.host$");
		for (String name : nameSet) {
			// String value = env.getProperty(name);
			String prefix = name.replaceFirst("\\.redis\\.host$", "");
			nameList.add(prefix);
		}
	}

	@Override
	public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
		// new Exception("JdbcRegistrar registerBeanDefinitions").printStackTrace();
		for (String name : nameList) {
			// String datasourceName = name.substring(name.lastIndexOf(".") + 1);

			this.registerRedisBean(name, registry);
		}
	}

	protected void registerRedisBean(String name, BeanDefinitionRegistry registry) {
		// System.err.println("datasourceName:" + datasourceName);
		String host = env.getProperty(name + ".redis.host");
		String port = env.getProperty(name + ".redis.port");
		String password = env.getProperty(name + ".redis.password");
		String maxActive = env.getProperty(name + ".redis.maxActive");

		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(RedisImpl.class);
		beanDefinition.setSynthetic(true);

		beanDefinition.setInitMethodName("init");
		beanDefinition.setDestroyMethodName("destroy");

		MutablePropertyValues mpv = beanDefinition.getPropertyValues();
		mpv.addPropertyValue("server", host + ":" + port);
		mpv.addPropertyValue("password", password);
		if (!StringUtils.isEmpty(maxActive)) {
			mpv.addPropertyValue("maxActive", Integer.parseInt(maxActive));
		}

		String beanNaname;
		if ("app".equals(name) || "redis".equals(name)) {
			beanNaname = "redis";
		}
		else {
			beanNaname = name + "Redis";
		}
		registry.registerBeanDefinition(beanNaname, beanDefinition);
	}
}
