package io.leopard.boot.freemarker.method;

import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;

import freemarker.template.TemplateModelException;

/**
 * 平均时间计算
 * 
 * @author 阿海
 *
 */

public class TimeTemplateMethod extends AbstractTemplateMethod {

	@Override
	public Object execute(HttpServletRequest request, Object... args) throws TemplateModelException {
		double time = (Double) args[0];
		if (time <= 0) {
			return "-";
		}
		String str;
		if (time < 0.01) {
			str = new DecimalFormat("0.000").format(time);
		}
		else if (time < 0.1) {
			str = new DecimalFormat("0.00").format(time);
		}
		else if (time < 1) {
			str = new DecimalFormat("0.0").format(time);
		}
		else if (time < 10) {
			str = new DecimalFormat("0.0").format(time);
		}
		else {
			str = new DecimalFormat("0").format(time);
		}
		return str + "ms";

	}

	@Override
	public String getKey() {
		return "time";
	}

}
