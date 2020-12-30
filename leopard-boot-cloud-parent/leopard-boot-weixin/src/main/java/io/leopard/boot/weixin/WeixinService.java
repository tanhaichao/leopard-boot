package io.leopard.boot.weixin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import io.leopard.boot.weixin.form.TemplateMessageForm;
import io.leopard.boot.weixin.model.AccessToken;
import io.leopard.boot.weixin.model.JSCode2Session;
import io.leopard.boot.weixin.model.OffiaccountUserinfo;
import io.leopard.boot.weixin.model.Qrcode;
import io.leopard.boot.weixin.model.WeixinMedia;
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

	/**
	 * 发送模板消息
	 * 
	 * @param message
	 */
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

	/**
	 * 获取公众号个人资料
	 * 
	 * @param openId
	 * @return
	 */
	OffiaccountUserinfo getUserinfo(String openId);

	Qrcode getWxaQrcode(String path, int width);

	/**
	 * 发送文本信息
	 * 
	 * @param openId
	 * @param content
	 */
	void sendTextMessage(String openId, String content);

	/**
	 * 发送图片消息
	 * 
	 * @param openId
	 * @param mediaId 素材ID
	 */
	void sendImageMessage(String openId, String mediaId);

	/**
	 * 上传图片素材
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	WeixinMedia uploadImageMedia(File file) throws IOException;

	WeixinMedia uploadImageMedia(InputStream input, String fileName) throws IOException;

	boolean refreshAccessToken();

	boolean forceRefreshAccessToken();

	AccessToken getAccessTokenByHttp();
}
