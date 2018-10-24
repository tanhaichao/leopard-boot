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

	@Override
	protected AdditionalField<VALUE> getAdditionalField(KEY id, JsonGenerator gen) throws Exception {
		TYPE type = parseType(gen);
		if (type == null) {
			return null;
		}

		String fieldName = this.getAdditionalFieldName(type);
		VALUE value = this.getData(type, id);
		AdditionalField<VALUE> field = new AdditionalField<VALUE>();
		field.setFieldName(fieldName);
		field.setValue(value);
		return field;
	}

	/**
	 * 解析类型
	 */
	protected TYPE parseType(JsonGenerator gen) throws NoSuchFieldException, SecurityException, IllegalAccessException {
		String typeFieldName = this.getTypeFieldName();
		if (StringUtils.isEmpty(typeFieldName)) {
			throw new RuntimeException("类型属性名称不能为空.");
		}
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
	 * 获取类型属性名称
	 * 
	 * @return
	 */
	protected abstract String getTypeFieldName();

	/**
	 * 获取追加的属性名称
	 * 
	 * @return
	 */
	protected abstract String getAdditionalFieldName(TYPE type);

	/**
	 * 获取附加属性值
	 * 
	 * @return
	 */
	protected abstract VALUE getData(TYPE type, KEY id);
}
