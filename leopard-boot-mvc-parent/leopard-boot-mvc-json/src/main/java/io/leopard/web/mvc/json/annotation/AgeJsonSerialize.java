package io.leopard.web.mvc.json.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.leopard.web.mvc.json.common.AgeJsonSerializer;

/**
 * 根据生日输入年龄
 * 
 * @author 谭海潮
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JsonSerialize(using = AgeJsonSerializer.class)
public @interface AgeJsonSerialize {

	/**
	 * 字段名称
	 * 
	 * @return
	 */
	String name() default "age";

	/**
	 * 是否忽略当前字段值
	 * 
	 * @return
	 */
	boolean ignore() default false;

}
