package io.leopard.boot.weixin.model;

import io.leopard.lang.inum.Snum;

public enum MsgType implements Snum {
	TEXT("text", "文本")//
	, LINK("link", "链接")//
	, EVENT("event", "事件")//
	, IMAGE("image", "图片");
	private String key;

	private String desc;

	private MsgType(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getDesc() {
		return desc;
	}

}
