package io.leopard.web.mvc.json.model;

/**
 * 附加的属性
 * 
 * @author 谭海潮
 *
 */
public class AdditionalField<T> {

	/**
	 * 属性名称
	 */
	private String fieldName;

	/**
	 * 属性值
	 */
	private T value;

	public AdditionalField() {
	}

	public AdditionalField(String fieldName, T value) {
		this.fieldName = fieldName;
		this.value = value;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

}
