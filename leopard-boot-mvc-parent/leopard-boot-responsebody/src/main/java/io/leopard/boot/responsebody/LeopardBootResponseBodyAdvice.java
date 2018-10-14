package io.leopard.boot.responsebody;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class LeopardBootResponseBodyAdvice implements ResponseBodyAdvice<Object> {

	// @Autowired
	// private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

	/**
	 * 判断哪些需要拦截
	 */
	@Override
	public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> converterType) {
		// System.err.println("LeopardBootResponseBodyAdvice supports returnType:" + returnType + " converterType:" + converterType.getName());
		return methodParameter.hasMethodAnnotation(ResponseBody.class);
		// return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {

		// System.err.println("LeopardBootResponseBody beforeBodyWrite returnType:" + returnType + " selectedConverterType:" + selectedConverterType.getName() + " selectedContentType:" +
		// selectedContentType);
		// System.err.println(
		// "LeopardBootResponseBodyAdvice beforeBodyWrite22:" + body + " selectedConverterType:" + selectedConverterType.getName() + " selectedContentType:" + selectedContentType.getType());

		if (!MappingJackson2HttpMessageConverter.class.isAssignableFrom(selectedConverterType)) {
			// return body;
			throw new RuntimeException("怎么会匹配到其他转换器[" + selectedConverterType.getName() + "]?");
		}

		ResponseEntity entity;
		if (body != null && body instanceof ResponseEntity) {
			entity = (ResponseEntity) body;
		}
		else {
			entity = new ResponseEntity();
			entity.setStatus("200");//FIXME 这里要改成success
			entity.setData(body);
		}
		return entity;
	}
}