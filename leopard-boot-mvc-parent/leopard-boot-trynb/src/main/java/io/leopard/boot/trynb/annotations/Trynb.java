package io.leopard.boot.trynb.annotations;

import java.lang.annotation.Documented;
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
@Documented
@Repeatable(Trynbs.class)
public @interface Trynb {

	/**
	 * 异常类型
	 * 
	 * @return
	 */
	Class<? extends Exception> value();

	/**
	 * 日志级别
	 * 
	 * @return
	 */
	LogLevel level() default LogLevel.WARN;

	/**
	 * 是否打印堆栈信息
	 * 
	 * @return
	 */
	boolean printStackTrace() default false;

	/**
	 * 错误日志
	 * 
	 * @return 默认为原始日志
	 */
	String message() default "";

}
