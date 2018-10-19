package io.leopard.lang.inum.dynamic;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import io.leopard.lang.inum.EnumConstantInvalidException;

public class DynamicEnumUtil {

	// protected static Constructor<?> findConstructor(Class<?> clazz) {
	// Constructor<?>[] constructors = clazz.getDeclaredConstructors();
	// for (Constructor<?> constructor : constructors) {
	// if (constructor.getParameterTypes().length == 1) {
	// return constructor;
	// }
	// }
	// return null;
	// // throw new RuntimeException("获取不到动态枚举[" + clazz.getName() + "]默认构造函数.");
	// }

	public static <E extends DynamicEnum<?>> void print(Class<E> clazz) {
		List<E> constantList = values(clazz);
		for (E constant : constantList) {
			System.err.println("key:" + constant.getKey() + " desc:" + constant.getDesc());
		}
	}

	public static <E extends DynamicEnum<?>> List<E> values(Class<E> clazz) {
		// Constructor<?> constructor = findConstructor(clazz);
		// System.err.println("constructor:" + constructor.toGenericString());
		List<EnumConstant> list = DynamicEnum.allOf(clazz.getName());
		List<E> result = new ArrayList<>();
		for (EnumConstant constant : list) {
			E instance = newInstance(clazz, constant);
			result.add(instance);
		}
		return result;
	}

	protected static <E extends DynamicEnum<?>> E newInstance(Class<E> clazz, EnumConstant constant) {
		E instance;
		try {
			instance = clazz.newInstance();
			{
				Field keyField = DynamicEnum.class.getDeclaredField("key");
				keyField.setAccessible(true);
				keyField.set(instance, constant.getKey());
			}
			{
				Field descField = DynamicEnum.class.getDeclaredField("desc");
				descField.setAccessible(true);
				descField.set(instance, constant.getDesc());
			}
			// }
			// else {
			// // instance = (E) clazz.newInstance();
			// Object[] initargs = new Object[constructor.getParameterTypes().length];
			// if (initargs.length > 0) {
			// initargs[0] = constant.getKey();
			// }
			// instance = (E) constructor.newInstance(initargs);
			// }
		}
		catch (InstantiationException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		catch (IllegalAccessException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		catch (NoSuchFieldException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		catch (SecurityException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return instance;
	}

	/**
	 * 根据ID转换为枚举(元素不存在会抛异常).
	 * 
	 * @param id
	 * @param clazz
	 * @return
	 */
	public static <E extends DynamicEnum<?>> E toEnum(Object key, Class<E> clazz) {
		EnumConstant constant = DynamicEnum.getConstant(clazz.getName(), key);
		if (constant == null) {
			throw new EnumConstantInvalidException("枚举元素[" + key + "]不存在[" + clazz.getName() + "].");
		}
		// Constructor<?> constructor = findConstructor(clazz);
		E instance = newInstance(clazz, constant);
		return instance;
	}
}
