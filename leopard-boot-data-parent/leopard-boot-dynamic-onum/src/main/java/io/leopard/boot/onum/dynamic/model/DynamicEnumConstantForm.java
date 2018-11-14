package io.leopard.boot.onum.dynamic.model;

/**
 * 动态枚举表单
 * 
 * @author 谭海潮
 *
 */
public class DynamicEnumConstantForm {

	/**
	 * 元素key
	 */
	private String key;

	/**
	 * 元素描述
	 */
	private String desc;

	/**
	 * 显示位置(批量修改时忽略此参数，以列表位置为准)
	 */
	private int position;

	/**
	 * 是否禁用
	 */
	private boolean disable;

	/**
	 * 备注
	 */
	private String remark;

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}

}
