package io.leopard.boot.weixin.model.message;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VoiceWeixinMessage {

	@JsonProperty("ToUserName")
	private String toUserName;

	@JsonProperty("FromUserName")
	private String fromUserName;

	@JsonProperty("CreateTime")
	private long createTime;

	@JsonProperty("MsgType")
	private String msgType;

	@JsonProperty("MediaId")
	private String mediaId;

	@JsonProperty("Format")
	private String format;

	@JsonProperty("MsgId")
	private String msgId;

	@JsonProperty("Recognition")
	private String recognition;

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

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getRecognition() {
		return recognition;
	}

	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}

}
