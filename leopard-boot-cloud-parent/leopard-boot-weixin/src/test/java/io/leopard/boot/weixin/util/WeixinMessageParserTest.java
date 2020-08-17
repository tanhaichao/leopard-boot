package io.leopard.boot.weixin.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import io.leopard.boot.weixin.model.message.TextWeixinMessage;
import io.leopard.boot.weixin.model.message.VoiceWeixinMessage;
import io.leopard.boot.weixin.model.message.WeixinMessage;
import io.leopard.json.Json;

public class WeixinMessageParserTest {

	@Test
	public void parse() throws IOException {
		File file = new File("/tmp/weixin/test1.xml");
		String xml = FileUtils.readFileToString(file, "UTF-8");
		WeixinMessage message = WeixinMessageParser.parse(xml);
		Json.printFormat(message, "message");

		String xml2 = WeixinMessageParser.toFormatXml(message);
		System.out.println(xml2);
	}

	@Test
	public void toFormatXml() throws IOException {
		TextWeixinMessage result = new TextWeixinMessage();

		result.setFromUserName("xxx");
		result.setCreateTime(new Date());
		result.setToUserName("xxx");

		String link = "<a data-miniprogram-appid=\"你的服务号open_id\" data-miniprogram-path=\"/pages/index/index\">点击此处，立即购药>></a>";

		result.setContent("您好！欢迎关注医理健康，我们将为您提供一站式购药解决方案。<br/>" + link);
		String json = WeixinMessageParser.toFormatXml(result);
		System.out.println(json);
	}

	@Test
	public void toJson() throws IOException {
		File file = new File("/tmp/weixin/test1.xml");
		String xml = FileUtils.readFileToString(file, "UTF-8");
		String json = WeixinMessageParser.toJson(xml);
		System.out.println(json);

		VoiceWeixinMessage message = Json.toObject(json, VoiceWeixinMessage.class);
		System.out.println("message:" + message.getToUserName());
	}

}