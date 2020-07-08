package io.leopard.web.mvc.json.common;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import io.leopard.web.mvc.json.AbstractJsonSerializer;

/**
 * 手机号码脱敏
 * 
 * @author 谭海潮
 *
 */
public class MobileMaskJsonSerializer extends AbstractJsonSerializer<String> {

	@Override
	public void out(String mobile, JsonGenerator gen, SerializerProvider serializers) throws Exception {
		if (StringUtils.isEmpty(mobile)) {
			gen.writeObject(mobile);
		}
		else {
			// TODO 这里要判断手机号码是合法性？
			if (mobile.length() != 11) {
				throw new RuntimeException("手机号码不合法.");
			}
			String left = StringUtils.left(mobile, 3);
			String mask = StringUtils.repeat('*', mobile.length() - 7);
			String right = StringUtils.right(mobile, 4);
			String idCardNumberMask = left + mask + right;
			gen.writeObject(idCardNumberMask);
		}
	}

}
