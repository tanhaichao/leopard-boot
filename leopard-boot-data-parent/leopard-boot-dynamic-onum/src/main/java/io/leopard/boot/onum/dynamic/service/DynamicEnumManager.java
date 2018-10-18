package io.leopard.boot.onum.dynamic.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.leopard.boot.onum.dynamic.model.DynamicEnumDataVO;
import io.leopard.boot.onum.dynamic.model.DynamicEnumInfo;
import io.leopard.lang.inum.Bnum;
import io.leopard.lang.inum.Inum;
import io.leopard.lang.inum.Snum;
import io.leopard.lang.inum.dynamic.DynamicEnum;
import io.leopard.lang.inum.dynamic.EnumConstant;

public class DynamicEnumManager {

	private static Map<String, String> beanMap = new HashMap<>();

	private static List<DynamicEnumInfo> enumList = new ArrayList<DynamicEnumInfo>();

	public static void addDynamicEnumInfo(DynamicEnumInfo enumInfo) {
		enumList.add(enumInfo);
	}

	public static DynamicEnumInfo findDynamicEnumInfo(String enumId) {
		for (DynamicEnumInfo enumInfo : enumList) {
			if (enumInfo.getEnumId().equals(enumId)) {
				return enumInfo;
			}
		}
		return null;
	}

	public static List<DynamicEnumInfo> getEnumList() {
		return enumList;
	}

	public static void addBean(String enumId, String beanClassName) {
		beanMap.put(enumId, beanClassName);
	}

	/**
	 * 是否有动态枚举
	 * 
	 * @param enumId 枚举ID
	 * @return
	 */
	public static boolean hasEnum(String enumId) {
		return beanMap.containsKey(enumId);
	}

	/**
	 * 根据枚举ID获取类型
	 * 
	 * @param enumId 枚举ID
	 * @return
	 */
	public static String getBeanClassName(String enumId) {
		return beanMap.get(enumId);
	}

	public static Object toObjectKey(Class<?> enumType, String key) {
		if (Snum.class.isAssignableFrom(enumType)) {
			return key;
		}
		else if (Inum.class.isAssignableFrom(enumType)) {
			return Integer.parseInt(key);
		}
		else if (Bnum.class.isAssignableFrom(enumType)) {
			return Byte.parseByte(key);
		}
		else {
			throw new IllegalArgumentException("未知枚举类型[" + enumType.getName() + "].");
		}
	}

	/**
	 * 判断枚举元素是否存在
	 * 
	 * @param enumId 枚举ID
	 * @param key 枚举元素key
	 * @return
	 */
	public static boolean hasEnumConstant(String enumId, String key) {
		List<EnumConstant> constantList = listByEnumId(enumId);
		if (constantList != null) {
			for (EnumConstant constant : constantList) {
				if (constant.getKey().toString().equals(key)) {
					return true;
				}
			}
		}
		return false;
	}

	public static List<EnumConstant> listByEnumId(String enumId) {
		DynamicEnumInfo enumInfo = findDynamicEnumInfo(enumId);
		if (enumInfo == null) {
			throw new RuntimeException("枚举[" + enumId + "]不存在.");
		}
		String className = enumInfo.getBeanClassName();
		return DynamicEnum.allOf(className);
	}

	public static List<DynamicEnumDataVO.EnumConstantIO> listByClassName(String className) {
		List<EnumConstant> constantList = DynamicEnum.allOf(className);
		List<DynamicEnumDataVO.EnumConstantIO> constantVOList = new ArrayList<>();
		for (EnumConstant constant : constantList) {
			DynamicEnumDataVO.EnumConstantIO constantVO = new DynamicEnumDataVO.EnumConstantIO();
			constantVO.setDesc(constant.getDesc());
			constantVO.setKey(constant.getKey());
			constantVO.setParameterMap(constant.getParameterMap());// TODO 这里引用传值是否有问题
			constantVOList.add(constantVO);
		}
		return constantVOList;
	}
}
