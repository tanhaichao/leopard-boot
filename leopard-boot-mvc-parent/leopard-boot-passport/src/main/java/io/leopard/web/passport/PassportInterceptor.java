package io.leopard.web.passport;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;
import io.leopard.boot.servlet.util.RequestUtil;

/**
 * 检查是否已登录.
 * 
 * @author 阿海
 * 
 */
@Component
// TODO ahai 在site项目必须实现BeanPostProcessor接口才能成功配置拦截器.
@Order(9) // 数字小优先
public class PassportInterceptor implements HandlerInterceptor {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private PassportValidatorFinder passportValidatorFinder;

	@Resource
	Configuration configuration;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		if ("/passport/login".equals(uri)) {// 忽略自己
			return true;
		}

		if (uri.indexOf("/enum/") == -1) {
			logger.info("preHandle:" + uri);
		}
		List<PassportValidator> passportValidatorList = passportValidatorFinder.find(request, handler);
		for (PassportValidator validator : passportValidatorList) {
			Object account = PassportUtil.validateAndStore(validator, request, response);
			// logger.info("validator:" + validator + " handler:" + handler + " account:" + account);
			if (account == null) {
				boolean isNeedCheckLogin = PassportUtil.isNeedCheckLogin(validator, request, handler);
				if (isNeedCheckLogin) {
					showLoginBox(validator, request, response);
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 显示登录框
	 * 
	 * @param validator
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 * @throws MalformedTemplateNameException
	 * @throws TemplateNotFoundException
	 */
	protected boolean showLoginBox(PassportValidator validator, HttpServletRequest request, HttpServletResponse response)
			throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException {
		String sessionAttributeName = PassportUtil.getSessionAttributeName(validator);

		String ip = RequestUtil.getProxyIp(request);
		String message = "您[" + ip + "]未登录,uri:" + request.getRequestURI();
		logger.info(message);
		if (validator.showLoginBox(request, response)) {// 自定义验证器已经显示登录框
			return true;
		}
		Template template = configuration.getTemplate("passport/login.ftl");

		// FtlView view = new FtlView("/passport/ftl", "login");
		String url = RequestUtil.getRequestURL(request);
		String queryString = request.getQueryString();
		if (queryString != null && queryString.length() > 0) {
			url += "?" + queryString;
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("url", url);
		model.put("type", sessionAttributeName);
		try {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			template.process(model, out);
			// view.render(model, request, response);
			return true;
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

}
