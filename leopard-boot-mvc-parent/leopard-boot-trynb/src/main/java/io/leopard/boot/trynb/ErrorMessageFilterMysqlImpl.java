package io.leopard.boot.trynb;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.mysql.jdbc.MysqlDataTruncation;

import io.leopard.boot.trynb.message.MessageParserImpl;

@Component
@Order(1)
public class ErrorMessageFilterMysqlImpl implements ErrorMessageFilter {

	@Override
	public String parseMessage(HttpServletRequest request, Throwable e) {
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
		return null;
	}

}
