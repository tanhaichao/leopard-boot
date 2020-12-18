package io.leopard.boot.weixin;

import org.springframework.stereotype.Component;

import io.leopard.boot.weixin.model.AccessToken;

@Component
public class WeixinAccessTokenDaoCacheImpl implements WeixinAccessTokenDao {

	@Override
	public AccessToken getAccessToken(String appId, String secret) {

		return null;
	}

}
