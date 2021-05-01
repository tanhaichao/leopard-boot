package io.leopard.boot.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * ID树
 * 
 * @author 谭海潮
 *
 */
public class TreeIdUtil {
	private static final String SEPARATOR = ":";

	public static String getTreeId(String parentTreeId, String id) {
		AssertUtil.assertNotEmpty(id, "id不能为空.");
		if (StringUtils.isEmpty(parentTreeId)) {
			return id + SEPARATOR;
		}
		else {
			return parentTreeId + id + SEPARATOR;
		}
	}

	public static String getTreeId(String parentTreeId, long id) {
		AssertUtil.assertNotEmpty(id, "id不能为空.");
		if (StringUtils.isEmpty(parentTreeId)) {
			return id + SEPARATOR;
		}
		else {
			return parentTreeId + id + SEPARATOR;
		}
	}

	public static String getTreeId(String parentTreeId, int id) {
		AssertUtil.assertNotEmpty(id, "id不能为空.");
		if (StringUtils.isEmpty(parentTreeId)) {
			return id + SEPARATOR;
		}
		else {
			return parentTreeId + id + SEPARATOR;
		}
	}

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
