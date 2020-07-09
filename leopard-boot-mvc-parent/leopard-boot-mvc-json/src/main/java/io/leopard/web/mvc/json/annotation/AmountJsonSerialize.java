package io.leopard.web.mvc.json.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.leopard.web.mvc.json.common.AmountJsonSerializer;

/**
 * 根据价格和数量字段计算金额
 * 
 * @author 谭海潮
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = AmountJsonSerializer.class)
public @interface AmountJsonSerialize {

	/**
	 * 数量字段
	 * 
	 * @return
	 */
	String quantity() default "quantity";

	/**
	 * 价格字段
	 * 
	 * @return
	 */
	String price() default "price";

}
