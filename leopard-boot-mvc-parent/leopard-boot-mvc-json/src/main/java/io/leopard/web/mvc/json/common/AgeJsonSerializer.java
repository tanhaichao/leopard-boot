package io.leopard.web.mvc.json.common;

import java.lang.reflect.Field;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import io.leopard.web.mvc.json.AbstractJsonSerializer;
import io.leopard.web.mvc.json.AsJsonSerializer;

public class AgeJsonSerializer extends AbstractJsonSerializer<Object> {

	@Override
	public void out(Object value, JsonGenerator gen, SerializerProvider serializers) throws Exception {
		gen.writeObject(value);

		Field field = AsJsonSerializer.getCurrentField(gen);
		field.setAccessible(true);
		AgeJsonSerialize ageJsonSerialize = field.getAnnotation(AgeJsonSerialize.class);
		String fieldName = ageJsonSerialize.name();
		gen.writeFieldName(fieldName);
		gen.writeObject(18);
	}

}
