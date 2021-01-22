package io.leopard.boot.admin.accesslog;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 管理后台日志.
 * 
 * @author 阿海
 * 
 */
@Component
public class AdminAccessLogHandlerInterceptor implements HandlerInterceptor {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		try {
			save(request, response, handler);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void save(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (!(handler instanceof HandlerMethod)) {
			return;
		}
		HandlerMethod method = (HandlerMethod) handler;
		ResponseBody anno = method.getMethodAnnotation(ResponseBody.class);
		if (anno == null) {
			return;
		}
	}
}
