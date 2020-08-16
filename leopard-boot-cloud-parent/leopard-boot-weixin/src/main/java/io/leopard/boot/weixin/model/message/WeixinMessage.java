package io.leopard.boot.weixin.model.message;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeixinMessage {

	@JsonProperty("ToUserName")
	private String toUserName;

	@JsonProperty("FromUserName")
	private String fromUserName;

	@JsonProperty("CreateTime")
	private long createTime;

	@JsonProperty("MsgType")
	private String msgType;

	@JsonProperty("Content")
	private String content;

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
