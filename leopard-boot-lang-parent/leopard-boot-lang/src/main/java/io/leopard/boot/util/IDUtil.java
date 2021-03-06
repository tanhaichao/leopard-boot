package io.leopard.boot.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * ID
 * 
 * @author 谭海潮
 *
 */
public class IDUtil {

	/**
	 * 获取ID属性列表
	 * 
	 * @param list
	 * @param mapper
	 * @return
	 */
	public static <R, T> List<R> getIdList(List<T> list, Function<? super T, ? extends R> mapper) {
		if (list == null) {
			return new ArrayList<>();
		}
		List<R> idList = list.stream().map(mapper).collect(Collectors.toList());
		return idList;
	}

	/**
	 * 检查元素是否唯一(忽略空值)
	 * 
	 * @param list
	 * @param mapper
	 * @param message 异常提示信息
	 * @return
	 */
	public static <R, T> void checkUniqueIdIgnoreEmpty(List<T> list, Function<? super T, ? extends R> mapper, String message) {
		if (list == null || list.isEmpty()) {
			return;
		}
		List<R> idList = list.stream().map(mapper).collect(Collectors.toList());// ID列表
		Iterator<R> iterator = idList.iterator();
		while (iterator.hasNext()) {// 过滤空ID
			R id = iterator.next();
			if (isEmptyId(id)) {
				iterator.remove();
			}
		}
		Set<R> idSet = new HashSet<>(idList);
		boolean unique = idList.size() == idSet.size();
		if (!unique) {
			// System.err.println("idList:" + idList);
			// System.err.println("idSet:" + idSet);
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 是否空ID
	 * 
	 * @param id
	 * @return
	 */
	public static boolean isEmptyId(Object id) {
		if (id == null) {
			return true;
		}
		if (id instanceof String) {
			return ((String) id).length() == 0;
		}
		else if (id instanceof Integer) {
			return ((Integer) id) <= 0;
		}
		else if (id instanceof Long) {
			return ((Long) id) <= 0;
		}
		else {
			throw new IllegalArgumentException("未知ID类型[" + id.getClass().getName() + "].");
		}
	}
}
