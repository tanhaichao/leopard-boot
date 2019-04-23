package io.leopard.boot.trynb;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController extends BasicErrorController {

	public ErrorController() {
		super(new DefaultErrorAttributes(), new ErrorProperties());
	}

	@Override
	@RequestMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
		Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
		HttpStatus status = getStatus(request);
		// 自定义的错误信息类
		// status.value():错误代码，
		// body.get("message").toString()错误信息
		// io.leopard.boot.responsebody.ResponseEntity entity = new io.leopard.boot.responsebody.ResponseEntity();
		Map<String, Object> entity = new LinkedHashMap<>();

		// entity.put("status", "500");
		// entity.put("message", );

		// if (!Strings.isNullOrEmpty((String) body.get("exception")) && body.get("exception").equals(TokenException.class.getName())) {
		// body.put("status", HttpStatus.FORBIDDEN.value());
		// status = HttpStatus.FORBIDDEN;
		// }
		return new ResponseEntity<Map<String, Object>>(body, status);
	}

	@Override
	public String getErrorPath() {
		return "error/error";
	}
}
