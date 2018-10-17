package io.leopard.boot.onum.dynamic.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 枚举元素
 * 
 * @author 谭海潮
 *
 */
public class DynamicEnumConstantVO {

	/**
	 * 枚举元素Key
	 */
	private Object key;

	/**
	 * 枚举元素描述
	 */
	private String desc;

	/**
	 * 扩展参数
	 */
	@JsonInclude(Include.NON_NULL)
	private Map<String, Object> parameterMap;

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Map<String, Object> getParameterMap() {
		return parameterMap;
	}

	public void setParameterMap(Map<String, Object> parameterMap) {
		this.parameterMap = parameterMap;
	}

}
