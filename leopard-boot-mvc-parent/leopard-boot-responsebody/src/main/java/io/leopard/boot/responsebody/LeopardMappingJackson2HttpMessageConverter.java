package io.leopard.boot.responsebody;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LeopardMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

	public LeopardMappingJackson2HttpMessageConverter(ObjectMapper objectMapper) {
		super(objectMapper);
		// logger.info("XmlMapper:" + XmlMapper.class.getName());
		// setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_HTML));
	}

	/**
	 * 对象做JSON序列化时，某个字段可能会出现异常，导致输出的JSON错乱
	 * 
	 * @param object
	 * @param type
	 * @param outputMessage
	 * @throws IOException
	 * @throws HttpMessageNotWritableException
	 */
	protected void _writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
		// outputMessage.getBody()
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		HttpOutputMessageWrapper outputMessageWrapper = new HttpOutputMessageWrapper(outputMessage, output);

		try {
			super.writeInternal(object, type, outputMessageWrapper);
		}
		catch (HttpMessageNotWritableException e) {
			Throwable t = e.getCause();
			if (t instanceof JsonMappingException) {
				throw (JsonMappingException) t;
			}
			throw e;
		}
		catch (RuntimeException e) {
			throw e;
		}

		outputMessage.getBody().write(output.toByteArray());
	}

	@Override
	protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
		this._writeInternal(object, type, outputMessage);
		// System.err.println("writeInternal:" + Json.toFormatJson(object));
		// super.writeInternal(object, type, outputMessage);// FIXME JSON序列化异常时，JSON会错乱
	}

}
