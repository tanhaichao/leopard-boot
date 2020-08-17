package io.leopard.boot.weixin.model.message;

public class SuccessWeixinMessage extends WeixinMessage {

	@Override
	public String toResponseXml() {
		return "success";
	}
}
