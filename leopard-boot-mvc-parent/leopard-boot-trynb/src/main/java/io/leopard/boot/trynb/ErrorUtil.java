package io.leopard.boot.trynb;

import java.lang.reflect.GenericSignatureFormatError;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.mysql.jdbc.MysqlDataTruncation;

import io.leopard.boot.trynb.message.MessageParserImpl;

public class ErrorUtil {
	protected static final Log logger = LogFactory.getLog(ErrorUtil.class);

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

	/**
	 * 获取异常信息.
	 * 
	 * @param e
	 * @return
	 */
	public static String parseMessage(Throwable e) {
		String message = _parseMessage(e);
		if (message == null) {
			return null;
		}
		return fillterDebugInfo(message);
	}

	private static String _parseMessage(Throwable e) {

		if (e == null) {
			throw new IllegalArgumentException("exception不能为空?");
		}

		String className = e.getClass().getName();

		String message = MESSAGE_MAP.get(className);
		if (message != null) {
			return message;
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
			return "JSON序列化出错(原因:" + cause.getMessage() + ")";
		}
		// throw new JsonMappingException("JSON序列化出错(原因:" + cause.getMessage() + ").", t);

		if (e instanceof DataIntegrityViolationException) {
			Exception exception = (Exception) e.getCause();
			if (exception instanceof MysqlDataTruncation) {
				return MessageParserImpl.parse((MysqlDataTruncation) exception);
			}
			else {
				return "操作数据库出错，请稍后重试.";
			}
			// try {
			// return parseDataIntegrityViolationException((DataIntegrityViolationException) e);
			// }
			// catch (Exception e1) {
			// e1.printStackTrace();
			// return "字段太长，请稍后重试.";
			// }

		}

		if (e instanceof MethodArgumentTypeMismatchException) {
			Exception exception = (Exception) e.getCause().getCause();
			if (exception == null) {
				return e.getCause().getMessage();
			}
			return exception.getMessage();
		}
		return e.getMessage();
	}

	/**
	 * 过滤debug信息.
	 * 
	 * @param message
	 * @return
	 */
	protected static String fillterDebugInfo(String message) {
		message = message.replaceAll("\\[[^\\[]*?\\]", "");
		return message.replaceAll("\\[.*?\\]", "");
		// return message;
	}
}
