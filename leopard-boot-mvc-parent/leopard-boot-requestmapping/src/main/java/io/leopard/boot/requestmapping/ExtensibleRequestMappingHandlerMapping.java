package io.leopard.boot.requestmapping;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import io.leopard.boot.requestmapping.custom.LeopardHttpHeaderRequestCondition;
import io.leopard.boot.requestmapping.custom.RequestHeaderMatcher;
import io.leopard.boot.requestmapping.custom.RequestHeaderResolver;

/**
 * 可扩展的
 * 
 * @author 谭海潮
 *
 */
public class ExtensibleRequestMappingHandlerMapping extends LeopardRequestMappingHandlerMapping {

	@Autowired
	private RequestHeaderResolver requestHeaderResolver;

	@Override
	protected RequestCondition<?> getCustomMethodCondition(Method method) {
		Set<RequestHeaderMatcher> headerMatcherList = new LinkedHashSet<>();
		requestHeaderResolver.resolve(method, headerMatcherList);
		if (headerMatcherList.isEmpty()) {
			return null;
		}
		System.err.println("headerMatcherList:" + headerMatcherList);
		return new LeopardHttpHeaderRequestCondition(headerMatcherList);
	}

	/**
	 * 优先匹配的
	 */
	private Set<RequestMappingInfo> firstLookupRequestMappingInfoSet = new HashSet<>();

	@Override
	protected RequestMappingInfo createMethodRequestMappingInfo(RequestMapping requestMapping, Method method) {
		RequestMappingInfo mapping = super.createMethodRequestMappingInfo(requestMapping, method);
		LeopardHttpHeaderRequestCondition condition = (LeopardHttpHeaderRequestCondition) mapping.getCustomCondition();
		if (condition != null) {
			if (condition.isFirstLookup()) {
				firstLookupRequestMappingInfoSet.add(mapping);
			}
		}
		return mapping;
	}

	@Override
	protected HandlerMethod lookupHandlerMethod(String lookupPath, HttpServletRequest request) throws Exception {
		if (!firstLookupRequestMappingInfoSet.isEmpty()) {
			for (Entry<RequestMappingInfo, HandlerMethod> entry : getHandlerMethods().entrySet()) {
				if (firstLookupRequestMappingInfoSet.contains(entry.getKey())) {
					RequestMappingInfo match = getMatchingMapping(entry.getKey(), request);
					if (match != null) {
						if (match.getCustomCondition() != null) {
							handleMatch(match, lookupPath, request);
							return entry.getValue();
						}
					}
				}
			}
		}
		return super.lookupHandlerMethod(lookupPath, request);
	}

}
