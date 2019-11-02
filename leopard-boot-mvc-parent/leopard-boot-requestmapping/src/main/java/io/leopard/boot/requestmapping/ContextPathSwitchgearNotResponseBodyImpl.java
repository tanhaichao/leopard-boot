package io.leopard.boot.requestmapping;

import java.lang.reflect.Method;

import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 没有ResponseBody注解的方法，不启用contextPath
 * 
 * @author 谭海潮
 *
 */
@Component
public class ContextPathSwitchgearNotResponseBodyImpl implements ContextPathSwitchgear {

	@Override
	public Boolean isEnableContextPath(Method method, Class<?> handlerType) {
		if (handlerType.equals(BasicErrorController.class)) {
			return false;
		}
		if (method.getAnnotation(ResponseBody.class) == null) {
			return false;
		}
		return null;
	}

}
