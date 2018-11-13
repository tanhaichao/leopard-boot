package io.leopard.boot.vhost;

import java.util.ArrayList;
import java.util.List;

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
public class VhostRequestHeaderMatcher implements RequestHeaderMatcher {
	protected Log logger = LogFactory.getLog(this.getClass());

	ExtensiveDomain domain = new ExtensiveDomain();

	/**
	 * 域名列表
	 */
	private List<String> hostList = new ArrayList<>();

	/**
	 * 排除的URL列表
	 */
	private List<String> excludeUrlList = new ArrayList<>();

	private boolean firstLookup;

	public VhostRequestHeaderMatcher(String[] hosts, boolean firstLookup) {
		this.firstLookup = firstLookup;

		for (String host : hosts) {
			// logger.warn("host:" + host);
			if (host.startsWith("*.")) {// 泛域名
				this.addExtensiveDomain(host);
			}
			else {
				this.addHost(host);
			}
		}
	}

	/**
	 * 添加排除的URL
	 * 
	 * @param excludeUrl
	 */
	public void addExcludeUrl(String excludeUrl) {
		excludeUrlList.add(excludeUrl);
	}

	/**
	 * 添加泛域名
	 * 
	 * @param domain
	 */
	public void addExtensiveDomain(String domainPattern) {
		this.domain.addExtensiveDomain(domainPattern);
	}

	/**
	 * 普通域名
	 * 
	 * @param host
	 */
	public void addHost(String host) {
		hostList.add(host);
	}

	@Override
	public String getHeader() {
		throw new RuntimeException("not impl.");
	}

	@Override
	public boolean match(HttpServletRequest request) {
		if (!excludeUrlList.isEmpty()) {
			String url = request.getRequestURI();
			url = url.replaceAll("/+", "/");
			// TODO 未过滤contextPath
			if (excludeUrlList.contains(url)) {
				return false;
			}
		}
		String serverName = request.getServerName();
		for (String host : hostList) {
			if (host.equals(serverName)) {
				return true;
			}
		}
		boolean matched = domain.match(serverName);
		logger.info("match serverName:" + serverName + " matched:" + matched);
		return matched;
	}

	@Override
	public boolean isFirstLookup() {
		return firstLookup;
	}

}
