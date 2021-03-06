package io.leopard.boot.freemarker.method;

import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import io.leopard.boot.freemarker.RequestHolder;
import io.leopard.boot.freemarker.TemplateVariable;

/**
 * 替换URL参数
 * 
 * @author 谭海潮
 *
 */
@Component
public class ReplaceParamTemplateMethod implements TemplateMethodModelEx, TemplateVariable {

	@Override
	public Object exec(@SuppressWarnings("rawtypes") List args) throws TemplateModelException {
		String param = args.get(0).toString();
		HttpServletRequest request = RequestHolder.getRequest();
		return replaceParam(param, request);
	}

	private static final Pattern pattern = Pattern.compile("([a-zA-Z0-9_]+)=([a-zA-Z0-9_\\.:/]+)");

	public static String replaceParam(String param, HttpServletRequest request) {
		// String queryString = request.getQueryString();
		// if (StringUtils.isEmpty(queryString)) {
		// return "?" + param;
		// }
		Matcher m = pattern.matcher(param);
		if (!m.find()) {
			throw new IllegalArgumentException("非法参数[" + param + "].");
		}
		String key = m.group(1);
		String value = m.group(2);
		return getQueryString(request, key, value);
	}

	protected static String getQueryString(HttpServletRequest request, String key, String value) {
		// url = url.substring(0, url.indexOf("?") == -1 ? url.length() : url.indexOf("?"));
		StringBuilder sb = new StringBuilder();
		Enumeration<String> e = request.getParameterNames();

		boolean hasReplace = false;
		while (e.hasMoreElements()) {
			String name = e.nextElement();
			String value2;
			if (name.equals(key)) {
				value2 = value;
				hasReplace = true;
			}
			else {
				value2 = request.getParameter(name);
			}
			if (sb.length() > 0) {
				sb.append("&");
			}
			// FIXME 参数名称和参数值合法性判断
			sb.append(name).append("=").append(value2);
		}
		if (!hasReplace) {
			if (sb.length() > 0) {
				sb.append("&");
			}
			sb.append(key).append("=").append(value);
		}
		return "?" + sb.toString();
	}

	@Override
	public String getKey() {
		return "replaceParam";
	}
}