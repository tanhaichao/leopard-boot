package io.leopard.boot.weixin.model.message;

public class TextWeixinMessage extends WeixinMessage {

	private String Content;

	public TextWeixinMessage() {
		this.setMsgType("text");
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

}
