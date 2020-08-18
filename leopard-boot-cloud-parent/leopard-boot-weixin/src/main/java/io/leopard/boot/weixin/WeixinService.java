package io.leopard.boot.weixin;

import io.leopard.boot.weixin.form.TemplateMessageForm;
import io.leopard.boot.weixin.model.AccessToken;
import io.leopard.boot.weixin.model.JSCode2Session;
import io.leopard.boot.weixin.model.Qrcode;
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

	Qrcode getQrcodeLimitStrScene(String sceneStr);

	void getAllPrivateTemplate();

	void sendTemplateMessage(TemplateMessageForm message);

	void createMenu(String body);

	Qrcode getQrcodeStrScene(String sceneStr, int expireSeconds);

	/**
	 * 设置行业
	 * 
	 * @param industryId1
	 * @param industryId2
	 */
	void industry(String industryId1, String industryId2);

	void getIndustry();
}
