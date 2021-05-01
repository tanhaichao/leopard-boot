package io.leopard.boot.util;

import java.util.List;

public class TreeIdUtil {
	private static final String SEPARATOR = ":";

	public static String getStringTreeId(List<String> idList) {
		AssertUtil.assertNotNull(idList, "idList不能为null.");
		StringBuilder sb = new StringBuilder();
		for (String id : idList) {
			sb.append(id).append(SEPARATOR);
		}
		return sb.toString();
	}

	public static String getLongTreeId(List<Long> idList) {
		AssertUtil.assertNotNull(idList, "idList不能为null.");
		StringBuilder sb = new StringBuilder();
		for (Long id : idList) {
			sb.append(id).append(SEPARATOR);
		}
		return sb.toString();
	}

	public static String getIntTreeId(List<Integer> idList) {
		AssertUtil.assertNotNull(idList, "idList不能为null.");
		StringBuilder sb = new StringBuilder();
		for (Integer id : idList) {
			sb.append(id).append(SEPARATOR);
		}
		return sb.toString();
	}
}
