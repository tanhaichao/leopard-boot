package io.leopard.boot.onum.dynamic;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import io.leopard.boot.LeopardApplicationListener;

public class DynamicEnumImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

	// private Environment environment;

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		// logger.info("postProcessBeanFactory");
		String basePackage = LeopardApplicationListener.getBasePackages();

		// logger.info("basePackage:" + basePackage);
		if (StringUtils.isEmpty(basePackage)) {
			throw new RuntimeException("app.properties没有配置base.package属性.");
		}
		// System.err.println("DynamicEnumScannerConfigurer postProcessBeanFactory");

		DynamicEnumScanner scanner = new DynamicEnumScanner(registry);
		// scanner.setResourceLoader(this.applicationContext);
		scanner.scan(basePackage);

		// // System.err.println("XiaoniuJettyTestImportBeanDefinitionRegistrar...");
		// ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
		// // scanner.addIncludeFilter(new AssignableTypeFilter(Foo.class));
		// scanner.scan(packageName);
	}

	// @Override
	// public void setEnvironment(Environment environment) {
	// System.err.println("setEnvironment:" + environment);
	// this.environment = environment;
	// }

}
