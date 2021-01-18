package io.leopard.boot.trynb.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.logging.LogLevel;

/**
 * Trynb异常拦截
 * 
 * @author 谭海潮
 *
 */
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Trynb(value = NoSuchMethodException.class, printStackTrace = false, level = LogLevel.WARN)
@Repeatable(TrynbWarns.class)
public @interface TrynbWarn {

	/**
	 * 异常类型
	 * 
	 * @return
	 */
	// @AliasFor("value")
	Class<? extends Exception> value() default UnsupportedOperationException.class;

	/**
	 * 错误日志
	 * 
	 * @return 默认为原始日志
	 */
	String message() default "";
}
