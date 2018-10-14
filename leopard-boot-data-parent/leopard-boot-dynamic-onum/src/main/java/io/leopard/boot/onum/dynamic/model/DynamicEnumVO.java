package io.leopard.boot.onum.dynamic.model;

import java.util.List;

public class DynamicEnumVO {

	/**
	 * 枚举ID
	 */
	private String enumId;

	/**
	 * 元素列表
	 */
	private List<EnumConstantVO> constantList;

	public String getEnumId() {
		return enumId;
	}

	public void setEnumId(String enumId) {
		this.enumId = enumId;
	}

	public List<EnumConstantVO> getConstantList() {
		return constantList;
	}

	public void setConstantList(List<EnumConstantVO> constantList) {
		this.constantList = constantList;
	}

}
