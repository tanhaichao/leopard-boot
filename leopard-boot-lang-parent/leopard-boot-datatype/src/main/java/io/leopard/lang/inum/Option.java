package io.leopard.lang.inum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启枚举接口(可通过URL访问枚举元素数据)
 * 
 * @author 谭海潮
 *
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Option {

	/**
	 * URL值
	 * 
	 * @return
	 */
	String value() default "";

}
