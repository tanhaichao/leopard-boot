package io.leopard.boot.weixin.util;

import java.io.File;

import org.junit.Test;

public class WeixinMediaUploadUtilTest {

	@Test
	public void uploadImageMedia() throws Exception {
		File file = new File("/Users/tanhaichao/Desktop/0.png");
		String accessToken = "xxx";
		String json = WeixinMediaUploadUtil.uploadImageMedia(accessToken, file);
		System.out.println("json:" + json);

	}

}