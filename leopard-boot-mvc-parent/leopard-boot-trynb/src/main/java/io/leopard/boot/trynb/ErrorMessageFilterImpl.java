package io.leopard.boot.trynb;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class ErrorMessageFilterImpl implements ErrorMessageFilter {

	@Autowired
	private List<ErrorMessageFilter> filterList;

	@Override
	public String parseMessage(HttpServletRequest request, Throwable e) {
		if (e == null) {
			throw new IllegalArgumentException("exception不能为空?");
		}
		for (ErrorMessageFilter filter : filterList) {
			String message = filter.parseMessage(request, e);
			if (message != null) {
				return message;
			}
		}
		return ErrorUtil.fillterDebugInfo(e.getMessage());
	}

}
