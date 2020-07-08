package io.leopard.web.mvc.json.common;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import io.leopard.web.mvc.json.AbstractJsonSerializer;

/**
 * 身份证号码脱敏
 * 
 * @author 谭海潮
 *
 */
public class IdCardNumberMaskJsonSerializer extends AbstractJsonSerializer<String> {

	@Override
	public void out(String idCardNumber, JsonGenerator gen, SerializerProvider serializers) throws Exception {
		if (StringUtils.isEmpty(idCardNumber)) {
			gen.writeObject(idCardNumber);
		}
		else {
			// TODO 这里要判断身份证合法性？
			if (idCardNumber.length() != 18) {
				throw new RuntimeException("身份证号码不合法.");
			}
			String left = StringUtils.left(idCardNumber, 6);
			String mask = StringUtils.repeat('*', idCardNumber.length() - 6);
			String idCardNumberMask = left + mask;
			gen.writeObject(idCardNumberMask);
		}
	}

}
