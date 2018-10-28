package io.leopard.boot.xparam.resolver;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ValueConstants;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver;

import io.leopard.boot.requestbody.RequestBodyParser;

/**
 * 基本数据类型
 * 
 * @author 谭海潮
 *
 */
@Component // 之前为什么把这个注解去掉?
@Order(7)
public class PrimitiveMethodArgumentResolver extends AbstractNamedValueMethodArgumentResolver implements XParamResolver {
	protected Log logger = LogFactory.getLog(this.getClass());

	private static final Set<Class<?>> SUPPORT_TYPE_SET = new HashSet<>();
	static {
		SUPPORT_TYPE_SET.add(long.class);
		SUPPORT_TYPE_SET.add(Long.class);
		SUPPORT_TYPE_SET.add(int.class);
		SUPPORT_TYPE_SET.add(Integer.class);
		SUPPORT_TYPE_SET.add(float.class);
		SUPPORT_TYPE_SET.add(Float.class);
		SUPPORT_TYPE_SET.add(double.class);
		SUPPORT_TYPE_SET.add(Double.class);
		SUPPORT_TYPE_SET.add(boolean.class);
		SUPPORT_TYPE_SET.add(Boolean.class);
		SUPPORT_TYPE_SET.add(Date.class);
		SUPPORT_TYPE_SET.add(String.class);
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		RequestParam ann = parameter.getParameterAnnotation(RequestParam.class);
		if (ann != null) {
			return false;
		}
		// logger.info("supportsParameter name:" + parameter.getParameterName() + " clazz:" + parameter.getParameterType());

		Class<?> clazz = parameter.getParameterType();
		return SUPPORT_TYPE_SET.contains(clazz);
	}

	@Override
	protected NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
		return new RequestParamNamedValueInfo();
	}

	@Override
	protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest webRequest) throws Exception {
		// System.err.println("PrimitiveMethodArgumentResolver resolveName name:" + name);
		// if (UnderlineHandlerMethodArgumentResolver.isEnable()) {
		// }
		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
		// Object value = request.getParameter(name);
		Object value = RequestBodyParser.getParameter(request, name);
		if (value == null) {
			value = this.getDefaultValue(parameter);
		}
		// logger.info("resolveName name:" + name + " clazz:" + parameter.getParameterType());

		return value;
	}

	protected Object getDefaultValue(MethodParameter parameter) {
		Class<?> clazz = parameter.getParameterType();
		if (clazz.equals(long.class)) {
			return 0L;
		}
		else if (clazz.equals(int.class)) {
			return 0;
		}
		else if (clazz.equals(double.class)) {
			return 0D;
		}
		else if (clazz.equals(float.class)) {
			return 0f;
		}
		else if (clazz.equals(boolean.class)) {
			return false;
		}
		else if (clazz.equals(String.class)) {
			return null;
		}
		else if (clazz.equals(Boolean.class)) {
			return null;
		}
		else if (clazz.equals(Integer.class)) {
			return null;
		}
		else if (clazz.equals(Long.class)) {
			return null;
		}
		else if (clazz.equals(Float.class)) {
			return null;
		}
		else if (clazz.equals(Double.class)) {
			return null;
		}
		else if (clazz.equals(Date.class)) {
			return null;
		}
		throw new RuntimeException("未知类型[" + clazz.getName() + "].");
	}

	@Override
	protected void handleMissingValue(String name, MethodParameter parameter) throws ServletException {

	}

	//
	private static class RequestParamNamedValueInfo extends NamedValueInfo {

		public RequestParamNamedValueInfo() {
			super("", false, ValueConstants.DEFAULT_NONE);
		}

		public RequestParamNamedValueInfo(RequestParam annotation) {
			super(annotation.name(), annotation.required(), annotation.defaultValue());
		}
	}
}
