package io.leopard.boot.onum.dynamic.model;

public class DynamicEnumInfo {

	private String enumId;

	private String beanClassName;

	private Class<?> enumType;

	public String getEnumId() {
		return enumId;
	}

	public void setEnumId(String enumId) {
		this.enumId = enumId;
	}

	public String getBeanClassName() {
		return beanClassName;
	}

	public void setBeanClassName(String beanClassName) {
		this.beanClassName = beanClassName;
	}

	public Class<?> getEnumType() {
		return enumType;
	}

	public void setEnumType(Class<?> enumType) {
		this.enumType = enumType;
	}

}
