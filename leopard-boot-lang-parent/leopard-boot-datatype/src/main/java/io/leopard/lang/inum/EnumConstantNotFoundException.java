package io.leopard.lang.inum;

/**
 * 枚举元素不存在.
 * 
 * @author 谭海潮
 *
 */
public class EnumConstantNotFoundException extends EnumConstantInvalidException {

	private static final long serialVersionUID = 1L;

	/**
	 * 枚举元素key
	 */
	private Object key;

	/**
	 * 枚举类
	 */
	private Class<?> enumClazz;

	public EnumConstantNotFoundException(Object key, Class<?> enumClazz) {
		super("枚举" + enumClazz.getSimpleName() + "元素[" + key + "]不存在[" + enumClazz.getName() + "].");
	}

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	public Class<?> getEnumClazz() {
		return enumClazz;
	}

	public void setEnumClazz(Class<?> enumClazz) {
		this.enumClazz = enumClazz;
	}

}
