package io.leopard.boot.requestmapping;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@Primary
public class RequestMappingInfoBuilderImpl implements RequestMappingInfoBuilder {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Autowired(required = false)
	private List<RequestMappingInfoBuilder> builders;

	@Override
	public void getHeaders(RequestMapping annotation, Method method, Map<String, String> headers) {
		if (builders == null) {
			return;
		}
		for (RequestMappingInfoBuilder builder : builders) {
			builder.getHeaders(annotation, method, headers);
		}
	}

}
