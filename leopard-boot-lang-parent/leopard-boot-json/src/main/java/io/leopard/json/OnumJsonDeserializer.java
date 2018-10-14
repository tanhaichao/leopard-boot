package io.leopard.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.leopard.lang.inum.EnumUtil;
import io.leopard.lang.inum.Inum;
import io.leopard.lang.inum.Onum;
import io.leopard.lang.inum.Snum;

/**
 * Onum Json反序列化
 * 
 * @author 谭海潮
 *
 */
public class OnumJsonDeserializer extends JsonDeserializer<Onum<?, ?>> {

	private Class<?> clazz;

	public OnumJsonDeserializer(Class<?> clazz) {
		this.clazz = clazz;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Onum<?, ?> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		JsonToken currentToken = jp.getCurrentToken();
		// System.err.println("getCurrentName:" + jp.getCurrentName());
		{
			// JsonNode node = jp.getCodec().readTree(jp);
			if (currentToken.equals(JsonToken.START_OBJECT)) {
				return this.parseOnum(jp, ctxt);
			}
		}

		if (Inum.class.isAssignableFrom(clazz)) {
			int key = jp.getIntValue();
			return (Onum<?, ?>) EnumUtil.toEnum(key, (Class<? extends Enum>) clazz);

		}
		else if (Snum.class.isAssignableFrom(clazz)) {
			// System.err.println("key:"+jp.getValueAsString());
			String key = jp.getText();
			return (Onum<?, ?>) EnumUtil.toEnum(key, (Class<? extends Enum>) clazz);
		}
		throw ctxt.mappingException("未知枚举类型[" + clazz.getName() + "].");

		// node.getNodeType().
		// if (currentToken == JsonToken.VALUE_STRING) {
		// return new TextContainer(jp.getText().trim(), null);
		// }
		// else if (currentToken == JsonToken.START_OBJECT) {
		// JsonToken jsonToken = jp.nextToken();
		// if (jsonToken == JsonToken.FIELD_NAME) {
		// String operation = jp.getText().trim();
		// jp.nextToken();
		// String text = jp.getText().trim();
		// jp.nextToken();
		// return new TextContainer(text, operation);
		// }
		// }

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Onum<?, ?> parseOnum(JsonParser jp, DeserializationContext ctxt) throws JsonParseException, IOException {
		String fieldName = jp.getCurrentName();
		JsonToken currentToken = jp.nextToken();
		if (currentToken.equals(JsonToken.END_OBJECT)) {
			return null;// 空对象直接返回null
		}

		this.nextToKey(jp, ctxt);
		// if (!"key".equals(jp.getCurrentName())) {
		// throw ctxt.mappingException("枚举[" + fieldName + "]的key字段必须要放在最前面.");
		// }

		jp.nextValue();

		Object key;
		if (Inum.class.isAssignableFrom(clazz)) {
			key = jp.getIntValue();
		}
		else if (Snum.class.isAssignableFrom(clazz)) {
			key = jp.getValueAsString();
		}
		else {
			throw ctxt.mappingException("未知枚举类型[" + clazz.getName() + "].");
		}
		// System.err.println("key:" + key + " name:" + jp.getCurrentName() + " value:" + jp.getCurrentValue() + " text:" + jp.getText());
		this.nextToClose(jp, ctxt);
		if (key == null) {
			return null;
		}
		return (Onum<?, ?>) EnumUtil.toEnum(key, (Class<? extends Enum>) clazz);
		// System.err.println("currentToken name:" + jp.getCurrentName() + " token:" + currentToken.name());
	}

	protected void nextToKey(JsonParser jp, DeserializationContext ctxt) throws IOException {
		// if (!"key".equals(jp.getCurrentName())) {
		// throw ctxt.mappingException("枚举[" + fieldName + "]的key字段必须要放在最前面.");
		// }

		while (true) {
			if ("key".equals(jp.getCurrentName())) {
				break;
			}
			else {
				System.err.println("jp.getCurrentName():" + jp.getCurrentName());
				jp.nextToken();
				// throw ctxt.mappingException("枚举[" + fieldName + "]的key字段必须要放在最前面.");
			}
		}
	}

	protected void nextToClose(JsonParser jp, DeserializationContext ctxt) throws JsonMappingException, IOException {
		while (true) {
			JsonToken token = jp.nextToken();// }
			if (token == JsonToken.END_OBJECT) {
				break;
			}
		}
	}
}