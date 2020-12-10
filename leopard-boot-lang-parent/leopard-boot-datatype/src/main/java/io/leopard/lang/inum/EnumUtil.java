package io.leopard.lang.inum;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 枚举工具类
 * 
 * @author 谭海潮
 *
 */
public class EnumUtil {

	// Map<枚举类名,Map<元素key, 元素>>
	protected static final Map<String, Map<Object, Enum<?>>> cache = new ConcurrentHashMap<String, Map<Object, Enum<?>>>();

	/**
	 * 清空缓存
	 */
	public static void clearCache() {
		cache.clear();
	}

	public static <E extends Enum<E>> void add(Object key, E onum) {
		String className = onum.getClass().getName();
		Map<Object, Enum<?>> constantMap = cache.get(className);
		if (constantMap == null) {
			constantMap = new HashMap<>();
			cache.put(className, constantMap);
		}
		if (cache.containsKey(key)) {
			throw new RuntimeException("枚举元素[" + key + "]已存在.");
		}
		constantMap.put(key, onum);
	}

	// public static <E extends Enum<E>> EnumSet<E> of(E e) {

	/**
	 * 获取枚举元素的key
	 * 
	 * @param enumConstant 枚举元素
	 * @return
	 */
	public static <KEYTYPE, E extends Onum<KEYTYPE, ?>> KEYTYPE getKey(E enumConstant) {
		if (enumConstant == null) {
			return null;
		}
		return enumConstant.getKey();
	}

	public static <E extends Enum<E>> String getDesc(Object key, Class<E> clazz) {
		// Onum<KEYTYPE, VALUETYPE> obj = EnumUtil.toEnum(key, clazz);
		E e = EnumUtil.get(key, clazz);
		if (e == null) {
			return null;
		}
		@SuppressWarnings("unchecked")
		Onum<Object, Object> onum = (Onum<Object, Object>) e;
		return (String) onum.getDesc();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object toEnumObject(Object key, Class<?> clazz) {
		return toEnum(key, (Class<Enum>) clazz);
	}

	/**
	 * 根据ID转换为枚举(元素不存在会抛异常).
	 * 
	 * @param id
	 * @param clazz
	 * @return
	 */
	public static <E extends Enum<E>> E toEnum(Object key, Class<E> clazz) {
		E inum = get(key, clazz);
		if (inum == null) {
			// System.err.println("key:" + key.getClass().getName());
			throw new EnumConstantNotFoundException(key, clazz);
		}
		return inum;
	}

	public static <E extends Enum<E>> E toEnumByDesc(String desc, Class<E> clazz) {
		E inum = getByDesc(desc, clazz);
		if (inum == null) {
			// System.err.println("key:" + key.getClass().getName());
			throw new EnumConstantInvalidException("根据描述找不到枚举[" + clazz.getName() + "]元素[" + desc + "]");
		}
		return inum;
	}

	/**
	 * 根据ID转换为枚举(元素不存在返回onum).
	 * 
	 * @param key
	 * @param clazz
	 * @param onum
	 * @return
	 */
	public static <E extends Enum<E>> E toEnum(Object key, Class<E> clazz, E onum) {
		E inum = get(key, clazz);
		if (inum == null) {
			return onum;
		}
		return inum;
	}

	/**
	 * 判断枚举元素是否存在.
	 * 
	 * @param key
	 * @param clazz
	 * @return
	 */
	public static <E extends Enum<E>> boolean contains(List<E> constantList, E constant) {
		if (constantList != null) {
			for (E _constant : constantList) {
				if (_constant == constant) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断key是否存在.
	 * 
	 * @param key
	 * @param clazz
	 * @return
	 */
	public static <E extends Enum<E>> boolean contains(Object key, Class<E> clazz) {
		// key = toLowerCase(key);
		Map<Object, Enum<?>> map = cache.get(key);
		if (map == null) {
			map = toMap(clazz);
		}
		return map.containsKey(key);
	}

	/**
	 * 根据ID转换为枚举(元素不存在则返回null，不抛异常)
	 * 
	 * @param id
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <E extends Enum<E>> E get(Object key, Class<E> clazz) {
		// key = toLowerCase(key);
		Map<Object, Enum<?>> map = cache.get(clazz.getName());
		if (map == null) {
			map = toMap(clazz);
		}
		return (E) map.get(key);
	}

	@SuppressWarnings("unchecked")
	public static <E extends Enum<E>> E getByDesc(String desc, Class<E> clazz) {
		// key = toLowerCase(key);
		Map<Object, Enum<?>> map = cache.get(clazz.getName());
		if (map == null) {
			map = toMap(clazz);
		}
		for (Enum<?> e : map.values()) {
			Onum<Object, String> onum = (Onum<Object, String>) e;
			if (desc.equals(onum.getDesc())) {
				return (E) e;
			}
		}
		return null;
	}
	// public static synchronized <E extends Enum<E>> Map<Object, Enum<?>> toMap(Class<E> clazz) {
	// return toMap(clazz.getName(), clazz);
	// }

	@SuppressWarnings("unchecked")
	public static synchronized <E extends Enum<E>> Map<Object, Enum<?>> toMap(Class<E> clazz) {
		Map<Object, Enum<?>> map = cache.get(clazz.getName());
		if (map != null) {
			return map;
		}
		map = new LinkedHashMap<Object, Enum<?>>();
		EnumSet<E> set = EnumSet.allOf(clazz);
		Iterator<E> iterator = set.iterator();
		while (iterator.hasNext()) {
			Onum<Object, Object> value = (Onum<Object, Object>) iterator.next();
			Object id = value.getKey();
			// id = toLowerCase(id);
			map.put(id, (Enum<?>) value);
		}
		// cache.put(key, map);
		cache.put(clazz.getName(), map);
		return map;
	}

	protected static Object toLowerCase(Object key) {
		if (key instanceof String) {
			return ((String) key).toLowerCase();// 统一转成小写保存
		}
		else {
			return key;
		}
	}

}
