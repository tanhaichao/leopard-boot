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
		if (e instanceof JsonMappingException) {
			Throwable cause = e.getCause();
			String message = cause.getMessage();
			return "JSON序列化出错(原因:" + ErrorUtil.fillterDebugInfo(message) + ")";
		}
		if (e instanceof MethodArgumentTypeMismatchException) {
			Exception exception = (Exception) e.getCause().getCause();
			if (exception == null) {
				return e.getCause().getMessage();
			}
			return exception.getMessage();
		}
		return null;
	}

}
