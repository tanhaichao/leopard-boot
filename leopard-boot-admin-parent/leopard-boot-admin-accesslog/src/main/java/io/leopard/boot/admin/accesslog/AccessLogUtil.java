package io.leopard.boot.admin.accesslog;

import java.io.PrintWriter;
import java.io.StringWriter;

public class AccessLogUtil {

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
