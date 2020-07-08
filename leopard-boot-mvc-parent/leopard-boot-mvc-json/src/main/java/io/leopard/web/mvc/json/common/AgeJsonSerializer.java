package io.leopard.web.mvc.json.common;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import io.leopard.web.mvc.json.annotation.AgeJsonSerialize;

/**
 * 根据生日输出年龄
 * 
 * @author 谭海潮
 *
 */
public class AgeJsonSerializer extends JsonSerializer<Date> implements ContextualSerializer {

	private AgeJsonSerialize ageJsonSerialize;

	public AgeJsonSerializer() {
		// new Exception("here1").printStackTrace();
	}

	public AgeJsonSerializer(AgeJsonSerialize ageJsonSerialize) {
		// new Exception("here2").printStackTrace();
		this.ageJsonSerialize = ageJsonSerialize;
	}

	@Override
	public void serialize(Date birthDate, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		if (ageJsonSerialize == null) {
			gen.writeObject(birthDate);
			return;
		}

		// Field field = AsJsonSerializer.getCurrentField(gen);
		// field.setAccessible(true);
		// AgeJsonSerialize ageJsonSerialize = field.getAnnotation(AgeJsonSerialize.class);

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

	@Override
	public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
		if (beanProperty != null) {
			// System.err.println("beanProperty:" + beanProperty + " name:" + beanProperty.getName() + " type:" + beanProperty.getMember().getGenericType());
			AgeJsonSerialize anno = beanProperty.getAnnotation(AgeJsonSerialize.class);
			if (anno == null) {
				anno = beanProperty.getContextAnnotation(AgeJsonSerialize.class);
			}
			if (anno != null) {
				return new AgeJsonSerializer(anno);
			}
			return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
		}
		return serializerProvider.findNullValueSerializer(beanProperty);
	}

}
