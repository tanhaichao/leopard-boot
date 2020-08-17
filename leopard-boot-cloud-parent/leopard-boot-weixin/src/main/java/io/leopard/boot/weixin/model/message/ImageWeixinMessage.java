package io.leopard.boot.weixin.model.message;

public class ImageWeixinMessage extends WeixinMessage {

	private String Image;

	public ImageWeixinMessage() {
		this.setMsgType("image");
	}

	public String getImage() {
		return Image;
	}

	public void setImage(String image) {
		Image = image;
	}

	public static class Image {
		private String MediaId;

		public String getMediaId() {
			return MediaId;
		}

		public void setMediaId(String mediaId) {
			MediaId = mediaId;
		}

	}
}
