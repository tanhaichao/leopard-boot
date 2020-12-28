package io.leopard.boot.weixin;

import io.leopard.boot.weixin.model.AccessToken;

public interface CustomWeixinAccessTokenDao extends WeixinAccessTokenDao {
	boolean updateAccessToken(String appId, AccessToken accessToken);

}
