package io.leopard.boot.util;

import java.util.ArrayList;
import java.util.List;
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
		sourceList.removeAll(fieldValueList);
		return sourceList;
	}

}
