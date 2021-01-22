package io.leopard.boot.admin.accesslog;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import io.leopard.boot.admin.accesslog.model.AccessLog;
import io.leopard.boot.servlet.util.RequestUtil;
import io.leopard.json.Json;

/**
 * 管理后台日志.
 * 
 * @author 阿海
 * 
 */
@Component
public class AdminAccessLogHandlerInterceptor implements HandlerInterceptor {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private AdminAccessLogService adminAccessLogService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		String uri = request.getRequestURI();
		uri = uri.replaceAll("/+", "/");

		if (!uri.startsWith("/api/admin/")) {// TODO 改成配置文件
			return;
		}
		logger.info("uri:" + uri);

		try {
			save(request, response, handler, ex);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 获取需要记录访问日志的方法
	 * 
	 * @param handler
	 * @return
	 */
	protected HandlerMethod getRequirementAccessLogMethod(Object handler) {
		if (!(handler instanceof HandlerMethod)) {
			return null;
		}
		HandlerMethod method = (HandlerMethod) handler;
		{// 没有ResponseBody注解，不记录日志
			ResponseBody anno = method.getMethodAnnotation(ResponseBody.class);
			if (anno == null) {
				return null;
			}
		}
		{// 存在不需要记录访问日志的注解
			NoAccessLog anno = method.getMethodAnnotation(NoAccessLog.class);
			if (anno != null) {
				return null;
			}
		}
		return method;
	}

	public void save(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		HandlerMethod method = this.getRequirementAccessLogMethod(handler);
		if (method == null) {
			return;
		}
		String handlerName = method.getBeanType().getName() + "." + method.getMethod().getName();
		long roleId = 0;
		long adminId = 0;

		String exception = AccessLogUtil.toExceptionString(ex);
		String proxyIp = RequestUtil.getProxyIp(request);
		String parameters = Json.toJson(AccessLogUtil.getParameters(request));
		String requestBody = AccessLogUtil.getRequestBody(request);
		String responseBody = null;

		AccessLog accessLog = new AccessLog();
		accessLog.setAdminId(adminId);
		accessLog.setException(exception);
		accessLog.setProxyIp(proxyIp);
		accessLog.setRoleId(roleId);
		accessLog.setRequestMethod(request.getMethod());
		accessLog.setUrl(request.getRequestURI());
		accessLog.setHandlerName(handlerName);
		accessLog.setParameters(parameters);
		accessLog.setRequestBody(requestBody);
		accessLog.setResponseBody(responseBody);

		adminAccessLogService.add(accessLog);
	}
}
