package io.leopard.boot.onum.dynamic.model;

import java.util.Date;

/**
 * 枚举元素
 * 
 * @author 谭海潮
 *
 */
public class DynamicEnumConstantEntity {

	/**
	 * 枚举ID
	 */
	private String enumId;

	/**
	 * key
	 */
	private String key;

	/**
	 * 描述
	 */
	private String desc;

	/**
	 * 显示位置
	 */
	private int position;

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

	// TODO 未实现扩展参数存储
	public String getEnumId() {
		return enumId;
	}

	public void setEnumId(String enumId) {
		this.enumId = enumId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
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

}
