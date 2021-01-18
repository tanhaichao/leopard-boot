package io.leopard.boot.trynb;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import io.leopard.boot.responsebody.ResponseEntity;
import io.leopard.boot.trynb.annotations.Trynb;

@Configuration
public class TrynbHandlerExceptionResolver implements HandlerExceptionResolver {
	protected Log logger = LogFactory.getLog(this.getClass());
	@Autowired
	private ErrorMessageFilter errorMessageFilter;

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
		if (e == null) {
			throw new NullPointerException("exception参数怎么会为null?");
		}
		request.setAttribute("exception", e);

		// System.err.println("handler:" + handler);
		// e.printStackTrace();
		// logger.error("resolveException:" + exception.toString());

		if (e instanceof MethodArgumentTypeMismatchException) {
			logger.error("参数解析出错 url:" + request.getRequestURI() + "?" + request.getQueryString());
		}

		this.printLog(handler, e);
		// TODO 未支持HTML页面错误提示

		// String uri = request.getRequestURI();
		// e.printStackTrace();// TODO
		// logger.error(e.getMessage(), e);// TODO 这里要区分日志级别？

		ResponseEntity entity = new ResponseEntity();
		String message;
		if (e instanceof ConstraintViolationException) {
			ConstraintViolationException exs = (ConstraintViolationException) e;
			Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
			// entity.setStatus("IllegalArgumentException");
			entity.setStatus(e.getClass().getSimpleName());
			if (!violations.isEmpty()) {
				ConstraintViolation<?> violation = violations.iterator().next();
				message = ErrorUtil.fillterDebugInfo(violation.getMessage());
				entity.setMessage(message);
			}
			else {
				message = "未知参数错误!";
			}
			// for (ConstraintViolation<?> item : violations) {
			// // 打印验证不通过的信息
			// System.out.println(item.getMessage());
			// }
		}
		else {
			message = errorMessageFilter.parseMessage(request, e);// ErrorUtil.parseMessage(e);
			entity.setStatus(e.getClass().getSimpleName());

		}
		entity.setMessage(message);
		// entity.setData("url:" + request.getRequestURI());
		return new TrynbView(entity);
	}

	protected void printLog(Object handler, Exception e) {

		if (!(handler instanceof HandlerMethod)) {
			logger.error(e.getMessage(), e);
			return;
		}
		HandlerMethod method = (HandlerMethod) handler;

		// this.printLog(method, e);

		Trynb trynb = this.getTrynb(method, e);
		if (trynb == null) {
			logger.error(e.getMessage(), e);
		}
		else {
			LogLevel level = trynb.level();
			boolean printStackTrace = trynb.printStackTrace();
			if (level == LogLevel.ERROR) {
				if (printStackTrace) {
					logger.error(e.getMessage());
				}
				else {
					logger.error(e.getMessage(), e);
				}
			}
			else if (level == LogLevel.WARN) {
				if (printStackTrace) {
					logger.warn(e.getMessage());
				}
				else {
					logger.warn(e.getMessage(), e);
				}
			}
			else if (level == LogLevel.INFO) {
				if (printStackTrace) {
					logger.info(e.getMessage());
				}
				else {
					logger.info(e.getMessage(), e);
				}
			}
			else if (level == LogLevel.DEBUG) {
				if (printStackTrace) {
					logger.debug(e.getMessage());
				}
				else {
					logger.debug(e.getMessage(), e);
				}
			}
			else if (level == LogLevel.OFF) {// 关闭日志
			}
			else if (level == LogLevel.FATAL) {
				if (printStackTrace) {
					logger.fatal(e.getMessage());
				}
				else {
					logger.fatal(e.getMessage(), e);
				}
			}
			else {
				logger.error(e.getMessage(), e);
			}
		}
	}

	protected Trynb getTrynb(HandlerMethod method, Exception e) {
		// {
		// AnnotatedElementUtils.getMergedRepeatableAnnotations(element, annotationType);

		Class<?> clazz = e.getClass();

		Trynb[] trynbs = method.getMethod().getDeclaredAnnotationsByType(Trynb.class);
		// Set<Trynb> trynbSet = AnnotationUtils.getRepeatableAnnotations(method.getMethod(), Trynb.class);
		// Set<Trynb> trynbSet = AnnotatedElementUtils.findMergedRepeatableAnnotations(method.getMethod(), Trynb.class);

		// Set<Trynb> trynbSet = method.getMethodAnnotation(Trynb.class);
		if (trynbs != null) {
			for (Trynb trynb : trynbs) {
				Class<? extends Exception> value = trynb.value();
				if (value.equals(clazz)) {
					return trynb;
				}
				System.err.println("trynb value:" + trynb.value().getName() + " level:" + trynb.level() + " print:" + trynb.printStackTrace() + " message:" + trynb.message());
			}
		}
		return null;
		// }

	}
}
