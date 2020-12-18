package io.leopard.boot.weixin;

import io.leopard.boot.weixin.model.AccessToken;

public interface WeixinAccessTokenDao {

	AccessToken getAccessToken(String appId, String secret);

}
