package io.leopard.boot.weixin.model;

public class WeixinData {

	private Watermark watermark;

	public Watermark getWatermark() {
		return watermark;
	}

	public void setWatermark(Watermark watermark) {
		this.watermark = watermark;
	}

	public static class Watermark {

		private int timestamp;

		private String appid;

		public int getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(int timestamp) {
			this.timestamp = timestamp;
		}

		public String getAppid() {
			return appid;
		}

		public void setAppid(String appid) {
			this.appid = appid;
		}
	}
}
