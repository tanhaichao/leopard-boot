package io.leopard.lang.datatype;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 排序
 * 
 * @author 谭海潮
 *
 */
public class Sort {
	// sort=+id
	// sort=-id

	/**
	 * 字段名称
	 */
	private String fieldName;

	/**
	 * 排序方向，null:为默认排序，false:为降序排序，true:为升序排序
	 */
	private Boolean direction;

	private String sort;

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		String regex = "^([\\+-])?([a-zA-Z0-9_]+)$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(sort);
		if (!m.find()) {
			throw new IllegalArgumentException("非法排序参数[" + sort + "].");
		}
		String direction = m.group(1);
		if (direction == null || direction.length() == 0) {
			this.direction = null;
		}
		else if ("+".equals(direction)) {
			this.direction = true;// 降序
		}
		else if ("-".equals(direction)) {
			this.direction = false;// 升序
		}
		else {
			throw new IllegalArgumentException("未知排序方向[" + direction + "].");
		}

		this.fieldName = m.group(2);
		this.checkAllFieldName(fieldName);
		this.sort = sort;
	}

	public String getFieldName() {
		return fieldName;
	}

	public Boolean getDirection() {
		return direction;
	}

	public boolean isAscending() {
		return direction != null && direction;
	}

	public boolean isDescending() {
		return direction != null && !direction;
	}

	/**
	 * 检查是否允许的字段
	 * 
	 * @param fieldName
	 */
	private void checkAllFieldName(String fieldName) {
		String[] allowFieldNames = this.allowFieldNames();
		if (allowFieldNames == null || allowFieldNames.length == 0) {
			return;
		}

		boolean allow = false;
		for (String allowFieldName : allowFieldNames) {
			if (allowFieldName.equals(fieldName)) {
				allow = true;
				break;
			}
		}
		if (!allow) {
			throw new IllegalArgumentException("不支持按该字段[" + fieldName + "]排序.");
		}
	}

	/**
	 * 允许排序的字段
	 * 
	 * @return
	 */
	public String[] allowFieldNames() {
		return null;
	}
}
