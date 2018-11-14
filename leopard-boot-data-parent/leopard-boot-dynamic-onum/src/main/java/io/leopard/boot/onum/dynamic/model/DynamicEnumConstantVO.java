package io.leopard.boot.onum.dynamic.model;

import java.util.Date;

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
	 * 是否禁用
	 */
	private boolean disable;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 创建时间
	 */
	private Date posttime;

	/**
	 * 最后修改时间
	 */
	private Date lmodify;
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

	public Date getPosttime() {
		return posttime;
	}

	public void setPosttime(Date posttime) {
		this.posttime = posttime;
	}

	public Date getLmodify() {
		return lmodify;
	}

	public void setLmodify(Date lmodify) {
		this.lmodify = lmodify;
	}

	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}

	// public Map<String, Object> getParameterMap() {
	// return parameterMap;
	// }
	//
	// public void setParameterMap(Map<String, Object> parameterMap) {
	// this.parameterMap = parameterMap;
	// }

}
