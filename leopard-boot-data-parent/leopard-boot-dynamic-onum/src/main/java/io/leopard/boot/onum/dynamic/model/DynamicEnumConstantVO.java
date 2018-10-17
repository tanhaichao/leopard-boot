package io.leopard.boot.onum.dynamic.model;

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
	 * 备注
	 */
	private String remark;

	/**
	 * 创建时间
	 */
	private String posttime;

	/**
	 * 最后修改时间
	 */
	private String lmodify;
	// /**
	// * 扩展参数
	// */
	// @JsonInclude(Include.NON_NULL)
	// private Map<String, Object> parameterMap;

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPosttime() {
		return posttime;
	}

	public void setPosttime(String posttime) {
		this.posttime = posttime;
	}

	public String getLmodify() {
		return lmodify;
	}

	public void setLmodify(String lmodify) {
		this.lmodify = lmodify;
	}

	// public Map<String, Object> getParameterMap() {
	// return parameterMap;
	// }
	//
	// public void setParameterMap(Map<String, Object> parameterMap) {
	// this.parameterMap = parameterMap;
	// }

}
