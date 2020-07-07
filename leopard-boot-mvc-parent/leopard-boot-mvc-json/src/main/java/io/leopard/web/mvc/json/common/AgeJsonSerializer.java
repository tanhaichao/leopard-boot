package io.leopard.web.mvc.json.common;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import io.leopard.web.mvc.json.AbstractJsonSerializer;
import io.leopard.web.mvc.json.AsJsonSerializer;
import io.leopard.web.mvc.json.annotation.AgeJsonSerialize;

public class AgeJsonSerializer extends AbstractJsonSerializer<Date> {

	@Override
	public void out(Date birthDate, JsonGenerator gen, SerializerProvider serializers) throws Exception {
		Field field = AsJsonSerializer.getCurrentField(gen);
		field.setAccessible(true);
		AgeJsonSerialize ageJsonSerialize = field.getAnnotation(AgeJsonSerialize.class);

		boolean ignore = ageJsonSerialize.ignore();
		if (ignore) {
			gen.writeNumber(0);// 隐藏生日
		}
		else {
			gen.writeObject(birthDate);
		}

		int age = getAge(birthDate);
		String fieldName = ageJsonSerialize.name();
		gen.writeFieldName(fieldName);
		gen.writeObject(age);
	}

	/**
	 * 根据生日获取年龄
	 * 
	 * @param birth
	 * @return
	 * @throws Exception
	 */
	public static Integer getAge(Date birth) {
		Calendar now = Calendar.getInstance();
		Calendar born = Calendar.getInstance();
		now.setTime(new Date());
		born.setTime(birth);
		if (born.after(now)) {
			throw new IllegalArgumentException("Can't be born in the future");
		}
		int age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
		if (now.get(Calendar.DAY_OF_YEAR) < born.get(Calendar.DAY_OF_YEAR)) {
			age -= 1;
		}
		return age;
	}
}
