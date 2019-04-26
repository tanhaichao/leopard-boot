package io.leopard.boot.requestmapping;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

@Component
@Primary
public class PathLookupHandlerImpl implements PathLookupHandler {

	@Autowired
	private List<PathLookupHandler> list;

	@Override
	public HandlerMethod transform(String lookupPath, HttpServletRequest request, HandlerMethod method) throws Exception {
		for (PathLookupHandler handler : list) {
			method = handler.transform(lookupPath, request, method);
		}
		return method;
	}

}
