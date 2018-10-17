package io.leopard.boot.onum.dynamic.model;

import java.util.Date;
import java.util.List;

/**
 * 枚举数据
 * 
 * @author 谭海潮
 *
 */
public class DynamicEnumDataVO {

	/**
	 * 最后更新时间
	 */
	private Date lmodify;

	/**
	 * 枚举列表
	 */
	private List<DynamicEnumVO> enumList;

	public Date getLmodify() {
		return lmodify;
	}

	public void setLmodify(Date lmodify) {
		this.lmodify = lmodify;
	}

	public List<DynamicEnumVO> getEnumList() {
		return enumList;
	}

	public void setEnumList(List<DynamicEnumVO> enumList) {
		this.enumList = enumList;
	}

}
