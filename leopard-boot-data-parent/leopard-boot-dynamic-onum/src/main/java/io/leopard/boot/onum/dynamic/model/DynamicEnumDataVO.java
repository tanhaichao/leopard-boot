package io.leopard.boot.onum.dynamic.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 所有枚举信息
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
	private List<EnumIO> enumList;

	public Date getLmodify() {
		return lmodify;
	}

	public void setLmodify(Date lmodify) {
		this.lmodify = lmodify;
	}

	public List<EnumIO> getEnumList() {
		return enumList;
	}

	public void setEnumList(List<EnumIO> enumList) {
		this.enumList = enumList;
	}

	/**
	 * 动态枚举
	 * 
	 * @author 谭海潮
	 *
	 */
	public static class EnumIO {

		/**
		 * 枚举ID
		 */
		private String enumId;

		/**
		 * 元素列表
		 */
		private List<EnumConstantIO> constantList;

		public String getEnumId() {
			return enumId;
		}

		public void setEnumId(String enumId) {
			this.enumId = enumId;
		}

		public List<EnumConstantIO> getConstantList() {
			return constantList;
		}

		public void setConstantList(List<EnumConstantIO> constantList) {
			this.constantList = constantList;
		}
	}

	/**
	 * 枚举元素
	 * 
	 * @author 谭海潮
	 *
	 */
	public static class EnumConstantIO {

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
}
