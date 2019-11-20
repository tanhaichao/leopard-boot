package io.leopard.boot.freemarker.util;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import freemarker.ext.beans.StringModel;
import freemarker.template.SimpleScalar;

/**
 * FreeMarker指令工具类
 * 
 * @author 谭海潮
 *
 */
public class DirectiveUtil {

	/**
	 * 获取指令的动态参数
	 * 
	 * @param params 指令传入的参数
	 * @param excludeNames 要排除的参数名称列表
	 * @return
	 */
	public static Map<String, Object> getDynamicArgs(Map<String, Object> params, String... excludeNames) {
		Set<String> excludeNameSet = new HashSet<>();// TODO 需要按照原来的顺序？
		for (String excludeName : excludeNames) {
			excludeNameSet.add(excludeName);
		}
		return getDynamicArgs(params, excludeNameSet);
	}

	public static Map<String, Object> getDynamicArgs(Map<String, Object> params, Set<String> excludeNameSet) {
		Map<String, Object> dynamicArgs = new LinkedHashMap<>();
		for (Entry<String, Object> entry : params.entrySet()) {
			String name = entry.getKey();
			if (!excludeNameSet.contains(name)) {
				Object value = entry.getValue();
				dynamicArgs.put(name, toObject(value));
			}
		}
		return dynamicArgs;
	}

	public static Object toObject(Object obj) {
		if (obj == null) {
			return null;
		}
		else if (obj instanceof SimpleScalar) {
			return ((SimpleScalar) obj).getAsString();
		}
		else if (obj instanceof StringModel) {
			return ((StringModel) obj).getWrappedObject();
		}
		else {
			throw new IllegalArgumentException("未知参数类型[" + obj.getClass().getName() + "].");
		}
	}

}
