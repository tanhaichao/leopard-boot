package io.leopard.boot.weixin.mock;

import io.leopard.boot.weixin.WeixinService;
import io.leopard.boot.weixin.model.JSCode2Session;

public class MockWeixinService implements WeixinService {

	private String sessionKey;

	private String appId;

	private JSCode2Session session;

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

}
