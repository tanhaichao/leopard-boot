package io.leopard.boot.weixin.form;

import java.util.LinkedHashMap;
import java.util.Map;

public class TemplateMessageForm {
	/**
	 * OPENID
	 */
	private String touser;

	private String templateId;

	private String url;

	private Miniprogram miniprogram;

	private Map<String, DataElement> data = new LinkedHashMap<>();

	public TemplateMessageForm() {

	}

	public TemplateMessageForm(String touser, String templateId) {
		this.touser = touser;
		this.templateId = templateId;
	}

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Miniprogram getMiniprogram() {
		return miniprogram;
	}

	public void setMiniprogram(Miniprogram miniprogram) {
		this.miniprogram = miniprogram;
	}

	public void setMiniprogram(String appid, String pagepath) {
		this.miniprogram = new Miniprogram(appid, pagepath);
	}

	public void addData(String key, String value) {
		this.addData(key, value, "#173177");
	}

	public void addData(String key, String value, String color) {
		this.data.put(key, new DataElement(value, color));
	}

	public Map<String, DataElement> getData() {
		return data;
	}

	public void setData(Map<String, DataElement> data) {
		this.data = data;
	}

	public static class DataElement {
		private String value;

		private String color;

		public DataElement() {

		}

		public DataElement(String value, String color) {
			this.value = value;
			this.color = color;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}

	}

	public static class Miniprogram {
		private String appid;

		private String pagepath;

		public Miniprogram() {
		}

		public Miniprogram(String appid, String pagepath) {
			super();
			this.appid = appid;
			this.pagepath = pagepath;
		}

		public String getAppid() {
			return appid;
		}

		public void setAppid(String appid) {
			this.appid = appid;
		}

		public String getPagepath() {
			return pagepath;
		}

		public void setPagepath(String pagepath) {
			this.pagepath = pagepath;
		}
	}
}
