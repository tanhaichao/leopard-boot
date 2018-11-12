package io.leopard.boot.requestmapping;

import java.lang.reflect.Method;

/**
 * ContextPath开关
 * 
 * @author 谭海潮
 *
 */
public interface ContextPathSwitchgear {

	Boolean isEnableContextPath(Method method, Class<?> handlerType);

}
