package io.leopard.boot.weixin;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import io.leopard.httpnb.Httpnb;
import io.leopard.json.Json;

public class AbstractWxmpServiceTest {

	@Test
	public void AbstractWxmpService() throws IOException {
		Map<String, Object> params = new LinkedHashMap<>();
		// params.put("scene", scene);
		params.put("path", "pages/test");
		params.put("width", 0);
		String url = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=";
		String body = Json.toJson(params);
		// String json = Httpnb.doPost(url, proxy, null, body);
		InputStream input = Httpnb.doPostForInputStream(url, null, null, body);
		byte[] data = IOUtils.toByteArray(input);
		input.reset();
		if (data.length < 1000) {
			System.out.println("len:" + data.length + " data:" + new String(data));
		}
	}

}