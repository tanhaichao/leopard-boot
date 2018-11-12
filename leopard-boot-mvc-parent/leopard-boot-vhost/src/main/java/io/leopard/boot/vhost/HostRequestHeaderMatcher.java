package io.leopard.boot.vhost;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import io.leopard.boot.requestmapping.custom.RequestHeaderMatcher;

public class HostRequestHeaderMatcher implements RequestHeaderMatcher {

	private String host;

	public HostRequestHeaderMatcher(String host) {
		if (StringUtils.isEmpty(host)) {
			throw new IllegalArgumentException("host不能为空.");
		}
		this.host = host;
	}

	@Override
	public boolean match(HttpServletRequest request) {
		String host = request.getHeader("Host");
		return this.host.equals(host);
	}

	@Override
	public String getHeader() {
		return "Host=" + this.host;
	}

	@Override
	public boolean isFirstLookup() {
		return true;
	}

}
