package io.leopard.boot.onum.dynamic.model;

/**
 * 动态枚举表单
 * 
 * @author 谭海潮
 *
 */
public class DynamicEnumConstantForm {
	/**
	 * 枚举ID
	 */
	private String enumId;

	/**
	 * 元素key
	 */
	private String key;

	/**
	 * 元素描述
	 */
	private String desc;

	/**
	 * 显示位置
	 */
	private int position;

	public String getEnumId() {
		return enumId;
	}

	public void setEnumId(String enumId) {
		this.enumId = enumId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

}
