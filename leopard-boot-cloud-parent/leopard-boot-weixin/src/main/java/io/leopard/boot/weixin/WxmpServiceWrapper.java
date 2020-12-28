package io.leopard.boot.weixin;

import java.io.IOException;
import java.io.InputStream;

import io.leopard.boot.weixin.model.AccessToken;

public class WxmpServiceWrapper implements WxmpService {

	protected WxmpService wxmpService;

	public WxmpService getWxmpService() {
		return wxmpService;
	}

	public void setWxmpService(WxmpService wxmpService) {
		this.wxmpService = wxmpService;
	}

	@Override
	public String getAppId() {
		return wxmpService.getAppId();
	}

	@Override
	public InputStream getWxaQrcode(String path, int width) throws IOException {
		return wxmpService.getWxaQrcode(path, width);
	}

	@Override
	public AccessToken getAccessToken() {
		return wxmpService.getAccessToken();
	}

	@Override
	public InputStream getUnlimitedWxaQrcode(String path, int width) throws IOException {
		return wxmpService.getUnlimitedWxaQrcode(path, width);
	}

	@Override
	public boolean refreshAccessToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
