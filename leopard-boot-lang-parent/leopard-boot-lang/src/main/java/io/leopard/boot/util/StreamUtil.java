package io.leopard.boot.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 流操作
 * 
 * @author 谭海潮
 *
 */
public class StreamUtil {

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
		Set<R> fieldValueSet = list.stream().map(mapper).collect(Collectors.toSet());
		boolean unique = list.size() == fieldValueSet.size();
		if (!unique) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 获取某个属性列表
	 * 
	 * @param list
	 * @param mapper
	 * @return
	 */
	public static <R, T> List<R> getFieldValueList(List<T> list, Function<? super T, ? extends R> mapper) {
		if (list == null) {
			return new ArrayList<>();
		}
		List<R> fieldValueList = list.stream().map(mapper).collect(Collectors.toList());
		return fieldValueList;
	}

	/**
	 * List转Map
	 * 
	 * @param list
	 * @param mapper
	 * @return
	 */
	public static <KEY, BEAN> Map<KEY, BEAN> toMap(List<BEAN> list, Function<? super BEAN, ? extends KEY> keyMapper) {
		// Map<KEY, List<BEAN>> map = list.stream().collect(Collectors.groupingBy(classifier));
		// Function<? super T, ? extends K> keyMapper,
		// Function<? super T, ? extends U> valueMapper,
		// BinaryOperator<U> mergeFunction
		if (list == null) {
			return new LinkedHashMap<KEY, BEAN>();
		}
		Map<KEY, BEAN> map = list.stream().collect(Collectors.toMap(keyMapper, key -> key, (bean1, bean2) -> bean1));
		return map;
	}

	// public static <R, T> List<R> getNotEmptyFieldValueList(List<T> list, Function<? super T, ? extends R> mapper, List<R> sourceListList) {
	// List<R> fieldValueList = getDeletedFieldValueList(list, mapper, sourceListList);
	// // 删除空记录
	// Iterator<?> iterator = list.iterator();
	// while (iterator.hasNext()) {
	// Object next = iterator.next();
	// if (next == null || isEmpty(next)) {
	// iterator.remove();
	// }
	// }
	// return fieldValueList;
	// }
	//
	// protected static boolean isEmpty(Object element) {
	// if (element instanceof String) {
	// String str = (String) element;
	// if (str.length() == 0) {
	// return true;
	// }
	// }
	// else if (element instanceof Integer) {
	// int number = (int) element;
	// if (number == 0) {
	// return true;
	// }
	// }
	// else if (element instanceof Long) {
	// long number = (long) element;
	// if (number == 0) {
	// return true;
	// }
	// }
	// return false;
	// }

	/**
	 * 获取已删除的ID列表
	 * 
	 * @param list
	 * @param mapper
	 * @param sourceList
	 * @return
	 */
	public static <R, T> List<R> getDeletedFieldValueList(List<T> list, Function<? super T, ? extends R> mapper, List<R> sourceList) {
		if (sourceList == null || sourceList.isEmpty()) {
			return sourceList;
		}
		List<R> fieldValueList = getFieldValueList(list, mapper);
		List<R> sourceList2 = new ArrayList<>(sourceList);
		sourceList2.removeAll(fieldValueList);
		return sourceList2;
	}

}
