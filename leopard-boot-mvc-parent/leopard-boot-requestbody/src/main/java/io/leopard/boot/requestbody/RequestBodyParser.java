package io.leopard.boot.requestbody;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

import io.leopard.json.Json;

public class RequestBodyParser {

	public static String getParameter(HttpServletRequest request, String name) {
		String value = request.getParameter(name);
		if (value == null) {
			// TODO 临时禁止
			// value = getParameterForRequestBody(request, name);
		}
		return value;
	}

	private static Map<String, Object> getRequestBody(HttpServletRequest request) {
		@SuppressWarnings("unchecked")

		Map<String, Object> requestBody = (Map<String, Object>) request.getAttribute("requestBody");
		if (requestBody == null) {
			String requestBodyJson = request.getParameter("requestBody");
			// logger.info("uri:" + request.getRequestURI() + " requestBodyJson:" + requestBodyJson);
			if (StringUtils.isEmpty(requestBodyJson)) {
				return null;
			}
			requestBody = Json.toMap(requestBodyJson);
			request.setAttribute("requestBody", requestBody);
		}
		return requestBody;
	}

	private static String getParameterForRequestBody(HttpServletRequest request, String name) {
		Map<String, Object> requestBody = getRequestBody(request);
		if (requestBody == null) {
			return null;
		}
		Object value = requestBody.get(name);

		if (value == null) {
			return null;
		}
		if (value instanceof String) {
			return (String) value;
		}
		else if (value instanceof Integer) {
			return Integer.toString((Integer) value);
		}
		else if (value instanceof Long) {
			return Long.toString((Long) value);
		}
		else if (value instanceof Float) {
			return Float.toString((Float) value);
		}
		else if (value instanceof Double) {
			return Double.toString((Double) value);
		}
		else if (value instanceof Date) {
			return ((Date) value).getTime() + "";
		}
		else if (value instanceof Boolean) {
			return value + "";
		}

		String json = Json.toJson(value);
		// System.err.println("getParameterForRequestBody name:" + name + " json:" + json);
		return json;
	}
}
