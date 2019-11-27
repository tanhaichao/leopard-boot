package io.leopard.boot.xparam;

import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.servlet.HandlerMapping;

public class XParamUtil {

	public static boolean isFalse(String value) {
		return CustomBooleanEditor.VALUE_FALSE.equalsIgnoreCase(value)//
				|| CustomBooleanEditor.VALUE_NO.equalsIgnoreCase(value)//
				|| CustomBooleanEditor.VALUE_0.equals(value)//
				|| CustomBooleanEditor.VALUE_OFF.equalsIgnoreCase(value);
	}

	public static boolean isTrue(String value) {
		return CustomBooleanEditor.VALUE_TRUE.equalsIgnoreCase(value)//
				|| CustomBooleanEditor.VALUE_YES.equalsIgnoreCase(value)//
				|| CustomBooleanEditor.VALUE_1.equals(value)//
				|| CustomBooleanEditor.VALUE_ON.equalsIgnoreCase(value);
	}

	public static int toInt(String str) {
		if (str == null || str.length() == 0) {
			return 0;
		}
		try {
			return Integer.parseInt(str);
		}
		catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 获取cookie的值</br>
	 * 
	 * @param name cookie名称
	 * @param request http请求
	 * @return cookie值
	 */
	public static String getCookie(String name, HttpServletRequest request) {
		if (name == null || name.length() == 0) {
			throw new IllegalArgumentException("cookie名称不能为空.");
		}
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}
		for (int i = 0; i < cookies.length; i++) {
			if (name.equalsIgnoreCase(cookies[i].getName())) {
				return cookies[i].getValue();
			}
		}
		return null;
	}

	public static String getProxyIp(HttpServletRequest request) {
		String proxyIp = request.getHeader("X-Real-IP");
		if (proxyIp == null) {
			proxyIp = request.getHeader("RealIP");
		}
		if (proxyIp == null) {
			proxyIp = request.getRemoteAddr();
		}
		return proxyIp;
	}

	public static String getPathVariableValue(String variableName, HttpServletRequest request, MethodParameter parameter) throws MissingPathVariableException {
		@SuppressWarnings("unchecked")
		Map<String, String> uriTemplateVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		String projectId = uriTemplateVars.get(variableName);
		if (StringUtils.isEmpty(projectId)) {
			// throw new MissingPathVariableException(variableName, parameter);
			return null;
		}
		return projectId;
	}

	static ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

	public static String[] getParameterNames(MethodParameter parameter) {
		Method method = parameter.getMethod();
		// ParameterNameDiscoverer parameterNameDiscoverer = null;
		return parameterNameDiscoverer.getParameterNames(method);
	}

	public static String[] getParameterNames(Method method) {
		return parameterNameDiscoverer.getParameterNames(method);
	}

	public static Set<String> getParameterNameSet(Method method) {
		String[] names = parameterNameDiscoverer.getParameterNames(method);
		Set<String> nameSet = new LinkedHashSet<>();
		for (String name : names) {
			nameSet.add(name);
		}
		return nameSet;
	}

}
