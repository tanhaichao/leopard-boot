package io.leopard.boot.requestmapping;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class PathLookupHandlerImpl implements PathLookupHandler {

	@Autowired
	private List<PathLookupHandler> list;

	@Override
	public boolean isIgnore(String lookupPath, HttpServletRequest request) throws Exception {
		for (PathLookupHandler handler : list) {
			boolean ignore = handler.isIgnore(lookupPath, request);
			System.err.println("isIgnore handler:" + handler);
			if (ignore) {
				return true;
			}
		}
		return false;
	}

}
