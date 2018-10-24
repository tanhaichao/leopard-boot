package io.leopard.web.mvc.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import io.leopard.web.mvc.json.model.AdditionalField;

/**
 * 附加字段
 * 
 * @author 谭海潮
 *
 */
public abstract class AdditionalFieldJsonSerializer<KEY, VALUE> extends AbstractJsonSerializer<KEY> {

	@Override
	public void out(KEY id, JsonGenerator gen, SerializerProvider serializers) throws Exception {
		gen.writeObject(id);

		AdditionalField<VALUE> additionalField = this.getAdditionalField(id, gen);
		if (additionalField != null) {
			gen.writeFieldName(additionalField.getFieldName());
			gen.writeObject(additionalField.getValue());
		}
	}

	/**
	 * 获取附加属性值
	 * 
	 * @param value
	 * @param gen
	 * @return
	 */
	protected abstract AdditionalField<VALUE> getAdditionalField(KEY id, JsonGenerator gen) throws Exception;

	/**
	 * 获取附加属性名称
	 * 
	 * @param gen
	 * @return
	 */
	protected String parseAdditionalFieldName(JsonGenerator gen) {
		String fieldName = gen.getOutputContext().getCurrentName();
		if ("uid".equals(fieldName)) {
			return "user";
		}
		if ("uidList".equals(fieldName)) {
			return "userList";
		}
		return fieldName.replace("Id", "");
	}
}
