package io.leopard.web.mvc.json;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.core.JsonGenerator;

import io.leopard.web.mvc.json.model.AdditionalField;

/**
 * 按类型查询数据
 * 
 * @author 谭海潮
 *
 * @param <T>
 */
public abstract class TypeJsonSerializer<KEY, VALUE, TYPE> extends AdditionalFieldJsonSerializer<KEY, VALUE> {
	protected static Log logger = LogFactory.getLog(TypeJsonSerializer.class);

	/**
	 * 类型属性名称
	 */
	protected String typeFieldName;

	/**
	 * 追加的属性名称
	 * 
	 * @return
	 */
	protected String additionalFieldName;

	/**
	 * 
	 * @param typeFieldName 类型属性名称
	 * @param additionalFieldName 追加的属性名称
	 */
	public TypeJsonSerializer(String typeFieldName, String additionalFieldName) {
		if (StringUtils.isEmpty(typeFieldName)) {
			throw new RuntimeException("类型属性名称不能为空.");
		}
		if (StringUtils.isEmpty(additionalFieldName)) {
			throw new RuntimeException("追加的属性名称不能为空.");
		}
		this.typeFieldName = typeFieldName;
		this.additionalFieldName = additionalFieldName;
	}

	@Override
	protected AdditionalField<VALUE> getAdditionalField(KEY id, JsonGenerator gen) throws Exception {
		TYPE type = parseType(gen);
		if (type == null) {
			return null;
		}

		VALUE value = this.get(type, id);
		AdditionalField<VALUE> field = new AdditionalField<VALUE>();
		field.setFieldName(additionalFieldName);
		field.setValue(value);
		return field;
	}

	/**
	 * 解析类型
	 */
	protected TYPE parseType(JsonGenerator gen) throws NoSuchFieldException, SecurityException, IllegalAccessException {
		Object currentValue = gen.getOutputContext().getCurrentValue();
		if (currentValue == null) {
			return null;
		}
		Class<?> clazz = currentValue.getClass();
		Field field = clazz.getDeclaredField(typeFieldName);
		field.setAccessible(true);
		@SuppressWarnings("unchecked")
		TYPE type = (TYPE) field.get(currentValue);
		return type;
	}

	/**
	 * 获取附加属性值
	 * 
	 * @return
	 */
	protected abstract VALUE get(TYPE type, KEY id);
}
