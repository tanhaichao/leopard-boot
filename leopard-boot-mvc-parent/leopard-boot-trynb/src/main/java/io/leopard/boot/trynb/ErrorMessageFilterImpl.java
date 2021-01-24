package io.leopard.boot.trynb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class ErrorMessageFilterImpl implements ErrorMessageFilter {

	@Autowired
	private List<ErrorMessageFilter> filterList;

	@Override
	public String parseMessage(Throwable e) {
		if (e == null) {
			throw new IllegalArgumentException("exception不能为空?");
		}
		for (ErrorMessageFilter filter : filterList) {
			String message = filter.parseMessage(e);
			if (message != null) {
				return message;
			}
		}
		return ErrorUtil.fillterDebugInfo(e.getMessage());
	}

}
