package io.leopard.boot;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

public class LeopardBeanNameGenerator extends AnnotationBeanNameGenerator {
	@Override
	protected String buildDefaultBeanName(BeanDefinition definition) {
		// 全限定类名
		String beanName = definition.getBeanClassName();
		return beanName;
	}

}
