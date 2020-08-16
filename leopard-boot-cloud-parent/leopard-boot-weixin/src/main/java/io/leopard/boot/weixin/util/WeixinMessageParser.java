package io.leopard.boot.weixin.util;

import java.io.IOException;
import java.io.InputStream;

import org.w3c.dom.Element;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import io.leopard.boot.weixin.model.message.WeixinMessage;

public class WeixinMessageParser {
	public static final XmlMapper xmlMapper = new XmlMapper();

	public static String toJson(String xml) throws IOException {
		JsonNode node = xmlMapper.readTree(xml.getBytes());

		ObjectMapper jsonMapper = new ObjectMapper();
		String json = jsonMapper.writeValueAsString(node);
		return json;
	}

	public static WeixinMessage parse(String xml) throws JsonParseException, JsonMappingException, IOException {
		return xmlMapper.readValue(xml, WeixinMessage.class);
	}

	public static WeixinMessage parseXml(InputStream input) throws IOException {
		// Element element = XmlUtils.toRootElement(xml);

		Element element = XmlUtils.read(input).getDocumentElement();
		input.close();

		String toUserName = element.getElementsByTagName("ToUserName").item(0).getTextContent();
		String fromUserName = element.getElementsByTagName("FromUserName").item(0).getTextContent();
		long createTime = Long.parseLong(element.getElementsByTagName("CreateTime").item(0).getTextContent());
		String msgType = element.getElementsByTagName("MsgType").item(0).getTextContent();
		WeixinMessage postBean = new WeixinMessage();
		postBean.setToUserName(toUserName);
		postBean.setFromUserName(fromUserName);
		postBean.setCreateTime(createTime);
		postBean.setMsgType(msgType);
		if (msgType.equals("voice")) {
			String recognition = element.getElementsByTagName("Recognition").item(0).getTextContent();
			postBean.setContent(recognition);
		}
		else {
			String content = element.getElementsByTagName("Content").item(0).getTextContent();
			postBean.setContent(content);
		}
		return postBean;
	}
}
