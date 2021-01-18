package io.leopard.boot.trynb;

import java.lang.reflect.GenericSignatureFormatError;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

@Component
@Order(0)
public class ErrorMessageFilterSystemImpl implements ErrorMessageFilter {
	private static Map<String, String> MESSAGE_MAP = new HashMap<String, String>();
	static {
		MESSAGE_MAP.put("org.springframework.dao.DataAccessException", "操作数据库出错，请稍后重试.");
		MESSAGE_MAP.put("org.springframework.jdbc.CannotGetJdbcConnectionException", "操作数据库出错，请稍后重试.");
		MESSAGE_MAP.put("org.springframework.dao.RecoverableDataAccessException", "操作数据库出错，请稍后重试.");
		MESSAGE_MAP.put("org.springframework.dao.DuplicateKeyException", "数据库主键重复，请稍后重试.");
		// MESSAGE_MAP.put("org.springframework.dao.DataIntegrityViolationException", "字段太长，请稍后重试.");
		MESSAGE_MAP.put("org.springframework.dao.TransientDataAccessResourceException", "操作数据库出错，请稍后重试.");
		MESSAGE_MAP.put("org.springframework.jdbc.BadSqlGrammarException", "非法SQL语句，请稍后重试.");
		MESSAGE_MAP.put("redis.clients.jedis.exceptions.JedisConnectionException", "操作数据库出错，请稍后重试.");
		MESSAGE_MAP.put("io.leopard.core.exception.other.OutSideException", "访问外部接口出错，请稍后重试.");
		MESSAGE_MAP.put("freemarker.template.TemplateNotFoundException", "模板文件找不到.");
		MESSAGE_MAP.put("com.fasterxml.jackson.databind.exc.InvalidFormatException", "JSON不合法.");
	}

	@Override
	public String parseMessage(HttpServletRequest request, Throwable e) {
		{
			String className = e.getClass().getName();
			String message = MESSAGE_MAP.get(className);
			if (message != null) {
				return message;
			}
		}
		if (e instanceof GenericSignatureFormatError) {
			return "更新程序后，还没有重启服务.";
		}
		if (e instanceof NoSuchMethodError) {
			return "NoSuchMethodError:方法找不到.";
		}
		if (e instanceof SQLException) {
			return "操作数据库出错，请稍后重试.";
		}
		if (e instanceof MismatchedInputException) {
			String message = e.getMessage();
			return "JSON序列化出错(原因:" + ErrorUtil.fillterDebugInfo(message) + ")";
		}
		if (e instanceof JsonMappingException) {
			Throwable cause = e.getCause();
			String message = cause.getMessage();
			return "JSON序列化出错(原因:" + ErrorUtil.fillterDebugInfo(message) + ")";
		}

		if (e instanceof MethodArgumentTypeMismatchException) {
			Exception exception = (Exception) e.getCause().getCause();
			String message;
			if (exception == null) {
				message = e.getCause().getMessage();
			}
			else {
				message = exception.getMessage();
			}

			MethodArgumentTypeMismatchException e2 = (MethodArgumentTypeMismatchException) e;
			String parameterName = e2.getParameter().getParameterName();
			// return "参数(" + parameterName + ")解析出错，原因:" + message;
			// 这里不能输出原因，错误message可能会出现安全漏洞
			return "参数(" + parameterName + ")解析出错";
		}
		return null;
	}

}
