package io.leopard.boot.weixin.util;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import io.leopard.boot.weixin.model.message.WeixinMessage;
import io.leopard.json.Json;

public class WeixinMessageParser {
	public static final XmlMapper xmlMapper = new XmlMapper();

	private static ObjectWriter writer;

	static {
		xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

		writer = xmlMapper.writer().withDefaultPrettyPrinter();

		// XML标签名:使用骆驼命名的属性名，
		// xmlMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		// xmlMapper.enable(MapperFeature.USE_STD_BEAN_NAMING);
	}

	public static String toFormatXml(Object obj) {
		if (obj == null) {
			return null;
		}
		try {
			return writer.writeValueAsString(obj);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static String toXml(Object obj) {
		if (obj == null) {
			return null;
		}
		try {
			return xmlMapper.writeValueAsString(obj);
		}
		catch (JsonProcessingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static String toJson(String xml) throws IOException {
		JsonNode node = xmlMapper.readTree(xml.getBytes());

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		String json = mapper.writeValueAsString(node);
		return json;
	}

	public static <E> E parse(String xml, Class<E> clazz) throws JsonParseException, JsonMappingException, IOException {
		String json = toJson(xml);
		return Json.toObject(json, clazz, true);
	}

	public static WeixinMessage parse(String xml) throws JsonParseException, JsonMappingException, IOException {
		String json = toJson(xml);
		// System.err.println("json:" + json);
		return Json.toObject(json, WeixinMessage.class);
		// return xmlMapper.readValue(xml, WeixinMessage.class);
	}

	// public static WeixinMessage parseXml(String xml) throws IOException {
	// Element element = XmlUtils.toRootElement(xml);
	//
	// // Element element = XmlUtils.read(input).getDocumentElement();
	// // input.close();
	//
	// String toUserName = element.getElementsByTagName("ToUserName").item(0).getTextContent();
	// String fromUserName = element.getElementsByTagName("FromUserName").item(0).getTextContent();
	// long createTime = Long.parseLong(element.getElementsByTagName("CreateTime").item(0).getTextContent());
	// String msgType = element.getElementsByTagName("MsgType").item(0).getTextContent();
	// WeixinMessage postBean = new WeixinMessage();
	// postBean.setToUserName(toUserName);
	// postBean.setFromUserName(fromUserName);
	// postBean.setCreateTime(createTime);
	// postBean.setMsgType(msgType);
	// if (msgType.equals("voice")) {
	// String recognition = element.getElementsByTagName("Recognition").item(0).getTextContent();
	// postBean.setContent(recognition);
	// }
	// else {
	// String content = element.getElementsByTagName("Content").item(0).getTextContent();
	// postBean.setContent(content);
	// }
	// return postBean;
	// }
}
