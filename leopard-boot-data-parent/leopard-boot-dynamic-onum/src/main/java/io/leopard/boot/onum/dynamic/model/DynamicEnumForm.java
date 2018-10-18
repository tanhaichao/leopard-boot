package io.leopard.boot.onum.dynamic.model;

import java.util.List;

/**
 * 动态枚举表单
 * 
 * @author 谭海潮
 *
 */
public class DynamicEnumForm {
	/**
	 * 枚举ID
	 */
	private String enumId;

	/**
	 * 元素列表
	 */
	private List<DynamicEnumConstantForm> constantList;

	public String getEnumId() {
		return enumId;
	}

	public void setEnumId(String enumId) {
		this.enumId = enumId;
	}

	public List<DynamicEnumConstantForm> getConstantList() {
		return constantList;
	}

	public void setConstantList(List<DynamicEnumConstantForm> constantList) {
		this.constantList = constantList;
	}
}
