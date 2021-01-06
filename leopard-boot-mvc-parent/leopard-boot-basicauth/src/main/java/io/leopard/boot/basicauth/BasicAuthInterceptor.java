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
public class BasicAuthInterceptor implements HandlerInterceptor {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		boolean isNeedCheckBasicAuth = isNeedCheckBasicAuth(handler);
		if (isNeedCheckBasicAuth) {
			logger.info("preHandle uri:" + request.getRequestURI());

		}
		return true;
	}

	/**
	 * 判断handler是否需要登录检查.
	 * 
	 * @param handler
	 * @return
	 */
	public static boolean isNeedCheckBasicAuth(Object handler) {
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		HandlerMethod method = (HandlerMethod) handler;
		BasicAuth basicAuth = method.getMethodAnnotation(BasicAuth.class);
		if (basicAuth != null) {
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

}
