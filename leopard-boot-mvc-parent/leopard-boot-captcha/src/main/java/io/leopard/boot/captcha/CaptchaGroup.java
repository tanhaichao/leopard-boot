package io.leopard.boot.captcha;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CaptchaGroup {

	// FIXME value改成默认必填
	String value() default "";

	// FIXME uri有啥用？
	String uri() default "";

}
