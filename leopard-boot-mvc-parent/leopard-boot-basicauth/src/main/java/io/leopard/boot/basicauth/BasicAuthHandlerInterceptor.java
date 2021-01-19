package io.leopard.boot.basicauth;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import io.leopard.boot.servlet.util.RequestUtil;

@Component
@Order(0) // 数字小优先
public class BasicAuthHandlerInterceptor implements HandlerInterceptor {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private AuthService authService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		boolean isNeedCheckBasicAuth = isNeedCheckBasicAuth(handler);
		// logger.info("preHandle uri:" + request.getRequestURI() + " isNeedCheckBasicAuth:" + isNeedCheckBasicAuth);
		if (isNeedCheckBasicAuth) {
			boolean autherized;
			try {
				autherized = authService.checkAuth(request);
				if (!autherized) {
					this.showAuthBox(request, response, "需要登录才能访问");
				}
			}
			catch (Exception e) {
				this.showAuthBox(request, response, e.getMessage());
				autherized = false;
			}
			return autherized;
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	protected void showAuthBox(HttpServletRequest request, HttpServletResponse response, String message) throws IOException {
		String proxyIp = RequestUtil.getProxyIp(request);
		logger.info("showAuthBox:" + request.getRequestURI() + " proxyIp:" + proxyIp + " message:" + message);
		String msg = "\"请登录\""; // 如果认证失败,则要求认证 ，不能输入中文
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		response.setStatus(401, "Authentication Required");// 发送状态码 401, 不能使用 sendError，坑
		response.setHeader("WWW-Authenticate", "Basic realm=" + msg);// 发送要求输入认证信息,则浏览器会弹出输入框
		// response.setCharacterEncoding("utf-8");
		// response.getWriter().append("<meta charset=\"utf-8\" />需要登录才能访问!");

		// logger.info("HTTP BasicAuth 登录失败！");
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
