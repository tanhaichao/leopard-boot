package io.leopard.boot.trynb;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import io.leopard.boot.responsebody.ResponseEntity;

/**
 * 全局异常处理
 * 
 * @author 谭海潮
 *
 */
@ControllerAdvice
@ResponseBody
public class TrynbExceptionHandler {
	protected Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private ErrorMessageFilter errorMessageFilter;

	@ExceptionHandler(Exception.class)
	// @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity handlerException(HttpServletRequest request, Exception e) {
		request.setAttribute("exception", e);
		// e.printStackTrace();// TODO
		logger.error(e.getMessage(), e);// TODO 这里要区分日志级别？

		String message = errorMessageFilter.parseMessage(request, e);// ErrorUtil.parseMessage(e);

		ResponseEntity entity = new ResponseEntity();
		entity.setStatus(e.getClass().getSimpleName());
		entity.setMessage(message);
		// entity.setData("url:" + request.getRequestURI());
		return entity;
	}

	@ExceptionHandler(ValidationException.class)
	// @ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity handle(ValidationException exception) {
		if (exception instanceof ConstraintViolationException) {
			ConstraintViolationException exs = (ConstraintViolationException) exception;
			Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();

			if (!violations.isEmpty()) {
				ConstraintViolation<?> violation = violations.iterator().next();
				String message = violation.getMessage();
				ResponseEntity entity = new ResponseEntity();
				entity.setStatus("IllegalArgumentException");
				entity.setMessage(message);
				// entity.setData("url:" + request.getRequestURI());
				return entity;
			}
			// for (ConstraintViolation<?> item : violations) {
			// // 打印验证不通过的信息
			// System.out.println(item.getMessage());
			// }
		}

		ResponseEntity entity = new ResponseEntity();
		entity.setStatus(exception.getClass().getSimpleName());
		entity.setMessage(exception.getMessage());
		// entity.setData("url:" + request.getRequestURI());
		return entity;

	}
}