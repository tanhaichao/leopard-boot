package io.leopard.boot.test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;

import io.leopard.spring.ServerEnv;

/**
 * 创建
 * 
 * @author 谭海潮
 *
 */
public class BeanCreator {

	public static <T> T create(Class<T> clazz) {
		try {
			return _create(clazz, ServerEnv.DEV);
		}
		catch (RuntimeException e) {
			throw e;
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	protected static Properties getProperties(ServerEnv env) throws IOException {
		Properties p = new Properties();
		{
			ClassPathResource resource = new ClassPathResource("/application.properties");
			InputStream input = resource.getInputStream();
			p.load(input);
			input.close();
		}

		ClassPathResource resource = new ClassPathResource("/application-" + env.getKey() + ".properties");
		if (resource.exists()) {
			InputStream input = resource.getInputStream();
			p.load(input);
			input.close();
		}
		return p;
	}

	protected static <T> T _create(Class<T> clazz, ServerEnv env) throws Exception {
		Properties p = getProperties(env);
		System.err.println("ketSet:" + p.keySet());
		T bean = clazz.newInstance();
		Field[] fields = clazz.getDeclaredFields();
		if (fields != null) {
			for (Field field : fields) {
				field.setAccessible(true);
				System.err.println("name:" + field.getName());
			}
		}
		return bean;
	}
}
