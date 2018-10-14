package io.leopard.boot.operator;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.aspectj.lang.JoinPoint;

/**
 * 实体类信息
 * 
 * @author 谭海潮
 *
 */
public class EntityInfo {

	JoinPoint joinPoint;

	private Object parameter;

	private Class<?> parameterClazz;

	public EntityInfo(JoinPoint joinPoint) {
		this.joinPoint = joinPoint;
		Object[] args = joinPoint.getArgs();
		if (args.length == 1) {// 只允许有一个参数
			this.parameter = args[0];
			if (args[0] != null) {
				this.parameterClazz = args[0].getClass();
			}
		}
	}

	public boolean isNotEntityParameter() {
		return !isEntityParameter();
	}

	/**
	 * 判断是否实体参数
	 * 
	 * @return
	 */
	public boolean isEntityParameter() {
		if (parameterClazz == null) {
			return false;
		}
		if (parameterClazz.isPrimitive()) {
			return false;
		}
		if (BASIC_TYPE_SET.contains(parameterClazz)) {
			return false;
		}
		return true;
	}

	/**
	 * 是否有任意一个属性
	 * 
	 * @param fieldNames
	 * @return
	 */
	public boolean hasAnyField(String... fieldNames) {
		if (this.isNotEntityParameter()) {
			return false;
		}
		for (String fieldName : fieldNames) {
			boolean hasField = this.hasField(fieldName);
			if (hasField) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否有属性
	 * 
	 * @param fieldName
	 * @return
	 */
	public boolean hasField(String fieldName) {
		Field field;
		try {
			field = parameterClazz.getDeclaredField(fieldName);
		}
		catch (NoSuchFieldException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		catch (SecurityException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return field != null;
	}

	/**
	 * 设置参数
	 * 
	 * @param fieldName
	 * @param value
	 */
	public boolean setFieldValue(String fieldName, Object value) {
		Field field;
		try {
			field = parameterClazz.getDeclaredField(fieldName);
		}
		catch (NoSuchFieldException e) {
			return false;
		}
		catch (SecurityException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		field.setAccessible(true);
		try {
			field.set(parameter, value);
		}
		catch (IllegalAccessException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return true;
	}

	/**
	 * 基本数据类型
	 */
	private static Set<Class<?>> BASIC_TYPE_SET = new HashSet<>();
	static {
		BASIC_TYPE_SET.add(Integer.class);
		BASIC_TYPE_SET.add(Long.class);
		BASIC_TYPE_SET.add(Float.class);
		BASIC_TYPE_SET.add(Double.class);
		BASIC_TYPE_SET.add(Boolean.class);
		BASIC_TYPE_SET.add(String.class);
		BASIC_TYPE_SET.add(Short.class);
		BASIC_TYPE_SET.add(Character.class);
		BASIC_TYPE_SET.add(Date.class);
	}

}
