package io.leopard.web.mvc.json.common;

import java.lang.reflect.Field;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import io.leopard.boot.util.DecimalUtil;
import io.leopard.web.mvc.json.AbstractJsonSerializer;
import io.leopard.web.mvc.json.annotation.AmountJsonSerialize;

/**
 * 计算金额
 * 
 * @author 谭海潮
 *
 */
public class AmountJsonSerializer extends AbstractJsonSerializer<Double> implements ContextualSerializer {

	private AmountJsonSerialize amountJsonSerialize;

	public AmountJsonSerializer() {
	}

	public AmountJsonSerializer(AmountJsonSerialize amountJsonSerialize) {
		this.amountJsonSerialize = amountJsonSerialize;
	}

	@Override
	public void out(Double amount, JsonGenerator gen, SerializerProvider serializers) throws Exception {
		Object currentValue = gen.getOutputContext().getCurrentValue();
		if (currentValue == null) {
			gen.writeObject(amount);
			return;
		}
		String quantifyFieldName = amountJsonSerialize.quantity();
		String priceFieldName = amountJsonSerialize.price();

		int quantify = (int) this.resolveFieldValue(currentValue, quantifyFieldName);
		double price = (double) this.resolveFieldValue(currentValue, priceFieldName);
		amount = DecimalUtil.multiply(quantify, price);// 忽略传入的金额
		gen.writeObject(amount);
	}

	/**
	 * 解析属性值
	 * 
	 * @param currentValue
	 * @param fieldName
	 * @return
	 */
	protected Object resolveFieldValue(Object currentValue, String fieldName) {
		Class<?> clazz = currentValue.getClass();
		try {
			Field field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			return field.get(currentValue);
		}
		catch (NoSuchFieldException e) {
			throw new RuntimeException("属性[" + fieldName + "]不存在.");
		}
		catch (SecurityException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		catch (IllegalAccessException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
		if (beanProperty != null) {
			// System.err.println("beanProperty:" + beanProperty + " name:" + beanProperty.getName() + " type:" + beanProperty.getMember().getGenericType());
			AmountJsonSerialize anno = beanProperty.getAnnotation(AmountJsonSerialize.class);
			if (anno == null) {
				anno = beanProperty.getContextAnnotation(AmountJsonSerialize.class);
			}
			if (anno != null) {
				return new AmountJsonSerializer(anno);
			}
			return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
		}
		return serializerProvider.findNullValueSerializer(beanProperty);
	}
}
