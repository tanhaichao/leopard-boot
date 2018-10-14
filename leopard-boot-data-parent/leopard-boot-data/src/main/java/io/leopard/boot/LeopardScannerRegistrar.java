package io.leopard.boot;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

/**
 * 扫描业务系统的包
 * 
 * @author 谭海潮
 *
 */
// @Configuration
// @Order(Ordered.HIGHEST_PRECEDENCE)
// @ConditionalOnProperty(prefix = "app", name = "basePackages")
// @EnableConfigurationProperties
public class LeopardScannerRegistrar implements ImportBeanDefinitionRegistrar {

	@Override
	public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
		String basePackages = LeopardApplicationListener.getBasePackages();
		// new Exception("basePackages:" + basePackages).printStackTrace();
		if (StringUtils.isEmpty(basePackages)) {
			// throw new RuntimeException("app.basePackages未配置.");
			return;
		}
		String[] packages = basePackages.split(",");
		for (int i = 0; i < packages.length; i++) {
			packages[i] = packages[i].trim();
		}
		// System.exit(0);
		// AutoConfigurationPackages.register(registry, "com.baidu");
		ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
		scanner.scan(packages);
	}

}