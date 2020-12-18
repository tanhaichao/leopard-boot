package io.leopard.boot.weixin;

import java.net.Proxy;

import io.leopard.boot.weixin.model.AccessToken;

interface WeixinAccessTokenDao {

	AccessToken getAccessToken(String appId, String secret, Proxy proxy);

	boolean updateAccessToken(String appId, AccessToken accessToken);
}
