package io.leopard.json;

import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

import io.leopard.lang.inum.Onum;

/**
 * 禁用@JsonSerializer
 * 
 * @author 谭海潮
 *
 */
public class DisablingJsonSerializerIntrospector extends JacksonAnnotationIntrospector {

	private static final long serialVersionUID = 1L;

	@Override
	public Object findDeserializer(Annotated a) {
		// System.err.println("Annotated:" + a.getClass().getName() + " " + a);

		if (a instanceof AnnotatedClass) {
			Class<?> clazz = ((AnnotatedClass) a).getAnnotated();
			// System.err.println("clazz:" + clazz.getName() + " " + a.getName());

			if (clazz.isEnum()) {
				if (Onum.class.isAssignableFrom(clazz)) {
					// System.err.println("OnumJsonSerializerIntrospector findDeserializer:" + clazz.getName() + " a:" + a);
					return new OnumJsonDeserializer(clazz);
				}
			}
		}

		Object deserializer = super.findDeserializer(a);
		return deserializer;
	}

	// @Override
	// public Boolean isIgnorableType(AnnotatedClass ac) {
	// System.out.println("isIgnorableType:" + ac.getAnnotated().getName());
	// return super.isIgnorableType(ac);
	// }

	@Override
	public Object findSerializer(Annotated am) {
		// System.err.println("am:" + am.getName());
		return null;
		// Object serializer = super.findSerializer(am);
		// if (serializer != null) {
		// Class<?> clazz = (Class<?>) serializer;
		// String className = clazz.getName();
		// if (className.endsWith("ProvinceJsonSerializer")) {
		// return null;
		// }
		// }
		// return serializer;
	}

	public Object findJsonSerializer(Annotated am) {
		return super.findSerializer(am);
	}
}
