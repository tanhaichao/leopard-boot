package io.leopard.spring;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.context.ApplicationContext;

import io.leopard.lang.inum.EnumUtil;

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

		Set<ServerEnv> envSet = new LinkedHashSet<>();// 这里应该只有一个环境,或者为空（dev）

		if (profiles != null) {
			for (String profile : profiles) {
				profileSet.add(profile);
				ServerEnv env = EnumUtil.get(profile, ServerEnv.class);
				if (env != null) {
					envSet.add(env);
				}
			}
		}
		if (envSet.size() > 1) {
			throw new RuntimeException("出现多个环境[" + envSet + "]，同时只允许一种环境[dev|test|pre|prod]。");
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
