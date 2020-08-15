package io.leopard.boot.weixin;

import io.leopard.boot.weixin.model.AccessToken;
import io.leopard.boot.weixin.model.JSCode2Session;
import io.leopard.boot.weixin.model.WeixinMobile;

public interface WeixinService {
	/**
	 * 获取微信用户信息
	 * 
	 * @param code 调用微信登陆返回的Code
	 * @return
	 */
	String getSessionKey(String code);

	String getAppId();

	AccessToken getAccessToken();

	JSCode2Session jscode2Session(String code);

	String getUnionIdByUserinfo(String sessionKey, String encryptedData, String iv);

	WeixinMobile getWeixinMobile(String sessionKey, String encryptedData, String iv);
}
