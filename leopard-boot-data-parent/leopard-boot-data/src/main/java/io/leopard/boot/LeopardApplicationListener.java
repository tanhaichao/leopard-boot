package io.leopard.boot;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import io.leopard.spring.LeopardBeanFactoryAware;

@Order(Ordered.LOWEST_PRECEDENCE - 1000) // 保证在ConfigFileApplicationListener后面初始化
public class LeopardApplicationListener implements ApplicationListener<ApplicationEvent> {

	private static String basePackages;

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		// System.err.println("LeopardApplicationListener onApplicationEvent:" + event.getClass().getName());
		if (event instanceof ApplicationEnvironmentPreparedEvent) {
			onApplicationEnvironmentPreparedEvent((ApplicationEnvironmentPreparedEvent) event);
		}
		else if (event instanceof ApplicationPreparedEvent) {
			ConfigurableApplicationContext context = ((ApplicationPreparedEvent) event).getApplicationContext();
			LeopardBeanFactoryAware.setBeanFactory(context);
		}
	}

	private void onApplicationEnvironmentPreparedEvent(ApplicationEnvironmentPreparedEvent event) {
		basePackages = event.getEnvironment().getProperty("app.basePackages");
		// System.exit(0);
		// EnvironmentPostProcessor postProcessor = null;
		// postProcessor.postProcessEnvironment(event.getEnvironment(), event.getSpringApplication());
	}

	public static String getBasePackages() {
		return basePackages;
	}

}
