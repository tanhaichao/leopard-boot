package io.leopard.boot.converter;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import io.leopard.boot.util.DateTime;
import io.leopard.boot.util.DateUtil;

@Component
public class DateConverter implements Converter<String, Date> {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Override
	public Date convert(String source) {
		logger.info("MonthConverter source:" + source);
		if (StringUtils.isEmpty(source)) {
			return null;
		}
		if ("undefined".equals(source)) {
			return null;
		}
		else if (DateTime.isDateTime(source)) {
			// if ("1970-01-01 08:00:00".equals(source)) {
			// return new Date(1);
			// }
			// else {
			return DateUtil.toDate(source);
			// }
		}
		else if (DateTime.isDate(source)) {
			return DateUtil.toDate(source + " 00:00:00");
		}
		else {
			long time = Long.parseLong(source);
			// if (time == 0) {
			// return new Date(1);
			// }
			// else {
			return new Date(time);
			// throw new IllegalArgumentException("非法时间戳[" + time + "].");
			// }
		}
	}
}
