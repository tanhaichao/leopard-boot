package io.leopard.boot.weixin.model.message;

import java.util.HashMap;
import java.util.Map;

import io.leopard.json.SubclassJsonDeserializer;

public class WeixinMessageJsonDeserializer extends SubclassJsonDeserializer<WeixinMessage> {

	private static Map<String, Class<? extends WeixinMessage>> CLAZZ_MAPPING = new HashMap<String, Class<? extends WeixinMessage>>();

	static {
		CLAZZ_MAPPING.put("voice", VoiceWeixinMessage.class);
		CLAZZ_MAPPING.put("event", EventWeixinMessage.class);
	}

	@Override
	protected String getTypeFieldName() {
		return "MsgType";
	}

	@Override
	protected Class<WeixinMessage> findClass(String type) {
		@SuppressWarnings("unchecked")
		Class<WeixinMessage> clazz = (Class<WeixinMessage>) CLAZZ_MAPPING.get(type);
		if (clazz == null) {
			throw new UnsupportedOperationException("未知节点类型[" + type + "].");
		}
		return clazz;
	}

}
