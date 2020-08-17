package io.leopard.boot.weixin.model.message;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import io.leopard.boot.weixin.util.WeixinMessageParser;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonDeserialize(using = WeixinMessageJsonDeserializer.class)
@JacksonXmlRootElement(localName = "xml")
public class WeixinMessage {

	private String ToUserName;

	private String FromUserName;

	private int CreateTime;

	private String MsgType;

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public int getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(int createTime) {
		CreateTime = createTime;
	}

	public void setCreateTime(Date createTime) {
		int second = (int) (createTime.getTime() / 1000);
		this.CreateTime = second;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	public String toResponseXml() {
		return WeixinMessageParser.toFormatXml(this);
	}
}
