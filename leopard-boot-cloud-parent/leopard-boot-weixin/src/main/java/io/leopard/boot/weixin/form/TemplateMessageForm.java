package io.leopard.boot.weixin.form;

import java.util.Map;

public class TemplateMessageForm {
	/**
	 * OPENID
	 */
	private String touser;

	private String templateId;

	private String url;

	private Miniprogram miniprogram;

	private Map<String, String> data;

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

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
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
