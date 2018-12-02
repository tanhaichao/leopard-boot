package io.leopard.boot.util;

import java.util.List;

/**
 * 树ID生成
 * 
 * @author 谭海潮
 *
 */
public class TreeIdUtil {

	public static String genTreeId(List<Long> idList) {
		StringBuilder sb = new StringBuilder();
		for (long id : idList) {
			String fixedLengthId = getFixedLengthId(id, 5);
			sb.append(fixedLengthId);
		}
		return sb.toString();
	}

	protected static String getFixedLengthId(long id, int len) {
		return id + "".substring(0, len);
	}
}
