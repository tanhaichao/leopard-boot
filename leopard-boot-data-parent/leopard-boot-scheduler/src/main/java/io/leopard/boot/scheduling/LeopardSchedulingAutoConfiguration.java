package io.leopard.boot.scheduling;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Role;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.config.TaskManagementConfigUtils;

public class LeopardSchedulingAutoConfiguration {

	@Conditional(HasTimerCondition.class)
	@Bean(name = TaskManagementConfigUtils.SCHEDULED_ANNOTATION_PROCESSOR_BEAN_NAME)
	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
	public ScheduledAnnotationBeanPostProcessor scheduledAnnotationProcessor() {
		// new Exception("new ScheduledAnnotationBeanPostProcessor").printStackTrace();
		return new ScheduledAnnotationBeanPostProcessor();
	}

}
