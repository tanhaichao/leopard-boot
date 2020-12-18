package io.leopard.boot.weixin;

import java.net.Proxy;

import org.springframework.stereotype.Component;

import io.leopard.boot.weixin.model.AccessToken;

@Component
public class WeixinAccessTokenDaoCacheImpl implements WeixinAccessTokenDao {

	@Override
	public AccessToken getAccessToken(String appId, String secret, Proxy proxy) {

		return null;
	}

}
