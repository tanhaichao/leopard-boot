package io.leopard.boot.requestmapping;

import java.lang.reflect.Method;

import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

/**
 * RequestMappingInfo组合器
 * 
 * @author 谭海潮
 *
 */
public interface RequestMappingInfoCombiner {

	RequestMappingInfo combine(RequestMappingInfo.BuilderConfiguration options, RequestMappingInfo info, Method method, Class<?> handlerType);

}
