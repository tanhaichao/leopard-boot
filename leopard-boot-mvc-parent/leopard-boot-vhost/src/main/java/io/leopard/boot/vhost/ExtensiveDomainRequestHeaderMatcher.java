package io.leopard.boot.vhost;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.leopard.boot.requestmapping.custom.RequestHeaderMatcher;

/**
 * 泛域名匹配器
 * 
 * @author 谭海潮
 *
 */
public class ExtensiveDomainRequestHeaderMatcher implements RequestHeaderMatcher {
	protected Log logger = LogFactory.getLog(this.getClass());

	ExtensiveDomain domain = new ExtensiveDomain();

	private String domainPattern;

	private boolean firstLookup;

	public ExtensiveDomainRequestHeaderMatcher(String domainPattern, boolean firstLookup) {
		this.domain.addExtensiveDomain(domainPattern);
		this.domainPattern = domainPattern;
		this.firstLookup = firstLookup;
	}

	@Override
	public String getHeader() {
		return "Host=" + domainPattern;
	}

	@Override
	public boolean match(HttpServletRequest request) {
		String serverName = request.getServerName();
		boolean matched = domain.match(serverName);
		logger.info("match serverName:" + serverName + " matched:" + matched);
		return matched;
	}

	@Override
	public boolean isFirstLookup() {
		return firstLookup;
	}

}
