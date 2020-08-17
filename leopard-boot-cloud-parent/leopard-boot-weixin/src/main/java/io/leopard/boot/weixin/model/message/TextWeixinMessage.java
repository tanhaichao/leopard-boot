package io.leopard.boot.weixin.model.message;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;

public class TextWeixinMessage extends WeixinMessage {
	@JacksonXmlCData(value = true)
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
