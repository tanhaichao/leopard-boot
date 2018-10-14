package io.leopard.boot.requestmapping;

import org.springframework.boot.autoconfigure.web.WebMvcRegistrationsAdapter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
@ComponentScan
public class RequestMappingAutoConfiguration extends WebMvcRegistrationsAdapter {

	@Override
	public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
		return new LeopardContextPathRequestMappingHandlerMapping();
		// return new LeopardRequestMappingHandlerMapping();
	}

}
