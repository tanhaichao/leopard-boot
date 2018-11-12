package io.leopard.boot.requestmapping.custom;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.mvc.condition.RequestCondition;

public class LeopardHttpHeaderRequestCondition implements RequestCondition<LeopardHttpHeaderRequestCondition> {

	private Set<RequestHeaderMatcher> headerMatcherList;

	public LeopardHttpHeaderRequestCondition(Set<RequestHeaderMatcher> headerMatcherList) {
		System.err.println("new LeopardHttpHeaderRequestCondition:" + headerMatcherList);
		this.headerMatcherList = headerMatcherList;
	}

	@Override
	public LeopardHttpHeaderRequestCondition combine(LeopardHttpHeaderRequestCondition other) {
		new Exception("combine").printStackTrace();
		System.err.println("combine:" + other);
		// 不做合并
		Set<RequestHeaderMatcher> set = new LinkedHashSet<>(this.headerMatcherList);
		set.addAll(other.headerMatcherList);
		return new LeopardHttpHeaderRequestCondition(set);
	}

	/**
	 * 是否优先匹配
	 * 
	 * @return
	 */
	public boolean isFirstLookup() {
		for (RequestHeaderMatcher matcher : this.headerMatcherList) {
			if (matcher.isFirstLookup()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public LeopardHttpHeaderRequestCondition getMatchingCondition(HttpServletRequest request) {
		System.err.println("getMatchingCondition:" + request);
		new Exception("getMatchingCondition").printStackTrace();
		for (RequestHeaderMatcher headerMatcher : headerMatcherList) {
			boolean matched = headerMatcher.match(request);
			if (!matched) {
				return null;
			}
		}
		return this;
	}

	@Override
	public int compareTo(LeopardHttpHeaderRequestCondition other, HttpServletRequest request) {
		return other.headerMatcherList.size() - headerMatcherList.size();
	}

}
