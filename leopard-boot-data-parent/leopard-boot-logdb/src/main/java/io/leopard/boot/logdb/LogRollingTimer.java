package io.leopard.boot.logdb;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.helpers.LogLog;

import io.leopard.boot.timer.PerDayPeriod;
import io.leopard.boot.timer.Period;
import io.leopard.boot.timer.SimpleTimer;

public class LogRollingTimer {

	private static Map<LogRollOver, Boolean> status = new ConcurrentHashMap<LogRollOver, Boolean>();

	static {
		Period period;
		period = new PerDayPeriod(0, 0, 10);
		// period = new SecondPeriod(10);
		new SimpleTimer(period) {
			@Override
			public void start() {
				rollOver();
			}

		}.run();
	}

	public static void rollOver() {
		Set<LogRollOver> keySet = status.keySet();
		for (LogRollOver logRollOver : keySet) {
			try {
				logRollOver.autoRollOver();
			}
			catch (Exception e) {
				LogLog.error("DailyAutoRollingFileAppender init failed." + logRollOver.getFilename(), e);
			}
		}
	}

	public static void start(LogRollOver logRollOver) {
		status.put(logRollOver, true);
	}

	public static void stop(LogRollOver logRollOver) {
		status.remove(logRollOver);
	}
}
