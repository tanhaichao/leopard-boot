package io.leopard.boot.admin.accesslog;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;

import io.leopard.boot.admin.accesslog.model.Pair;

public class AccessLogUtil {

	public static String getRequestBody(HttpServletRequest request) throws IOException {
		ServletInputStream input = request.getInputStream();
		byte[] bytes = IOUtils.toByteArray(input);
		input.close();
		return new String(bytes);
	}

	public static List<Pair> getParameters(HttpServletRequest request) {
		List<Pair> paramList = new ArrayList<Pair>();
		Map<String, String[]> paramMap = request.getParameterMap();
		for (Entry<String, String[]> entry : paramMap.entrySet()) {
			String name = entry.getKey();
			String[] values = entry.getValue();
			if (values == null || values.length == 0) {
				paramList.add(new Pair(name, null));
				continue;
			}
			for (String value : values) {
				// if (value != null && value.length() > 10000) {
				// continue;
				// }
				paramList.add(new Pair(name, value));
			}
		}
		return paramList;
	}

	public static String toExceptionString(Exception exception) {
		if (exception == null) {
			return null;
		}
		// exception.printStackTrace();
		StringWriter sw = new StringWriter();
		exception.printStackTrace(new PrintWriter(sw));
		return sw.getBuffer().toString();
	}
}
