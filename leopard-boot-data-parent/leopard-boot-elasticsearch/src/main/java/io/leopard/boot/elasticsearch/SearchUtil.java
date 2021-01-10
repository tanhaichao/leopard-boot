package io.leopard.boot.elasticsearch;

import org.apache.commons.lang3.StringUtils;

public class SearchUtil {

	public static String filterKeyword(String keyword) {
		keyword = StringUtils.trim(keyword);
		if (StringUtils.isBlank(keyword)) {
			return "";
		}
		keyword = SearchUtil.filter(keyword);
		keyword = keyword.replace(" ", "");
		keyword = keyword.trim();
		return keyword;
	}

	public static String filter(String s) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			// These characters are part of the query syntax and must be escaped
			if (c == '\\' || c == '+' || c == '-' || c == '!' || c == '(' || c == ')' || c == ':' || c == '^' || c == '[' || c == ']' || c == '\"' || c == '{' || c == '}' || c == '~' || c == '*'
					|| c == '?' || c == '|' || c == '&' || c == '/' || c == '%') {
				// sb.append('\\');
			}
			else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
}
