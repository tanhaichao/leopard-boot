package io.leopard.boot.onum.dynamic;

/**
 * 动态枚举找不到
 * 
 * @author 谭海潮
 *
 */
public class DynamicEnumNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public DynamicEnumNotFoundException(String enumId) {
		super("动态枚举[" + enumId + "]不存在.");
	}
}
