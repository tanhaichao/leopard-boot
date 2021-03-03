package io.leopard.lang.datatype;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 精确到月份的日期(如:2013-01)
 * 
 * @author 阿海
 * 
 */
public class Month extends Date {

	private static final long serialVersionUID = 1L;

	public Month() {
		this(System.currentTimeMillis());
		// new Exception().printStackTrace();
	}

	public Month(java.util.Date date) {
		this(date.getTime());
	}

	public Month(String datetime) {
		this(getMillis(datetime));
	}

	public Month(long time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		this.setTime(cal.getTimeInMillis());
	}

	protected static long getMillis(String datetime) {
		if (datetime.length() == 7) {
			return DateTime.getTimestamp(datetime + "-01 00:00:00");
		}
		else if (DateTime.isDateTime(datetime)) {
			return DateTime.getTimestamp(datetime);
		}
		else if (DateTime.isDate(datetime)) {
			return DateTime.getTimestamp(datetime + " 00:00:00");
		}
		else if (isTimestamp(datetime)) {
			return Long.parseLong(datetime);
		}
		else {
			throw new IllegalArgumentException("非法参数[" + datetime + "].");
		}
	}

	// 1596211200000

	private static final String IS_TIMESTAMP_REGEX = "^1[0-9]{12}$";

	/**
	 * 判断字符串是否为时间戳
	 */
	private static boolean isTimestamp(final String datetime) {
		if (datetime == null || datetime.length() == 0) {
			return false;
		}
		return datetime.matches(IS_TIMESTAMP_REGEX);
	}

	// private static final SimpleDateFormat GET_TIME_FORMAT = new SimpleDateFormat("yyyy-MM");

	@Override
	public String toString() {
		return new SimpleDateFormat("yyyy-MM").format(this);
	}

	public static Month addMonth(final int month) {
		Calendar cal = Calendar.getInstance();
		// cal.setTime(dat);
		cal.add(Calendar.MONTH, month);
		long time = cal.getTimeInMillis();
		return new Month(time);
	}
}
