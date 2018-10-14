package io.leopard.boot.onum.dynamic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AssignableTypeFilter;

import io.leopard.boot.util.StringUtil;
import io.leopard.lang.inum.dynamic.DynamicEnum;

public abstract class DynamicEnumScanner extends ClassPathBeanDefinitionScanner {
	protected Log logger = LogFactory.getLog(this.getClass());

	public DynamicEnumScanner(BeanDefinitionRegistry registry) {
		super(registry);
	}

	@Override
	public void registerDefaultFilters() {
		this.addIncludeFilter(new AssignableTypeFilter(DynamicEnum.class));
	}

	@Override
	public boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
		String beanClassName = beanDefinition.getBeanClassName();
		String simpleName = beanClassName.substring(beanClassName.lastIndexOf(".") + 1);
		String enumId = StringUtil.firstCharToLowerCase(simpleName);
		registerEnum(enumId, beanClassName);
		logger.info("beanDefinition..." + beanDefinition.getBeanClassName() + " enumId:" + enumId);
		return false;// 不注册Spring Bean
	}

	protected abstract void registerEnum(String enumId, String beanClassName);

}
