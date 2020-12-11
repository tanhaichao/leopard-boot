package io.leopard.spring;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.context.ApplicationContext;

/**
 * 环境变量
 * 
 * @author 谭海潮
 *
 */
public class SpringEnvUtil {
	protected static ApplicationContext applicationContext;

	private static final Set<String> profileSet = new LinkedHashSet<>();

	public static void setApplicationContext(ApplicationContext context) {
		applicationContext = context;
		String[] profiles = context.getEnvironment().getActiveProfiles();

		if (profiles != null) {
			for (String profile : profiles) {
				profileSet.add(profile);
			}
		}
	}

	/**
	 * 是否开发环境
	 */
	public static boolean isDev() {
		return profileSet.isEmpty() || profileSet.contains("dev") || !(isTest() || isPre() || isProd());
	}

	/**
	 * 是否测试环境
	 * 
	 * @return
	 */
	public static boolean isTest() {
		return profileSet.contains("test");
	}

	/**
	 * 是否生产环境
	 * 
	 * @return
	 */
	public static boolean isProd() {
		return profileSet.contains("prod");
	}

	/**
	 * 是否预发布环境
	 * 
	 * @return
	 */
	public static boolean isPre() {
		return profileSet.contains("pre");
	}
}
