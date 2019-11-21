package io.leopard.boot.freemarker.method;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import freemarker.template.TemplateModelException;

/**
 * 多久以前的时间.
 * 
 * @author 阿海
 *
 */
@Component
public class TimeAgoTemplateMethod extends AbstractTemplateMethod {

	@Override
	public Object execute(HttpServletRequest request, Object... args) throws TemplateModelException {
		Date datetime = (Date) args[0];
		long time = datetime.getTime() / 1000;
		long current = System.currentTimeMillis() / 1000;
		long second = current - time;
		long minute = second / 60;
		if (minute <= 0) {
			return "刚刚";
		}
		if (minute < 60) {
			return minute + "分钟前";
		}
		long hour = minute / 60;
		if (hour < 24) {
			return hour + "小时前";
		}
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(datetime);
	}

	@Override
	public String getKey() {
		return "timeAgo";
	}

}
