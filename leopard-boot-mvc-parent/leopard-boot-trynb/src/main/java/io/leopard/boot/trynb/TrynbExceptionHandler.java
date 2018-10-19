package io.leopard.boot.trynb;

import javax.servlet.http.HttpServletRequest;

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

	@Autowired
	private ErrorMessageFilter errorMessageFilter;

	@ExceptionHandler(Exception.class)
	// @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity handlerException(HttpServletRequest request, Exception e) {
		request.setAttribute("exception", e);
		e.printStackTrace();// TODO
		String message = errorMessageFilter.parseMessage(request, e);// ErrorUtil.parseMessage(e);

		ResponseEntity entity = new ResponseEntity();
		entity.setStatus(e.getClass().getSimpleName());
		entity.setMessage(message);
		// entity.setData("url:" + request.getRequestURI());
		return entity;
	}

}