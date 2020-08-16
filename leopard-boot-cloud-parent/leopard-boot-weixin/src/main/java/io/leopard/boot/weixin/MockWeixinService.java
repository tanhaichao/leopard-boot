package io.leopard.boot.weixin;

import org.springframework.util.StringUtils;

import io.leopard.boot.weixin.form.TemplateMessageForm;
import io.leopard.boot.weixin.model.AccessToken;
import io.leopard.boot.weixin.model.JSCode2Session;
import io.leopard.boot.weixin.model.Qrcode;
import io.leopard.boot.weixin.model.WeixinMobile;

public class MockWeixinService implements WeixinService {

	private String sessionKey;

	private String appId;

	private JSCode2Session session;

	private String unionId;

	private String purePhoneNumber;

	public MockWeixinService() {
	}

	public MockWeixinService(String appId) {
		this.appId = appId;
	}

	@Override
	public String getSessionKey(String code) {
		return this.sessionKey;
	}

	@Override
	public String getAppId() {
		return this.appId;
	}

	@Override
	public JSCode2Session jscode2Session(String code) {
		return this.session;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public JSCode2Session getSession() {
		return session;
	}

	public void setSession(JSCode2Session session) {
		this.session = session;
	}

	public void setSession(String sessionKey, String openid) {
		this.setSession(sessionKey, openid, null);
	}

	public void setSession(String sessionKey, String openid, String unionId) {
		JSCode2Session session = new JSCode2Session();
		session.setSessionKey(sessionKey);
		session.setOpenid(openid);
		session.setUnionId(unionId);
		this.session = session;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	@Override
	public String getUnionIdByUserinfo(String sessionKey, String encryptedData, String iv) {
		return this.unionId;
	}

	public String getPurePhoneNumber() {
		return purePhoneNumber;
	}

	public void setPurePhoneNumber(String purePhoneNumber) {
		this.purePhoneNumber = purePhoneNumber;
	}

	@Override
	public WeixinMobile getWeixinMobile(String sessionKey, String encryptedData, String iv) {
		if (StringUtils.isEmpty(purePhoneNumber)) {
			throw new RuntimeException("非法请求.");
		}
		WeixinMobile weixinMobile = new WeixinMobile();
		weixinMobile.setPurePhoneNumber(purePhoneNumber);
		return weixinMobile;
	}

	@Override
	public AccessToken getAccessToken() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Qrcode getQrcodeLimitStrScene(String sceneStr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getAllPrivateTemplate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendTemplateMessage(TemplateMessageForm message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createMenu(String body) {
		// TODO Auto-generated method stub

	}

}
