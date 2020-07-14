package io.leopard.boot.weixin;

import io.leopard.boot.weixin.model.JSCode2Session;

public interface WeixinService {
	/**
	 * 获取微信用户信息
	 * 
	 * @param code 调用微信登陆返回的Code
	 * @return
	 */
	String getSessionKey(String code);

	JSCode2Session jscode2Session(String code);
}
