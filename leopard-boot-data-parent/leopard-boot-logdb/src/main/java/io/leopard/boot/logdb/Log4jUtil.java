package io.leopard.boot.logdb;

import org.apache.commons.logging.Log;

public class Log4jUtil {

	private static LogDao logDao;

	protected static LogDao getLogDao() {
		if (logDao != null) {
			return logDao;
		}

		boolean isEnableLog4j = isEnableLog4j();
		if (isEnableLog4j) {
			logDao = new LogDaoLog4jImpl();
		}
		else {
			logDao = new LogDaoInterfaceImpl();
		}

		return logDao;
	}

	private static boolean isEnableLog4j() {
		try {
			Class.forName("org.apache.log4j.Logger");
			logDao = new LogDaoLog4jImpl();
		}
		catch (ClassNotFoundException e) {
			return false;
		}

		Class<?> clazz;
		try {
			clazz = Class.forName("org.apache.log4j.FileAppender");
		}
		catch (ClassNotFoundException e) {
			return false;
		}
		try {
			clazz.getDeclaredField("fileName");
		}
		catch (NoSuchFieldException e) {
			return false;
		}
		catch (SecurityException e) {
			return false;
		}
		return true;
	}

	public static Log getLogger(String name, Level level, String filename) {
		return getLogDao().getLogger(name, level, filename);
	}

	public static Log getLogger(String name, Level level, String filename, boolean bufferedIO) {
		return getLogDao().getLogger(name, level, filename, bufferedIO);
	}

	public static Log getLogger(Class<?> clazz, Level level, String filename) {
		return getLogDao().getLogger(clazz, level, filename);
	}

	public static Log getLogger(Class<?> clazz, Level level, String filename, boolean bufferedIO) {
		return getLogDao().getLogger(clazz, level, filename, bufferedIO);
	}

	/**
	 * 禁止类初始化log.
	 */
	public static void disabledBeanLog() {
		getLogDao().disabledBeanLog();
	}

	/**
	 * 禁止公用包log.
	 */
	public static void disabledCommonsLog() {
		getLogDao().disabledCommonsLog();
	}
}
