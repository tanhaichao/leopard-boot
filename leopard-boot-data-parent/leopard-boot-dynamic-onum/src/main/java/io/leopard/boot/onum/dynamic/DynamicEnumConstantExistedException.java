package io.leopard.boot.onum.dynamic;

import io.leopard.core.exception.ExistedException;

/**
 * 动态枚举元素已存在
 * 
 * @author 谭海潮
 *
 */
public class DynamicEnumConstantExistedException extends ExistedException {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param enumId 枚举ID
	 * @param key 枚举元素key
	 */
	public DynamicEnumConstantExistedException(String enumId, String key) {
		super("动态枚举[" + enumId + "]元素[" + key + "]已存在");
	}
}
