package io.leopard.boot;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

public class LeopardBeanNameGenerator extends AnnotationBeanNameGenerator {

	private static boolean isFullName = false;

	public static boolean isFullName() {
		return isFullName;
	}

	public static void setFullName(boolean isFullName) {
		LeopardBeanNameGenerator.isFullName = isFullName;
	}

	@Override
	protected String buildDefaultBeanName(BeanDefinition definition) {
		if (isFullName) {
			// 全限定类名
			String beanName = definition.getBeanClassName();
			return beanName;
		}
		else {
			return super.buildDefaultBeanName(definition);
		}
	}

}
