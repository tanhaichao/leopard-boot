package io.leopard.boot.requestmapping;

import org.springframework.boot.autoconfigure.web.WebMvcRegistrationsAdapter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
@ComponentScan
public class LeopardRequestMappingAutoConfiguration extends WebMvcRegistrationsAdapter {

	@Override
	public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
		ExtensibleRequestMappingHandlerMapping mapping = new ExtensibleRequestMappingHandlerMapping();
		mapping.setUseSuffixPatternMatch(false);
		mapping.setUseTrailingSlashMatch(false);
		mapping.setUseRegisteredSuffixPatternMatch(false);
		return mapping;
		// return new LeopardContextPathRequestMappingHandlerMapping();
		// return new LeopardRequestMappingHandlerMapping();
	}

}
