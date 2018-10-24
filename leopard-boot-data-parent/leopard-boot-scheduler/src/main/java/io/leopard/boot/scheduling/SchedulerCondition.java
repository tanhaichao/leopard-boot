package io.leopard.boot.scheduling;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class SchedulerCondition implements Condition {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		List<String> beanList = this.findTimerBeanList(context.getRegistry());
		if (beanList.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * 查找所有Timer的Bean名称
	 * 
	 * @param context
	 * @return
	 */
	protected List<String> findTimerBeanList(BeanDefinitionRegistry registry) {
		List<String> beanList = new ArrayList<>();
		String[] beanNames = registry.getBeanDefinitionNames();
		for (String beanName : beanNames) {
			if (beanName.endsWith("Timer")) {
				beanList.add(beanName);
			}
		}
		return beanList;
	}

}
