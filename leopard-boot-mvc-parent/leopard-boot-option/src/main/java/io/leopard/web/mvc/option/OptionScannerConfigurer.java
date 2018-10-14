package io.leopard.web.mvc.option;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import io.leopard.boot.LeopardApplicationListener;

@Component
public class OptionScannerConfigurer implements BeanFactoryPostProcessor, ApplicationContextAware {

	protected Log logger = LogFactory.getLog(this.getClass());

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// logger.info("setApplicationContext");
		this.applicationContext = applicationContext;
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		// logger.info("postProcessBeanFactory");
		// LeopardPropertyPlaceholderConfigurer configurer = beanFactory.getBean(LeopardPropertyPlaceholderConfigurer.class);
		String basePackage = LeopardApplicationListener.getBasePackages();
		logger.info("basePackage:" + basePackage);
		if (basePackage == null || basePackage.length() == 0) {
			String message = "application.properties没有配置app.basePackages属性，Leopard无法扫描项目定义了哪些枚举类.";
			logger.warn(message);
			return;
		}
		// System.err.println("OptionScannerConfigurer postProcessBeanFactory");
		OptionScanner scanner = new OptionScanner((BeanDefinitionRegistry) beanFactory);
		scanner.setResourceLoader(this.applicationContext);
		scanner.scan(basePackage);
	}

}
