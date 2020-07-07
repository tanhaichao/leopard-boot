package io.leopard.boot.requestbody;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils;

public class RequestBodyFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		// 打印请求Url
		String requestBody = request.getParameter("requestBody");
		if (requestBody == null) {
			ServletInputStream input = request.getInputStream();
			byte[] bytes = IOUtils.toByteArray(input);
			if (bytes.length > 0) {
				requestBody = new String(bytes);
			}
			// System.err.println("requestBody uri:" + request.getRequestURI() + " length:" + bytes.length + " " + new String(bytes));
			input.close();
		}
		// System.err.println("RequestBodyFilter:" + requestBody);
		if (StringUtils.isEmpty(requestBody)) {
			chain.doFilter(req, res);
		}
		else {
			RequestBodyHttpServletRequestWrapper wrapper = new RequestBodyHttpServletRequestWrapper(request, requestBody);
			chain.doFilter(wrapper, res);

			// Object body = Json.toMap(requestBody);
			// request.setAttribute("requestBody", body);
			// chain.doFilter(req, res);
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}
