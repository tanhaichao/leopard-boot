package io.leopard.spring;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
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
	private static final List<ServerEnv> envList = new ArrayList<>();// 这里应该只有一个环境,或者为空（dev）

	public static void setApplicationContext(ApplicationContext context) {
		applicationContext = context;
		String[] profiles = context.getEnvironment().getActiveProfiles();

		if (profiles != null) {
			for (String profile : profiles) {
				profileSet.add(profile);
				ServerEnv env = EnumUtil.get(profile, ServerEnv.class);
				if (env != null) {
					envList.add(env);
				}
			}
		}
		if (envList.size() > 1) {
			throw new RuntimeException("出现多个环境[" + envList + "]，同时只允许一种环境[dev|test|pre|prod]。");
		}
	}

	public static Set<String> getProfileSet() {
		return profileSet;
	}

	public static ServerEnv getEnv() {
		if (envList.isEmpty()) {
			return ServerEnv.DEV;
		}
		return envList.get(0);
	}

	/**
	 * 是否开发环境
	 */
	public static boolean isDevEnv() {
		return envList.isEmpty() || envList.contains(ServerEnv.DEV);
	}

	/**
	 * 是否测试环境
	 * 
	 * @return
	 */
	public static boolean isTestEnv() {
		return envList.contains(ServerEnv.TEST);
	}

	/**
	 * 是否预发布环境
	 * 
	 * @return
	 */
	public static boolean isPreEnv() {
		return envList.contains(ServerEnv.PRE);
	}

	/**
	 * 是否生产环境
	 * 
	 * @return
	 */
	public static boolean isProdEnv() {
		return envList.contains(ServerEnv.PROD);
	}

	/**
	 * 是否定时器环境
	 * 
	 * @return
	 */
	public static boolean isJobEnv() {
		return envList.contains(ServerEnv.JOB);
	}

	/**
	 * 是否生产环境或pre环境
	 * 
	 * @return
	 */
	public static boolean isProdOrPreEnv() {
		return envList.contains(ServerEnv.PROD) || envList.contains(ServerEnv.PRE);
	}
}
