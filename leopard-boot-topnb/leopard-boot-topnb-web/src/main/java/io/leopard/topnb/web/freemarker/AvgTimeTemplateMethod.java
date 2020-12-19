package io.leopard.topnb.web.freemarker;

import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import freemarker.template.TemplateModelException;
import io.leopard.boot.freemarker.method.AbstractTemplateMethod;

/**
 * 平均时间计算
 * 
 * @author 阿海
 *
 */
@Component
public class AvgTimeTemplateMethod extends AbstractTemplateMethod {

	@Override
	public Object execute(HttpServletRequest request, Object... args) throws TemplateModelException {
		long count = (Long) args[0];
		double time = (Double) args[1];

		return this.avgTime(count, time);
	}

	protected String avgTime(long count, double time) {
		if (count <= 0 || time <= 0) {
			// throw new TemplateModelException("count不能小于0");
			return "-";
		}
		// System.out.println("count:" + count + " time:" + time);
		double avg = time / count;
		String avgTime;
		if (avg < 0.01) {
			avgTime = new DecimalFormat("0.000").format(avg);
		}
		else if (avg < 0.1) {
			avgTime = new DecimalFormat("0.00").format(avg);
		}
		else if (avg < 1) {
			avgTime = new DecimalFormat("0.0").format(avg);
		}
		else if (avg < 10) {
			avgTime = new DecimalFormat("0.0").format(avg);
		}
		else {
			// System.out.println("avg:" + avg);
			avgTime = new DecimalFormat("0").format(avg);
			// return Double.parseDouble(avgTime) + "ms";
		}
		// System.out.println("avgTime:" + avgTime);
		return avgTime + "ms";
	}

	@Override
	public String getKey() {
		return "avgTime";
	}

}
