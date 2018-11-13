package io.leopard.boot.vhost;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 虚拟主机
 * 
 * @author 谭海潮
 *
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(VhostExcludeUrls.class)
public @interface VhostExcludeUrl {

	/**
	 * 排除的URL
	 * 
	 * @return
	 */
	String[] value();

}
