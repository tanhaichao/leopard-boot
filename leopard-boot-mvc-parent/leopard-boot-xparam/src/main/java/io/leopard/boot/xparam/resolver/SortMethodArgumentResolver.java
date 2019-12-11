package io.leopard.boot.xparam.resolver;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

import io.leopard.boot.requestbody.RequestBodyParser;
import io.leopard.lang.datatype.Sort;

/**
 * 排序参数解析器
 * 
 * @author ahai
 *
 */
@Component
@Order(3)
public class SortMethodArgumentResolver extends AbstractNamedValueMethodArgumentResolver implements XParamResolver {
	protected Log logger = LogFactory.getLog(this.getClass());

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		Class<?> clazz = parameter.getParameterType();
		if (Sort.class.isAssignableFrom(clazz)) {
			return true;
		}
		return false;
	}

	@Override
	protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest webRequest) throws Exception {
		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
		// Object value = request.getParameter(name);
		Object value = RequestBodyParser.getParameter(request, name);
		if (value == null) {
			return null;
		}
		String sortValue = (String) value;
		Class<?> clazz = parameter.getParameterType();
		Sort sort = (Sort) clazz.newInstance();
		sort.setSort(sortValue);
		return sort;
	}

}
