package io.leopard.boot.requestmapping.custom;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class RequestHeaderResolverImpl implements RequestHeaderResolver {

	@Autowired(required = false)
	private List<RequestHeaderResolver> resolvers;

	@Override
	public void resolve(Method method, Set<RequestHeaderMatcher> headerMatcherList) {
		if (resolvers != null && !resolvers.isEmpty()) {
			for (RequestHeaderResolver resolver : resolvers) {
				resolver.resolve(method, headerMatcherList);
			}
		}
	}
}
