package io.leopard.web.mvc.json.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.leopard.web.mvc.json.common.AgeJsonSerializer;

@Target({ ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotation
@JsonSerialize(using = AgeJsonSerializer.class)
public @interface AgeJsonSerialize {

	/**
	 * 字段名称
	 * 
	 * @return
	 */
	String name() default "age";

}
