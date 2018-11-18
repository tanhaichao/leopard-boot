package io.leopard.boot.onum.dynamic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AssignableTypeFilter;

import io.leopard.boot.onum.dynamic.model.DynamicEnumInfo;
import io.leopard.boot.onum.dynamic.service.DynamicEnumManager;
import io.leopard.boot.util.StringUtil;
import io.leopard.lang.inum.Bnum;
import io.leopard.lang.inum.Inum;
import io.leopard.lang.inum.Snum;
import io.leopard.lang.inum.dynamic.AbstractBnum;
import io.leopard.lang.inum.dynamic.AbstractInum;
import io.leopard.lang.inum.dynamic.AbstractSnum;
import io.leopard.lang.inum.dynamic.DynamicEnum;

public class DynamicEnumScanner extends ClassPathBeanDefinitionScanner {
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

	// protected abstract void registerEnum(String enumId, String beanClassName);

	// @Override
	protected void registerEnum(String enumId, String beanClassName) {
		Class<?> enumType = this.parseEnumType(beanClassName);

		DynamicEnumInfo enumInfo = new DynamicEnumInfo();
		enumInfo.setEnumId(enumId);
		enumInfo.setBeanClassName(beanClassName);
		enumInfo.setEnumType(enumType);
		// enumList.add(enumInfo);
		DynamicEnumManager.addDynamicEnumInfo(enumInfo);

		DynamicEnumManager.addBean(enumId, beanClassName);
	}

	/**
	 * 根据className解析枚举类型.
	 * 
	 * @param beanClassName
	 * @return
	 */
	protected Class<?> parseEnumType(String beanClassName) {
		Class<?> clazz;
		try {
			clazz = Class.forName(beanClassName);
		}
		catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		if (AbstractSnum.class.isAssignableFrom(clazz)) {
			return Snum.class;
		}
		else if (AbstractInum.class.isAssignableFrom(clazz)) {
			return Inum.class;
		}
		else if (AbstractBnum.class.isAssignableFrom(clazz)) {
			return Bnum.class;
		}
		else {
			throw new IllegalArgumentException("未知动态枚举类型[" + clazz.getName() + "].");
		}
	}
}
