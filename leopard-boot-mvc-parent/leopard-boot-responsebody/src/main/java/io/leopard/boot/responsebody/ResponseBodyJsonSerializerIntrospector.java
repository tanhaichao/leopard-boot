package io.leopard.boot.responsebody;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.AnnotationMap;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

/**
 * 为了能识别自定义的JsonSerialize注解
 * 
 * @author 谭海潮
 *
 */
public class ResponseBodyJsonSerializerIntrospector extends JacksonAnnotationIntrospector {

	private static final long serialVersionUID = 1L;

	private CustomJsonSerializeAnnotationCache customJsonSerializeAnnotationCache = new CustomJsonSerializeAnnotationCache();

	@Override
	public Object findSerializer(Annotated a) {
		// System.err.println("a:" + a);
		if (a instanceof AnnotatedMethod) {// 在field上加的注解，会变成AnnotatedMethod
			AnnotatedMethod annotated = ((AnnotatedMethod) a);
			// if ("getBirthDate".equals(annotated.getName())) {
			// System.err.println("getBirthDate annotated:" + annotated.hashCode());
			JsonSerialize customJsonSerializeAnnotation = this.findCustomJsonSerializeAnnotation(annotated);
			// JsonSerialize ann = _findAnnotation(a, JsonSerialize.class);
			if (customJsonSerializeAnnotation != null) {
				@SuppressWarnings("rawtypes")
				Class<? extends JsonSerializer> serClass = customJsonSerializeAnnotation.using();
				if (serClass != JsonSerializer.None.class) {
					return serClass;
				}
			}
			// }
		}
		// else if (a instanceof AnnotatedField) {
		// AnnotatedField annotated = ((AnnotatedField) a);
		//
		// System.err.print("fieldName:" + annotated.getName() + " annotated:" + annotated);
		// }
		return super.findSerializer(a);
	}

	/**
	 * 返回第一个自定义的JsonSerialize注解
	 * 
	 * @param annotated
	 * @return
	 */
	protected JsonSerialize findCustomJsonSerializeAnnotation(AnnotatedMethod annotated) {
		AnnotationMap annotationMap = annotated.getAllAnnotations();
		if (annotationMap.size() == 0) {
			return null;
		}
		Iterator<Annotation> iterator = annotationMap.annotations().iterator();
		while (iterator.hasNext()) {
			Annotation annotation = iterator.next();
			JsonSerialize customJsonSerializeAnnotation = customJsonSerializeAnnotationCache.findCustomJsonSerializeAnnotation(annotation);
			if (customJsonSerializeAnnotation != null) {
				// System.err.println("customJsonSerializeAnnotation:" + customJsonSerializeAnnotation.annotationType().getName() + " " + customJsonSerializeAnnotation);
				return customJsonSerializeAnnotation;
			}
		}
		return null;
	}

	/**
	 * 判断一个注解是否为自定义的JsonSerialize
	 * 
	 * @author 谭海潮
	 *
	 */
	public static class CustomJsonSerializeAnnotationCache {

		Map<Class<?>, JsonSerialize> cache = new HashMap<>();

		public JsonSerialize findCustomJsonSerializeAnnotation(Annotation annotation) {
			Class<?> annotationType = annotation.annotationType();
			JsonSerialize customJsonSerializeAnnotation = cache.get(annotationType);
			if (customJsonSerializeAnnotation == null) {// TODO 这里需要加上锁？
				if (!cache.containsKey(annotationType)) {
					customJsonSerializeAnnotation = findCustomJsonSerializeAnnotation(annotationType);
					cache.put(annotationType, customJsonSerializeAnnotation);
				}
			}
			return customJsonSerializeAnnotation;
		}

		/**
		 * 判断一个注解是否为自定义的JsonSerialize
		 * 
		 * @param annotation
		 * @return
		 */
		public static JsonSerialize findCustomJsonSerializeAnnotation(Class<?> annotationType) {
			for (Annotation a : annotationType.getAnnotations()) {
				if (a.annotationType().equals(JsonSerialize.class)) {
					return (JsonSerialize) a;
				}
			}
			return null;
		}
	}
}
