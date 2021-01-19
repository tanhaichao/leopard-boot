package io.leopard.boot.basicauth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@Order(0) // 数字小优先
public class BasicAuthHandlerInterceptor implements HandlerInterceptor {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		boolean isNeedCheckBasicAuth = isNeedCheckBasicAuth(handler);
		// logger.info("preHandle uri:" + request.getRequestURI() + " isNeedCheckBasicAuth:" + isNeedCheckBasicAuth);
		if (isNeedCheckBasicAuth) {
			logger.info("preHandle auth:" + request.getRequestURI());

		}
		return true;
	}

	/**
	 * 判断handler是否需要认证
	 * 
	 * @param handler
	 * @return
	 */
	public static boolean isNeedCheckBasicAuth(Object handler) {
		if (handler instanceof HandlerMethod) {
			HandlerMethod method = (HandlerMethod) handler;
			System.err.println("method:" + method);
			if (method.getMethodAnnotation(BasicAuth.class) != null) {
				return true;
			}

			Class<?> beanType = method.getBeanType();
			if (beanType.getAnnotation(BasicAuth.class) != null) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

}
