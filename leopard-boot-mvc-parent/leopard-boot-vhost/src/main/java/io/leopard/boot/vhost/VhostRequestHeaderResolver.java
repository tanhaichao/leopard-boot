package io.leopard.boot.vhost;

import java.lang.reflect.Method;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import io.leopard.boot.requestmapping.custom.RequestHeaderMatcher;
import io.leopard.boot.requestmapping.custom.RequestHeaderResolver;

/**
 * 虚拟主机
 * 
 * @author 谭海潮
 *
 */
@Order(1)
@Component
public class VhostRequestHeaderResolver implements RequestHeaderResolver {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Override
	public void resolve(Method method, Set<RequestHeaderMatcher> headerMatcherList) {
		if (method == null) {
			return;
		}
		Vhost vhost = findVhost(method);
		if (vhost == null) {
			return;
		}

		VhostExcludeUrls vhostExcludeUrls = method.getAnnotation(VhostExcludeUrls.class);
		VhostRequestHeaderMatcher matcher = new VhostRequestHeaderMatcher(vhost.value(), vhost.firstLookup());
		if (vhostExcludeUrls != null) {
			for (VhostExcludeUrl vhostExcludeUrl : vhostExcludeUrls.value()) {
				for (String excludeUrl : vhostExcludeUrl.value()) {
					// System.err.println("excludeUrl:" + excludeUrl);
					matcher.addExcludeUrl(excludeUrl);
				}
			}
		}
		// List<String> hostList = new ArrayList<>();
		headerMatcherList.add(matcher);

	}

	protected Vhost findVhost(Method method) {
		Vhost vhost = method.getAnnotation(Vhost.class);
		if (vhost != null) {
			return vhost;
		}
		Class<?> clazz = method.getDeclaringClass();
		vhost = AnnotationUtils.findAnnotation(clazz, Vhost.class);
		return vhost;
	}

	/**
	 * 获取vhost配置
	 * 
	 * @param method
	 * @return
	 */
	protected String[] getVhosts(Method method) {
		Vhost vhost = method.getAnnotation(Vhost.class);
		if (vhost != null && vhost.value().length > 0) {
			return vhost.value();
		}
		Class<?> clazz = method.getDeclaringClass();
		vhost = AnnotationUtils.findAnnotation(clazz, Vhost.class);
		if (vhost == null) {
			return null;
		}
		// System.err.println("vhost:" + vhost.toString());
		return vhost.value();
	}

}
