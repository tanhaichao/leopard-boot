package io.leopard.boot.weixin.form;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TemplateMessageForm {
	/**
	 * OPENID
	 */
	private String touser;

	@JsonProperty("template_id")
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

	public void setMiniprogram(String appid, String path) {
		this.miniprogram = new Miniprogram(appid, path);
	}

	public void addData(String key, String value) {
		this.addData(key, value, "#173177");
	}

	public void addCurrencyData(String key, double num) {
		String value = num + "元";
		this.addData(key, value, "#173177");
	}

	public void addDateData(String key, Date date) {
		String value = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(date);
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

		private String path;

		public Miniprogram() {
		}

		public Miniprogram(String appid, String path) {
			super();
			this.appid = appid;
			this.path = path;
		}

		public String getAppid() {
			return appid;
		}

		public void setAppid(String appid) {
			this.appid = appid;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

	}
}
