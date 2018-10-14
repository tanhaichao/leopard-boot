package io.leopard.boot.onum.dynamic;

/**
 * 动态枚举元素不存在.
 * 
 * @author 谭海潮
 *
 */
public class DynamicEnumConstantNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public DynamicEnumConstantNotFoundException(String enumId, String key) {
		super("动态枚举元素[enumId:" + enumId + " key:" + key + "]不存在.");
	}

}
