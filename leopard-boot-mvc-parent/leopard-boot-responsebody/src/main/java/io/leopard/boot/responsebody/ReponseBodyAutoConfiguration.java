package io.leopard.boot.responsebody;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Configuration
@Order(Ordered.LOWEST_PRECEDENCE - 1000)
public class ReponseBodyAutoConfiguration extends WebMvcConfigurerAdapter {
	// @Autowired
	// private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

	@Autowired
	private LeopardMappingJackson2HttpMessageConverter leopardMappingJackson2HttpMessageConverter;

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		// for (HttpMessageConverter<?> converter : converters) {
		// System.err.println("ReponseBodyAutoConfiguration converter:" + converter.getClass().getName() + " types:" + converter.getSupportedMediaTypes());
		// }
		// converters.clear();// 清除Spring Boot默认的转换器
		converters.add(leopardMappingJackson2HttpMessageConverter);
		// super.configureMessageConverters(converters);
	}

	@Bean
	public LeopardMappingJackson2HttpMessageConverter leopardMappingJackson2HttpMessageConverter(ObjectMapper objectMapper) {
		{
			SimpleModule module = new SimpleModule();
			module.addSerializer(new OnumJsonSerializer());
			objectMapper.registerModule(module);

			// objectMapper.setAnnotationIntrospector(new ResponseBodyJsonSerializerIntrospector());
		}

		LeopardMappingJackson2HttpMessageConverter converter = new LeopardMappingJackson2HttpMessageConverter(objectMapper);
		// requestMappingHandlerAdapter.setMessageConverters(Arrays.asList(converter));
		return converter;
	}

}