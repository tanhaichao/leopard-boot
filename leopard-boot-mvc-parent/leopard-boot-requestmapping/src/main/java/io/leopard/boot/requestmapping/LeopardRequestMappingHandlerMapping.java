package io.leopard.boot.requestmapping;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo.BuilderConfiguration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

public class LeopardRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

	@Value("${mvc.restful:true}") // 默认为true
	private boolean restful;

	@Autowired(required = false)
	private List<RequestMappingInfoCombiner> combinerList;
	// @Autowired
	// private RequestMappingInfoBuilder requestMappingInfoBuilder;

	public RequestMappingInfo.BuilderConfiguration getConfig() {
		try {
			Field field = RequestMappingHandlerMapping.class.getDeclaredField("config");
			field.setAccessible(true);
			return (BuilderConfiguration) field.get(this);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
		RequestMappingInfo info = createRequestMappingInfo(method);
		if (info != null) {
			RequestMappingInfo typeInfo = createRequestMappingInfo(handlerType);
			if (typeInfo != null) {
				info = typeInfo.combine(info);
			}
		}
		if (info != null) {
			if (combinerList != null) {
				for (RequestMappingInfoCombiner combiner : combinerList) {
					info = combiner.combine(info, method, handlerType);
				}
			}
		}
		return info;
		// RequestMappingInfo info = null;
		// RequestMapping methodAnnotation = AnnotationUtils.findAnnotation(method, RequestMapping.class);
		// if (methodAnnotation != null) {
		// info = createRequestMappingInfo2(methodAnnotation, method);
		// RequestMapping typeAnnotation = AnnotationUtils.findAnnotation(handlerType, RequestMapping.class);
		// if (typeAnnotation != null) {
		// info = createRequestMappingInfo2(typeAnnotation, null).combine(info);
		// }
		// }
		// return info;
	}

	/**
	 * Delegates to {@link #createRequestMappingInfo(RequestMapping, RequestCondition)}, supplying the appropriate custom {@link RequestCondition} depending on whether the supplied
	 * {@code annotatedElement} is a class or method.
	 * 
	 * @see #getCustomTypeCondition(Class)
	 * @see #getCustomMethodCondition(Method)
	 */
	private RequestMappingInfo createRequestMappingInfo(AnnotatedElement element) {
		RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(element, RequestMapping.class);
		if (requestMapping == null) {
			return null;
		}
		if (element instanceof Class) {
			return createClassRequestMappingInfo(requestMapping, (Class<?>) element);
		}
		else {
			return createMethodRequestMappingInfo(requestMapping, (Method) element);
		}
	}

	protected RequestMappingInfo createClassRequestMappingInfo(RequestMapping requestMapping, Class<?> clazz) {
		RequestCondition<?> condition = getCustomTypeCondition(clazz);
		return createRequestMappingInfo(requestMapping, condition);
	}

	protected String[] parsePatterns(RequestMapping requestMapping, Method method) {
		String[] patterns;
		if (method != null && requestMapping.path().length == 0) {
			patterns = new String[] { this.createPattern(method.getName()) };
		}
		else {
			patterns = resolveEmbeddedValuesInPatterns(requestMapping.path());
		}
		return patterns;
	}

	protected RequestMappingInfo createMethodRequestMappingInfo(RequestMapping requestMapping, Method method) {
		String[] patterns = this.parsePatterns(requestMapping, method);

		String[] produces = requestMapping.produces();
		if (true) {
			if (produces == null || produces.length == 0) {
				ResponseBody responseBody = method.getAnnotation(ResponseBody.class);
				if (responseBody != null) {// 让所有ResponseBody以JSON格式返回
					produces = new String[] { "application/json;charset=UTF-8" };
				}
			}
		}

		RequestCondition<?> customCondition = getCustomMethodCondition(method);
		return RequestMappingInfo.paths(resolveEmbeddedValuesInPatterns(patterns)).methods(requestMapping.method()).params(requestMapping.params()).headers(requestMapping.headers())
				.consumes(requestMapping.consumes()).produces(produces).mappingName(requestMapping.name()).customCondition(customCondition).options(this.getConfig()).build();
	}

	protected String createPattern(String methodName) {
		if (restful) {
			return methodName;
		}
		return methodName + ".do";
	}
}
