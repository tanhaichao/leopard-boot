package io.leopard.boot.weixin;

import java.net.Proxy;

import io.leopard.boot.weixin.model.AccessToken;

public interface WeixinAccessTokenDao {

	AccessToken getAccessToken(String appId, String secret, Proxy proxy);

	boolean refreshAccessToken(String appId, String secret, Proxy proxy);

	boolean forceRefreshAccessToken(String appId, String secret, Proxy proxy);

}
