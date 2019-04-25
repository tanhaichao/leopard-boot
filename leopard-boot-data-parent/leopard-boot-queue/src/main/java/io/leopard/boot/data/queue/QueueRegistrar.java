package io.leopard.boot.data.queue;

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

/**
 * 其他数据源注册
 * 
 * @author 谭海潮
 *
 */
public class QueueRegistrar implements EnvironmentAware, ImportBeanDefinitionRegistrar {

	private List<String> nameList = new ArrayList<>();

	private ConfigurableEnvironment env;

	@Override
	public void setEnvironment(Environment environment) {
		this.env = (ConfigurableEnvironment) environment;
		// System.err.println("getSystemProperties:" + env.getSystemProperties());
		Set<String> nameSet = EnvironmentUtil.findPropertyeNameSet(env, "^.+\\.queue\\.host$");
		for (String name : nameSet) {
			// String value = env.getProperty(name);
			String prefix = name.replaceFirst("\\.queue\\.host$", "");
			nameList.add(prefix);
		}
	}

	@Override
	public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
		// new Exception("JdbcRegistrar registerBeanDefinitions").printStackTrace();
		for (String name : nameList) {
			// String datasourceName = name.substring(name.lastIndexOf(".") + 1);

			this.registerQueueBean(name, registry);
		}
	}

	protected void registerQueueBean(String name, BeanDefinitionRegistry registry) {
		// System.err.println("datasourceName:" + datasourceName);
		String host = env.getProperty(name + ".queue.host");
		String port = env.getProperty(name + ".queue.port");
		String password = env.getProperty(name + ".queue.password");
		String maxActive = env.getProperty(name + ".queue.maxActive");

		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(QueueRedisImpl.class);
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
		if ("app".equals(name) || "queue".equals(name)) {
			beanNaname = "queue";
		}
		else {
			beanNaname = name + "Queue";
		}
		registry.registerBeanDefinition(beanNaname, beanDefinition);
	}
}
