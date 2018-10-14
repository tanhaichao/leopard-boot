package io.leopard.boot.servlet.util;

import java.lang.reflect.Method;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Spring组件操作
 * 
 * @author 谭海潮
 *
 */
public class ComponentUtil {
	public static boolean isNotController(Class<?> clazz) {
		return !isController(clazz);
	}

	public static boolean isController(Class<?> clazz) {
		Controller controller = clazz.getAnnotation(Controller.class);
		return (controller != null);
	}

	public static boolean isNotResponseBody(Method method) {
		return !isResponseBody(method);
	}

	public static boolean isResponseBody(Method method) {
		ResponseBody responseBody = method.getAnnotation(ResponseBody.class);
		return (responseBody != null);
	}
}
