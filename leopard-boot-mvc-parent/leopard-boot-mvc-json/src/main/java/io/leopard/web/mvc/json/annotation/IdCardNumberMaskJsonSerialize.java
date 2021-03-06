package io.leopard.web.mvc.json.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.leopard.web.mvc.json.common.IdCardNumberMaskJsonSerializer;

/**
 * 身份证号码脱敏
 * 
 * @author 谭海潮
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = IdCardNumberMaskJsonSerializer.class)
public @interface IdCardNumberMaskJsonSerialize {

}
