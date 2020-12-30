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

public class WeixinServiceWrapper implements WeixinService {

	protected WeixinService weixinService;

	public WeixinService getWeixinService() {
		return weixinService;
	}

	public void setWeixinService(WeixinService weixinService) {
		this.weixinService = weixinService;
	}

	@Override
	public AccessToken getAccessToken() {
		return weixinService.getAccessToken();
	}

	@Override
	public String getSessionKey(String code) {
		return weixinService.getSessionKey(code);
	}

	@Override
	public String getAppId() {
		return weixinService.getAppId();
	}

	@Override
	public JSCode2Session jscode2Session(String code) {
		return weixinService.jscode2Session(code);
	}

	@Override
	public String getUnionIdByUserinfo(String sessionKey, String encryptedData, String iv) {
		return weixinService.getUnionIdByUserinfo(sessionKey, encryptedData, iv);
	}

	@Override
	public WeixinMobile getWeixinMobile(String sessionKey, String encryptedData, String iv) {
		return weixinService.getWeixinMobile(sessionKey, encryptedData, iv);
	}

	@Override
	public Qrcode getQrcodeLimitStrScene(String sceneStr) {
		return weixinService.getQrcodeLimitStrScene(sceneStr);
	}

	@Override
	public Qrcode getQrcodeStrScene(String sceneStr, int expireSeconds) {
		return weixinService.getQrcodeStrScene(sceneStr, expireSeconds);
	}

	@Override
	public void getAllPrivateTemplate() {
		weixinService.getAllPrivateTemplate();
	}

	@Override
	public void sendTemplateMessage(TemplateMessageForm message) {
		weixinService.sendTemplateMessage(message);
	}

	@Override
	public void createMenu(String body) {
		weixinService.createMenu(body);
	}

	@Override
	public void industry(String industryId1, String industryId2) {
		weixinService.industry(industryId1, industryId2);
	}

	@Override
	public void getIndustry() {
		weixinService.getIndustry();
	}

	@Override
	public OffiaccountUserinfo getUserinfo(String openId) {
		return weixinService.getUserinfo(openId);
	}

	@Override
	public Qrcode getWxaQrcode(String path, int width) {
		return weixinService.getWxaQrcode(path, width);
	}

	@Override
	public void sendTextMessage(String openId, String content) {
		weixinService.sendTextMessage(openId, content);
	}

	@Override
	public void sendImageMessage(String openId, String mediaId) {
		weixinService.sendImageMessage(openId, mediaId);
	}

	@Override
	public WeixinMedia uploadImageMedia(File file) throws IOException {
		return weixinService.uploadImageMedia(file);
	}

	@Override
	public WeixinMedia uploadImageMedia(InputStream input, String fileName) throws IOException {
		return weixinService.uploadImageMedia(input, fileName);
	}

	@Override
	public boolean refreshAccessToken() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean forceRefreshAccessToken() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AccessToken getAccessTokenByHttp() {
		// TODO Auto-generated method stub
		return null;
	}
}
