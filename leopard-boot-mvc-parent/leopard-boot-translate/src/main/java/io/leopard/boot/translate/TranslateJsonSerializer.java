package io.leopard.boot.translate;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import io.leopard.web.mvc.json.AbstractJsonSerializer;

/**
 * 翻译
 * 
 * @author 谭海潮
 *
 */
public class TranslateJsonSerializer extends AbstractJsonSerializer<String> {

	@Autowired(required = false)
	private Translater translater;

	// @Override
	// public void serialize(String chinese, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
	// String english = translater.translate(chinese);
	// if (english == null) {
	// gen.writeString(chinese);
	// }
	// else {
	// gen.writeString(english);
	// }
	// }

	@Override
	public void out(String chinese, JsonGenerator gen, SerializerProvider serializers) throws Exception {
		String english = null;
		if (translater != null) {
			english = translater.translate(chinese);
		}
		if (english == null) {
			gen.writeString(chinese);
		}
		else {
			gen.writeString(english);
		}
	}

}
