package io.leopard.jdbc.builder;

/**
 * 排序
 * 
 * @author 谭海潮
 *
 */
public class Orderby {

	/**
	 * 字段名称
	 */
	private String fieldName;

	// 按desc 还是asc
	private String direction;

	public Orderby(String fieldName, String direction) {
		this.fieldName = fieldName;
		this.direction = direction;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

}
