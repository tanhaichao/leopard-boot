package io.leopard.web.mvc.json;

import java.util.ArrayList;
import java.util.List;

import io.leopard.spring.LeopardBeanFactoryAware;

public class BeanFinderImpl implements BeanFinder {

	private List<BeanFinder> beanFinderList = new ArrayList<>();

	public void addBeanFinder(BeanFinder beanFinder) {
		this.beanFinderList.add(beanFinder);
	}

	@Override
	public Object findBean(Class<?> type) {
		for (BeanFinder beanFinder : beanFinderList) {
			Object bean = beanFinder.findBean(type);
			if (bean != null) {
				return bean;
			}
		}
		return LeopardBeanFactoryAware.getBeanFactory().getBean(type);
	}
}
