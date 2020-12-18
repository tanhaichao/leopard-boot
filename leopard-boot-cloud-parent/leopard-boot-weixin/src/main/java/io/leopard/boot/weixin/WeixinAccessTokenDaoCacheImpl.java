package io.leopard.boot.weixin;

import java.net.Proxy;

import javax.annotation.Resource;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.leopard.boot.weixin.model.AccessToken;

@Component
public class WeixinAccessTokenDaoCacheImpl implements WeixinAccessTokenDao {
	protected Log logger = LogFactory.getLog(this.getClass());

	@Resource(name = "customWeixinAccessTokenDao")
	private WeixinAccessTokenDao customWeixinAccessTokenDao;

	@Autowired
	private WeixinAccessTokenDaoHttpImpl weixinAccessTokenDaoHttpImpl;

	@Override
	public AccessToken getAccessToken(String appId, String secret, Proxy proxy) {
		logger.info("getAccessToken:" + appId);
		AccessToken accessToken = customWeixinAccessTokenDao.getAccessToken(appId, secret, proxy);
		if (accessToken == null) {
			accessToken = this.weixinAccessTokenDaoHttpImpl.getAccessToken(appId, secret, proxy);
			this.customWeixinAccessTokenDao.updateAccessToken(appId, accessToken);
		}
		return accessToken;
	}

	@Override
	public boolean updateAccessToken(String appId, AccessToken accessToken) {
		throw new NotImplementedException("not impl.");
	}

}
