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
	private List<DynamicEnumConstantVO> constantList;

	public String getEnumId() {
		return enumId;
	}

	public void setEnumId(String enumId) {
		this.enumId = enumId;
	}

	public List<DynamicEnumConstantVO> getConstantList() {
		return constantList;
	}

	public void setConstantList(List<DynamicEnumConstantVO> constantList) {
		this.constantList = constantList;
	}

}
