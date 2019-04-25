package io.leopard.web.mvc.json;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ImageSerializer extends AbstractJsonSerializer<String> {

	@Autowired
	private ImageUrlConverter imageUrlConverter;

	@Override
	public void out(String uri, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		String url = imageUrlConverter.convert(uri);
		jgen.writeString(url);
	}

}
