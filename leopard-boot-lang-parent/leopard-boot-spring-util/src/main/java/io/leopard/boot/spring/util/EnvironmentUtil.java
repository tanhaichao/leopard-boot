package io.leopard.boot.spring.util;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.boot.env.EnumerableCompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

public class EnvironmentUtil {

	public static Set<String> findPropertyeNameSet(ConfigurableEnvironment environment, String regex) {
		EnumerableCompositePropertySource source = findEnumerableCompositePropertySource(environment);
		Set<String> nameSet = new LinkedHashSet<>();
		if (source != null) {
			String[] names = source.getPropertyNames();
			for (String name : names) {
				if (name.matches(regex)) {
					nameSet.add(name);
				}
			}
		}
		return nameSet;
	}

	private static EnumerableCompositePropertySource findEnumerableCompositePropertySource(ConfigurableEnvironment environment) {
		List<EnumerableCompositePropertySource> list = findEnumerableCompositePropertySourceList(environment);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	@SuppressWarnings("unchecked")
	private static List<EnumerableCompositePropertySource> findEnumerableCompositePropertySourceList(ConfigurableEnvironment environment) {
		MutablePropertySources propertySources = environment.getPropertySources();
		Iterator<PropertySource<?>> iterator = propertySources.iterator();
		while (iterator.hasNext()) {
			PropertySource<?> source = iterator.next();
			Object obj = source.getSource();
			// System.err.println("obj:"+obj.getClass().getName());
			if (obj instanceof List) {
				return (List<EnumerableCompositePropertySource>) obj;
			}
		}
		return null;
	}
}
