package io.leopard.jdbc.autoconfigure;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import io.leopard.jdbc.JdbcMysqlImpl;
import io.leopard.jdbc.datasource.MysqlDsnDataSource;
import io.leopard.jdbc.transaction.LeopardTransactionManager;

/**
 * 其他数据源注册
 * 
 * @author 谭海潮
 *
 */
public class OtherJdbcRegistrar implements EnvironmentAware, ImportBeanDefinitionRegistrar {

	private List<String> datasourcePrifixList = new ArrayList<>();

	private ConfigurableEnvironment env;

	@Override
	public void setEnvironment(Environment environment) {
		this.env = (ConfigurableEnvironment) environment;
		// System.err.println("getSystemProperties:" + env.getSystemProperties());
		Set<String> nameSet = EnvironmentUtil.findPropertyeNameSet(env, "^spring.datasource\\..+\\.url$");
		for (String name : nameSet) {
			// String value = env.getProperty(name);
			String prefix = name.replaceFirst("\\.url$", "");
			datasourcePrifixList.add(prefix);
			// System.err.println("name:" + name + " value:" + value);
		}
	}

	@Override
	public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
		// new Exception("JdbcRegistrar registerBeanDefinitions").printStackTrace();
		for (String prefix : datasourcePrifixList) {
			String datasourceName = prefix.substring(prefix.lastIndexOf(".") + 1);

			this.registerDataSourceBean(datasourceName, registry);
			this.registerTransactionManager(datasourceName, registry);
			this.registerJdbc(datasourceName, registry);
		}
	}

	protected void registerJdbc(String datasourceName, BeanDefinitionRegistry registry) {
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(JdbcMysqlImpl.class);
		beanDefinitionBuilder.addPropertyReference("dataSource", datasourceName + "DataSource");
		registry.registerBeanDefinition(datasourceName + "Jdbc", beanDefinitionBuilder.getBeanDefinition());
	}

	protected void registerTransactionManager(String datasourceName, BeanDefinitionRegistry registry) {
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(LeopardTransactionManager.class);
		beanDefinitionBuilder.addPropertyReference("dataSource", datasourceName + "DataSource");
		registry.registerBeanDefinition(datasourceName + "TransactionManager", beanDefinitionBuilder.getBeanDefinition());

		// GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		// beanDefinition.setBeanClass(LeopardTransactionManager.class);
		// beanDefinition.setSynthetic(true);
		// MutablePropertyValues mpv = beanDefinition.getPropertyValues();
		// mpv.addPropertyValue("dataSource", datasourceName + "DataSource");
		// registry.registerBeanDefinition(datasourceName + "TransactionManager", beanDefinition);
	}

	protected void registerDataSourceBean(String datasourceName, BeanDefinitionRegistry registry) {
		// System.err.println("datasourceName:" + datasourceName);
		String url = env.getProperty("spring.datasource." + datasourceName + ".url");
		String username = env.getProperty("spring.datasource." + datasourceName + ".username");
		String password = env.getProperty("spring.datasource." + datasourceName + ".password");

		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(MysqlDsnDataSource.class);
		beanDefinition.setSynthetic(true);

		beanDefinition.setInitMethodName("init");
		beanDefinition.setDestroyMethodName("destroy");

		MutablePropertyValues mpv = beanDefinition.getPropertyValues();
		mpv.addPropertyValue("maxPoolSize", 15);
		mpv.addPropertyValue("user", username);
		mpv.addPropertyValue("password", password);
		mpv.addPropertyValue("url", url);
		mpv.addPropertyValue("idleConnectionTestPeriod", 60);

		registry.registerBeanDefinition(datasourceName + "DataSource", beanDefinition);
	}
}
