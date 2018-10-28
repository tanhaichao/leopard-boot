package io.leopard.boot.requestbody;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import io.leopard.json.Json;

public class RequestBodyHttpServletRequestWrapper extends HttpServletRequestWrapper {

	private Map<String, Object> requestBody;

	public RequestBodyHttpServletRequestWrapper(HttpServletRequest request, String requestBody) {
		super(request);
		this.requestBody = Json.toMap(requestBody);

		request.setAttribute("requestBody", this.requestBody);
	}

	@Override
	public String getParameter(String name) {
		Object value = requestBody.get(name);
		// System.err.println("RequestBodyHttpServletRequestWrapper getParameter:" + name + " value:" + value);
		if (value != null) {
			return toString(name, value);
		}

		return super.getParameter(name);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String[] getParameterValues(String name) {
		Object value = requestBody.get(name);
		System.err.println("getParameterValues name:" + name + " value:" + value);
		if (value != null) {
			if (value instanceof List) {
				return toStrings(name, (List) value);
			}
			else {
				String element = toString(name, value);
				return new String[] { element };
			}
		}

		return super.getParameterValues(name);
	}

	@SuppressWarnings("rawtypes")
	protected String[] toStrings(String name, List list) {
		String[] values = new String[list.size()];
		int index = 0;
		for (Object element : list) {
			String str = toString(name, element);
			values[index] = str;
			index++;
		}
		return values;
	}

	public static String toString(String name, Object value) {
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
		else {
			String json = Json.toJson(value);
			System.err.println("getParameterForRequestBody name:" + name + " json:" + json);
			return json;
		}
	}
}
