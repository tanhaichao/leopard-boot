package io.leopard.boot.weixin;

import java.io.IOException;
import java.io.InputStream;

import io.leopard.boot.weixin.model.AccessToken;

/**
 * 微信小程序
 * 
 * @author 谭海潮
 *
 */
public interface WxmpService {

	String getAppId();

	InputStream getWxaQrcode(String path, int width) throws IOException;

	AccessToken getAccessToken();

	InputStream getUnlimitedWxaQrcode(String path, int width) throws IOException;

	boolean refreshAccessToken();
}
