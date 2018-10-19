package io.leopard.lang.inum;

/**
 * 枚举元素不存在.
 * 
 * @author 谭海潮
 *
 */
public class EnumConstantNotFoundException extends EnumConstantInvalidException {

	private static final long serialVersionUID = 1L;

	public EnumConstantNotFoundException(Object key, Class<?> enumClazz) {
		super("枚举" + enumClazz.getSimpleName() + "元素[" + key + "]不存在[" + enumClazz.getName() + "].");
	}

}
